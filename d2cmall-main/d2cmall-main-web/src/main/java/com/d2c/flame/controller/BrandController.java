package com.d2c.flame.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.search.model.SearcherMemberAttention;
import com.d2c.member.search.model.SearcherMemberCollection;
import com.d2c.member.search.service.MemberAttentionSearcherService;
import com.d2c.member.search.service.MemberCollectionSearcherService;
import com.d2c.product.dto.BrandDto;
import com.d2c.product.model.Brand;
import com.d2c.product.model.BrandCategory;
import com.d2c.product.model.BrandTag;
import com.d2c.product.model.Series;
import com.d2c.product.query.BrandSearcher;
import com.d2c.product.query.ProductSearcher;
import com.d2c.product.search.model.SearcherDesigner;
import com.d2c.product.search.model.SearcherDesignerTag;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.query.DesignerSearchBean;
import com.d2c.product.search.query.ProductProSearchQuery;
import com.d2c.product.search.service.DesignerSearcherService;
import com.d2c.product.search.service.DesignerTagRelationSearcherService;
import com.d2c.product.search.service.DesignerTagSearcherService;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.service.BrandCategoryService;
import com.d2c.product.service.BrandService;
import com.d2c.product.service.BrandTagService;
import com.d2c.product.service.SeriesService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/showroom")
public class BrandController extends BaseController {

    @Autowired
    private BrandCategoryService brandCategoryService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private BrandTagService brandTagService;
    @Reference
    private DesignerSearcherService designerSearcherService;
    @Reference
    private DesignerTagSearcherService designerTagSearcherService;
    @Reference
    private DesignerTagRelationSearcherService designerTagRelationSearcherService;
    @Autowired
    private SeriesService seriesService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Reference
    private MemberCollectionSearcherService memberCollectionSearcherService;
    @Reference
    private MemberAttentionSearcherService memberAttentionSearcherService;

    /**
     * 设计师首页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = {"/index", ""}, method = RequestMethod.GET)
    public String index(ModelMap model, PageModel page) {
        BrandTag tag = brandTagService.findFixedOne();
        List<String> designerIds = new ArrayList<>();
        PageResult<String> relationPager = designerTagRelationSearcherService.findDesignerByTagId(tag.getId(),
                new PageModel(1, 100));
        designerIds.addAll(relationPager.getList());
        Map<Long, SearcherDesigner> map = designerSearcherService
                .findMapByIds(designerIds.toArray(new String[designerIds.size()]), null);
        List<SearcherDesigner> brandList = new ArrayList<>();
        designerIds.forEach(id -> brandList.add(map.get(Long.valueOf(id))));
        model.put("list", brandList);
        return "shop/shop_index";
    }

    /**
     * 设计师品牌列表
     *
     * @param searcher
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(BrandSearcher searcher, ModelMap model, PageModel page) {
        PageResult<SearcherDesigner> pager = new PageResult<>(page);
        if (searcher.getTagId() != null) {
            List<String> designerIds = new ArrayList<>();
            PageResult<String> relationPager = new PageResult<>(page);
            relationPager = designerTagRelationSearcherService.findDesignerByTagId(searcher.getTagId(), page);
            designerIds.addAll(relationPager.getList());
            pager.setTotalCount(relationPager.getTotalCount());
            DesignerSearchBean beanSearcher = searcher.convert2DesignerSearchBean();
            Map<Long, SearcherDesigner> map = designerSearcherService
                    .findMapByIds(designerIds.toArray(new String[designerIds.size()]), beanSearcher);
            List<SearcherDesigner> brandList = new ArrayList<>();
            designerIds.forEach(id -> brandList.add(map.get(Long.valueOf(id))));
            pager.setList(brandList);
        } else {
            DesignerSearchBean beanSearcher = searcher.convert2DesignerSearchBean();
            pager = designerSearcherService.search(beanSearcher, page);
        }
        // 设计师对应商品
        List<SearcherDesigner> brandList = pager.getList();
        JSONArray array = new JSONArray();
        for (SearcherDesigner searcherBrand : brandList) {
            this.getProductList(array, searcherBrand.getId(), new JSONObject(), searcher);
        }
        model.put("productList", array);
        model.put("pager", pager);
        return "shop/shop_list";
    }

    /**
     * 获取品牌商品
     *
     * @param array
     * @param brandId 设计师id
     * @param obj
     */
    private void getProductList(JSONArray array, Long brandId, JSONObject obj, BrandSearcher searcher) {
        ProductProSearchQuery searcherBean = new ProductProSearchQuery();
        searcherBean.setSeriesId(searcher.getSeriesId());
        PageResult<SearcherProduct> productPager = productSearcherQueryService.findSaleProductByDesigner(brandId,
                new PageModel(1, 8), searcherBean);
        JSONArray productArray = new JSONArray();
        productPager.getList().forEach(item -> productArray.add(item.toJson()));
        obj.put("products", productArray);
        obj.put("productTotal", productPager.getTotalCount());
        array.add(obj);
    }

    /**
     * 设计师标签和分类
     *
     * @param type
     * @param model
     * @return
     */
    @RequestMapping(value = "/basic/{type}", method = RequestMethod.GET)
    public String category(@PathVariable String type, ModelMap model) {
        List<BrandCategory> datas = brandCategoryService.findByType(type);
        model.put("datas", datas);
        PageResult<SearcherDesignerTag> tags = designerTagSearcherService.search(new PageModel());
        model.put("tags", tags.getList());
        model.put("basicDatas", datas);
        return "shop/shop_basic_data";
    }

    /**
     * 设计师分类
     *
     * @param model
     * @param type
     * @return
     */
    @RequestMapping(value = "/category/{type}", method = RequestMethod.GET)
    public String category(ModelMap model, @PathVariable String type) {
        List<BrandCategory> datas = brandCategoryService.findByType(type);
        model.put("datas", datas);
        return "";
    }

    /**
     * 设计师详情
     *
     * @param designerId
     * @param model
     * @return
     */
    @RequestMapping(value = "/designer/{designerId}", method = RequestMethod.GET)
    public String designer(@PathVariable final Long designerId, ModelMap model) {
        Brand brand = brandService.findById(designerId);
        if (brand != null) {
            BrandDto brandDto = new BrandDto();
            BeanUtils.copyProperties(brand, brandDto);
            try {
                MemberInfo memberInfo = this.getLoginMemberInfo();
                SearcherMemberAttention sa = memberAttentionSearcherService
                        .findByMemberAndDesignerId(memberInfo.getId(), brand.getId());
                if (sa != null && sa.getDesignerId() != null) {
                    brandDto.setAttentioned(1);
                } else {
                    brandDto.setAttentioned(0);
                }
            } catch (NotLoginException e) {
                brandDto.setAttentioned(0);
            }
            model.put("designer", brandDto);
            List<Long> seriesIds = productSearcherQueryService.findFactSeries(designerId);
            List<Series> list = new ArrayList<>();
            if (seriesIds != null && seriesIds.size() > 0) {
                list = seriesService.findByIds(seriesIds);
            }
            model.put("series", list);
        } else {
            throw new BusinessException("设计师已经下架啦！");
        }
        return "shop/shop_detial";
    }

    /**
     * 设计师详情 （二级域名）
     *
     * @param domain
     * @param model
     * @return
     */
    @RequestMapping(value = "/design/{domain}", method = RequestMethod.GET)
    public String domain(@PathVariable String domain, ModelMap model) {
        Brand brand = brandService.findByDomain(domain);
        if (brand == null && !StringUtils.isEmpty(domain)) {
            try {
                Long brandId = Long.valueOf(domain);
                brand = this.brandService.findById(brandId);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        if (brand != null) {
            if (isMobileDevice() || !this.checkDomain(domain)) {
                return "redirect:www.d2cmall.com/showroom/designer/" + brand.getId();
            }
            model.put("designer", brand);
            List<Long> seriesIds = productSearcherQueryService.findFactSeries(brand.getId());
            List<Series> list = seriesService.findByIds(seriesIds);
            model.put("series", list);
            return "shop/shop_domain";
        } else {
            throw new BusinessException(domain + "，这个设计师没有域名！");
        }
    }

    /**
     * 验证设计师域名是否合法
     *
     * @param doMain
     * @return
     */
    private boolean checkDomain(String doMain) {
        if (StringUtils.isBlank(doMain)) {
            return false;
        }
        boolean flag = false;
        try {
            String check = "[A-Z,a-z,0-9,-]*";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(doMain);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 设计师商品分页
     *
     * @param brandId
     * @param page
     * @param searcher
     * @return
     */
    @RequestMapping(value = "/product/{brandId}", method = RequestMethod.GET)
    public String brandProductList(@PathVariable Long brandId, ModelMap model, PageModel page,
                                   ProductSearcher searcher) {
        ProductProSearchQuery searcherBean = new ProductProSearchQuery();
        BeanUtils.copyProperties(searcher, searcherBean, "tagId");
        PageResult<SearcherProduct> productPager = productSearcherQueryService.findSaleProductByDesigner(brandId, page,
                searcherBean);
        model.put("products", productPager);
        return "";
    }

    /**
     * 设计师系列商品
     *
     * @param seriesId
     * @param model
     * @param page
     * @return
     */
    @RequestMapping(value = "/series/{seriesId}", method = RequestMethod.GET)
    public String seriesProductList(@PathVariable Long seriesId, ModelMap model, PageModel page) {
        ProductProSearchQuery searcher = new ProductProSearchQuery();
        searcher.setSeriesId(seriesId);
        PageResult<SearcherProduct> pager = productSearcherQueryService.search(searcher, new PageModel(1, 100));
        List<SearcherProduct> salesProductList = pager.getList();
        String[] ids = new String[salesProductList.size()];
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            for (int i = 0; i < salesProductList.size(); i++) {
                ids[i] = memberInfo.getId() + "_" + salesProductList.get(i).getProductId();
            }
            Map<Long, SearcherMemberCollection> memberCollections = memberCollectionSearcherService.findByIds(ids);
            for (SearcherProduct product : salesProductList) {
                Long productId = product.getProductId();
                if (memberCollections.containsKey(productId)) {
                    product.setCollectioned(1);
                } else {
                    product.setCollectioned(0);
                }
            }
        } catch (NotLoginException e) {
            for (SearcherProduct product : salesProductList) {
                product.setCollectioned(0);
            }
        }
        model.put("productList", salesProductList);
        return "";
    }

    /**
     * 设计师A-Z目录
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/alphabetical", method = RequestMethod.GET)
    public String alphabetical(ModelMap model) {
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
                "S", "T", "U", "V", "W", "X", "Y", "Z", "0-9"};
        List<Brand> designers = brandService.findByLetters();
        PageResult<SearcherDesignerTag> tags = designerTagSearcherService.search(new PageModel());
        model.put("tags", tags.getList());
        model.put("letters", letters);
        model.put("designers", designers);
        return "shop/shop_alphabetical";
    }

    /**
     * 设计师二级页
     *
     * @param designerId
     * @param model
     * @return
     */
    @RequestMapping(value = "/designerintro/{designerId}", method = RequestMethod.GET)
    public String designerIntro(@PathVariable Long designerId, ModelMap model) {
        Brand brand = brandService.findById(designerId);
        model.put("designer", brand);
        return "shop/shop_intro";
    }

    /**
     * 设计师app二级页
     *
     * @param designerId
     * @param model
     * @return
     */
    @RequestMapping(value = "/appintro/{designerId}", method = RequestMethod.GET)
    public String appIntro(@PathVariable Long designerId, ModelMap model) {
        Brand brand = brandService.findById(designerId);
        model.put("brand", brand);
        return "shop/shop_custom";
    }

}
