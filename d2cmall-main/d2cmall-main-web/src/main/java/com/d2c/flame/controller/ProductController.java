package com.d2c.flame.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.dto.CountDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.security.D2CSign;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.logger.model.MemberSearchInfo;
import com.d2c.logger.service.MemberSearchKeyService;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.search.model.SearcherMemberCollection;
import com.d2c.member.search.query.CommentSearchBean;
import com.d2c.member.search.service.CommentSearcherService;
import com.d2c.member.search.service.ConsultSearcherService;
import com.d2c.member.search.service.MemberCollectionSearcherService;
import com.d2c.product.dto.ProductCategoryDto;
import com.d2c.product.dto.ProductDetailDto;
import com.d2c.product.dto.ProductDto;
import com.d2c.product.model.*;
import com.d2c.product.query.ProductCategorySearcher;
import com.d2c.product.query.ProductSearcher;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.query.ProductProSearchQuery;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.search.support.ProductSearchHelp;
import com.d2c.product.service.*;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = {"/goods", "/product"})
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private ProductDetailService productDetailService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Autowired
    private ProductRelationService productRelationService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private TopCategoryService topCategoryService;
    @Autowired
    private MemberSearchKeyService memberSearchKeyService;
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private ProductTagService productTagService;
    @Reference
    private MemberCollectionSearcherService memberCollectionSearcherService;
    @Reference
    private CommentSearcherService commentSearcherService;
    @Reference
    private ConsultSearcherService consultSearcherService;
    @Autowired
    private FlashPromotionService flashPromotionService;
    @Autowired
    private CollagePromotionService collagePromotionService;

    /**
     * 商品列表
     *
     * @param searcher
     * @param page
     * @param model
     * @param appParams
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String productList(ProductSearcher searcher, PageModel page, ModelMap model, String appParams) {
        PageResult<SearcherProduct> pager = new PageResult<>(page);
        String keyword = searcher.getKeywords();
        if (searcher.getTagId() != null) {
            ProductTag tag = productTagService.findById(searcher.getTagId());
            if (tag != null && tag.getStatus().intValue() == 0) {
                searcher.setTagId(null);
            }
        }
        if (searcher.getCategoryId() != null && searcher.getCategoryId() > 0) {
            ProductCategory ct = productCategoryService.findById(searcher.getCategoryId());
            model.put("cate", ct);
            if (ct != null && searcher.getTopId() == null) {
                searcher.setTopId(ct.getTopId());
            }
        }
        if (searcher.getTopId() != null && searcher.getTopId() > 0) {
            TopCategory tc = topCategoryService.findById(searcher.getTopId());
            model.put("topCate", tc);
        }
        if (searcher.getDesignerId() != null && searcher.getDesignerId() > 0) {
            Brand ds = brandService.findById(searcher.getDesignerId());
            model.put("designer", ds);
        }
        if (!StringUtil.isEmpty(keyword)) {
            this.saveMemberSearchInfo(keyword, appParams);
        }
        ProductProSearchQuery searcherBean = searcher.convertSearchQuery();
        pager = productSearcherQueryService.search(searcherBean, page);
        // 左边栏分类，设计师
        searcherBean.setDesignerId(null);
        Map<String, List<CountDTO<?>>> countResult = productSearcherQueryService
                .findFactDesignerAndCategory(searcherBean, searcher.getCategoryId(), searcher.getTopId());
        List<CountDTO<?>> designers = countResult.get("designer");
        List<CountDTO<?>> categorys = countResult.get("category");
        List<CountDTO<?>> topCategorys = countResult.get("topCategory");
        model.put("topCategorys", topCategorys);
        model.put("categorys", categorys);
        model.put("relationDesigners", designers);
        model.put("pager", pager);
        model.put("searcher", searcher);
        return "product/product_list";
    }

    /**
     * 添加关键字到MemberSearchInfo（会员搜索信息）
     *
     * @param keyword
     * @param appParams
     */
    private void saveMemberSearchInfo(String keyword, String appParams) {
        String ip = getLoginIp();
        MemberSearchInfo memberSearchInfo = new MemberSearchInfo();
        memberSearchInfo.setKeyword(keyword.trim());
        try {
            if (!"183.129.242.178".equals(ip) && D2CSign.SEARCH_KEY.equals(appParams)) {// ip过滤
                memberSearchKeyService.insert(memberSearchInfo);
            }
        } catch (Exception e) {
        }
    }

    /**
     * 商品列表异步
     *
     * @param searcher
     * @param page
     * @param model
     * @param appParams
     * @return
     */
    @RequestMapping(value = "/pager", method = RequestMethod.GET)
    public String productNextList(ProductSearcher searcher, PageModel page, ModelMap model, String appParams) {
        PageResult<SearcherProduct> pager = new PageResult<>(page);
        String keyword = searcher.getKeywords();
        ProductProSearchQuery searcherBean = searcher.convertSearchQuery();
        pager = productSearcherQueryService.search(searcherBean, page);
        model.put("pager", pager);
        if (!StringUtils.isEmpty(keyword)) {
            this.saveMemberSearchInfo(keyword, appParams);
        }
        return "";
    }

    /**
     * 商品详情页
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String product(@PathVariable Long id, ModelMap model) {
        // 商品主体
        ProductDto product = productService.findDetailById(id);
        if (product == null || product.getMark() < 0) {
            throw new BusinessException("商品已经下架！");
        }
        // 是否收藏
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            SearcherMemberCollection sc = memberCollectionSearcherService.findByMemberAndProductId(memberInfo.getId(),
                    id);
            if (sc != null && sc.getProductId() != null) {
                product.setCollectioned(1);
            } else {
                product.setCollectioned(0);
            }
        } catch (NotLoginException e) {
            product.setCollectioned(0);
        }
        // 品类信息
        ProductCategory productCategory = productCategoryService.findById(product.getProductCategoryId());
        product.setCategoryBannerPic(productCategory.getBannerPic());
        product.setCategoryBannerUrl(productCategory.getBannerUrl());
        model.put("product", product);
        // 商品库存和活动
        SearcherProduct searcherProduct = productSearcherQueryService.findById(id.toString());
        if (searcherProduct != null) {
            model.put("productPromotion", searcherProduct);
            model.put("store", searcherProduct.getStore());
        }
        if (product.getOrderPromotionId() != null && product.getOrderPromotionId() > 0) {
            Promotion orderPromotion = promotionService.findSimpleById(product.getOrderPromotionId());
            if (!orderPromotion.isOver()) {
                List<Promotion> orderPromotions = new ArrayList<>();
                orderPromotions.add(orderPromotion);
                model.put("orderPromotions", orderPromotions);
            }
        }
        if (product.getFlashPromotionId() != null && product.getFlashPromotionId() > 0) {
            FlashPromotion flashPromotion = flashPromotionService.findById(product.getFlashPromotionId());
            if (!flashPromotion.isOver()) {
                model.put("flashPromotion", flashPromotion);
                Map<String, Integer> stockMap = productService.findFlashStore(id);
                searcherProduct.setStore(stockMap.get("flashSellStock") >= stockMap.get("flashStock") ? 0 : 1);
            }
        }
        if (product.getCollagePromotionId() != null && product.getCollagePromotionId() > 0) {
            CollagePromotion collagePromotion = collagePromotionService.findById(product.getCollagePromotionId());
            if (collagePromotion.isOver() == 1) {
                model.put("collagePromotion", collagePromotion);
                Map<String, Integer> stockMap = productService.findFlashStore(id);
                searcherProduct.setStore(stockMap.get("flashSellStock") >= stockMap.get("flashStock") ? 0 : 1);
            }
        }
        // 商品品牌
        Long designerId = product.getDesignerId();
        Brand brand = brandService.findById(designerId);
        model.put("designer", brand);
        // 相关搭配
        List<Product> relationProducts = productRelationService.findRelationProducts(id, 1);
        model.put("relationProducts", relationProducts);
        // 猜您喜欢
        ProductProSearchQuery searcher = new ProductProSearchQuery();
        searcher.setDesignerId(designerId);
        PageResult<SearcherProduct> recommendPager = productSearcherQueryService.search(searcher, new PageModel(1, 5));
        model.put("recommendProducts", recommendPager.getList());
        // 热卖商品
        searcher = new ProductProSearchQuery();
        searcher.setCategoryId(product.getProductCategoryId());
        PageResult<SearcherProduct> hotSalePager = productSearcherQueryService.search(searcher, new PageModel(1, 7));
        model.put("hotSaleProducts", hotSalePager.getList());
        // 评价咨询
        CommentSearchBean csBean = new CommentSearchBean();
        csBean.setProductId(id);
        int countComment = commentSearcherService.count(csBean);
        int countConsult = consultSearcherService.countByProductId(id);
        model.put("countComment", countComment);
        model.put("countConsult", countConsult);
        return "product/product_detail";
    }

    /**
     * 商品的详细描述
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable Long id, ModelMap model) {
        ProductDetail productDetail = productDetailService.findByProductId(id);
        ProductDetailDto dto = new ProductDetailDto();
        BeanUtils.copyProperties(productDetail, dto);
        Product product = productService.findById(id);
        ProductCategory productCategory = productCategoryService.findById(product.getProductCategoryId());
        dto.setCategoryBannerPic(productCategory.getBannerPic());
        dto.setCategoryBannerUrl(productCategory.getBannerUrl());
        model.put("product", dto);
        return "product/detail_content";
    }

    /**
     * 选中颜色/尺码，获取所有可能性的SKU组合
     *
     * @param productId
     * @param color
     * @param size
     * @param model
     * @return
     */
    @RequestMapping(value = "/getSKUInfo/{productId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String getSkuBySalesProperties(@PathVariable Long productId, Long color, Long size, ModelMap model) {
        Map<String, Object> result = productSkuService.findBySalesProperties(productId, color, size);
        model.put("result", result);
        return "";
    }

    /**
     * 广告商 商品列表
     *
     * @param page
     * @param last
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/adProductList", method = RequestMethod.GET)
    public @ResponseBody
    JSONObject adProduct(PageModel page, Long last) throws Exception {
        Date lastDate = null;
        if (last != null) {
            lastDate = new Date(last);
        }
        JSONObject root = new JSONObject();
        JSONObject productsJson = new JSONObject();
        ProductSearcher productSearcher = new ProductSearcher();
        productSearcher.setAd(1);
        productSearcher.setMark(1);
        productSearcher.setLastDate(lastDate);
        productSearcher.setOrderByStr("up_market_date DESC");
        PageResult<ProductDto> pager = productService.findAdBySearch(productSearcher, page);
        JSONArray productsArray = new JSONArray();
        for (ProductDto product : pager.getList()) {
            JSONObject productJson = new JSONObject();
            productJson.put("product_id", product.getId());
            productJson.put("product_name", product.getName());
            productJson.put("product_desc", "");
            productJson.put("sold_out", product.getMark() == 1 ? 0 : 1);
            productJson.put("orig_price", product.getOriginalPrice());
            productJson.put("brand", product.getDesigner());
            productJson.put("product_url", "http://www.d2cmall.com/product/" + product.getId());
            JSONArray imagesArray = new JSONArray();
            if (StringUtils.isNotBlank(product.getAdPic())) {
                String[] pics = product.getAdPic().split(",");
                for (String pic : pics) {
                    if (StringUtils.isNotBlank(pic)) {
                        JSONObject imageObject = new JSONObject();
                        imageObject.put("pic_url", "http://img.d2c.cn/" + pic);
                        imagesArray.add(imageObject);
                    }
                }
            }
            if (imagesArray.size() < 1) {
                String pic = product.getProductImageCover();
                JSONObject imageObject = new JSONObject();
                imageObject.put("pic_url", "http://img.d2c.cn/" + pic);
                imagesArray.add(imageObject);
            }
            productJson.put("pic_urls", imagesArray);
            productJson.put("promotion", "");
            productJson.put("market_price", "");
            productJson.put("sell_price", product.getMinPrice());
            JSONArray categoryArray = JSONArray.parseArray(product.getProductCategory());
            JSONObject category = (JSONObject) categoryArray.get(categoryArray.size() - 1);
            productJson.put("category_id", category.get("id"));
            productJson.put("category_name", category.get("name"));
            productsArray.add(productJson);
        }
        productsJson.put("product", productsArray);
        productsJson.put("current_page", pager.getPageNumber());
        productsJson.put("pages", pager.getPageCount());
        productsJson.put("page_size", pager.getPageSize());
        productsJson.put("total", pager.getTotalCount());
        root.put("products", productsJson);
        return root;
    }

    /**
     * 广告商 商品分类
     *
     * @return
     */
    @RequestMapping(value = "/productCategories", method = RequestMethod.GET)
    public @ResponseBody
    JSONObject categories() {
        JSONObject root = new JSONObject();
        JSONObject categorysJson = new JSONObject();
        ProductCategorySearcher searcher = new ProductCategorySearcher();
        searcher.setStatus(1);
        PageResult<ProductCategoryDto> pager = productCategoryService.findBySearch(searcher, new PageModel(1, 500));
        JSONArray categoryArray = new JSONArray();
        for (ProductCategory productCategory : pager.getList()) {
            JSONObject categoryJson = new JSONObject();
            categoryJson.put("category_id", productCategory.getId());
            categoryJson.put("category_name", productCategory.getName());
            categoryJson.put("category_url", "http://www.d2cmall.com/product/list?t=" + productCategory.getTopId()
                    + "&c=" + productCategory.getId());
            categoryJson.put("category_level", productCategory.getDepth());
            categoryJson.put("father_id", productCategory.getParentId() == null ? "" : productCategory.getParentId());
            categoryArray.add(categoryJson);
        }
        categorysJson.put("category", categoryArray);
        root.put("categories", categorysJson);
        return root;
    }

    /**
     * 商品货号，获取商品信息，商品扫描用
     */
    @RequestMapping(value = "/sn/{sn}", method = RequestMethod.GET)
    public String productDetail(@PathVariable String sn, ModelMap model) {
        Product product = this.productService.findOneBySn(sn);
        if (product == null) {
            throw new BusinessException("商品已经下架！");
        }
        return "redirect:/product/" + product.getId();
    }

    /**
     * 推荐商品
     *
     * @param designerId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/recommend", method = RequestMethod.GET)
    public ResponseResult recommend() {
        ResponseResult result = new ResponseResult();
        PageResult<SearcherProduct> pager = new PageResult<>();
        pager = productSearcherQueryService.search(null, new PageModel(1, 10));
        JSONArray array = new JSONArray();
        for (SearcherProduct search : pager.getList()) {
            array.add(search.toJson());
        }
        result.putPage("recommends", pager, array);
        return result;
    }

    /**
     * 商品搜索帮助
     *
     * @param keyword
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/search/help", method = RequestMethod.GET)
    public SuccessResponse searchHelp(String keyword) {
        SuccessResponse result = new SuccessResponse();
        ProductSearcher searcher = new ProductSearcher();
        searcher.setKeywords(keyword);
        ProductProSearchQuery searcherBean = searcher.convertSearchQuery();
        String[] params = new String[]{"topCategoryId", "productCategoryId", "designerId"};
        Map<String, List<ProductSearchHelp>> map = productSearcherQueryService.findProductSearchHelp(searcherBean,
                params);
        for (Map.Entry<String, List<ProductSearchHelp>> entry : map.entrySet()) {
            JSONArray array = new JSONArray();
            List<ProductSearchHelp> list = entry.getValue();
            for (ProductSearchHelp item : list) {
                array.add(item);
            }
            result.put(entry.getKey(), array);
        }
        return result;
    }

    /**
     * 获取sku的库存
     *
     * @param productId
     * @param model
     * @return
     */
    @RequestMapping(value = "/skuStore/{productId}", method = RequestMethod.GET)
    public String getSkuStore(@PathVariable Long productId, ModelMap model) {
        List<ProductSku> productSKUSet = productSkuService.findByProductId(productId);
        JSONArray array = new JSONArray();
        productSKUSet.forEach(sku -> array.add(sku.toJson()));
        model.put("result", array);
        return "";
    }

}
