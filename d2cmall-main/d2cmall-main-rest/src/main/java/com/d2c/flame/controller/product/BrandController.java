package com.d2c.flame.controller.product;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.content.model.Article;
import com.d2c.content.query.ArticleSearcher;
import com.d2c.content.service.ArticleService;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.flame.enums.ProductCategoryEnum;
import com.d2c.flame.enums.SpecialBrandTagEnum;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.query.MemberShareSearcher;
import com.d2c.member.search.model.SearcherMemberAttention;
import com.d2c.member.search.model.SearcherMemberFollow;
import com.d2c.member.search.model.SearcherMemberLike;
import com.d2c.member.search.model.SearcherMemberShare;
import com.d2c.member.search.service.MemberAttentionSearcherService;
import com.d2c.member.search.service.MemberFollowSearcherService;
import com.d2c.member.search.service.MemberLikeSearcherService;
import com.d2c.member.search.service.MemberShareSearcherService;
import com.d2c.order.model.CouponDef;
import com.d2c.order.model.Setting;
import com.d2c.order.service.CouponDefQueryService;
import com.d2c.order.service.SettingService;
import com.d2c.product.model.Promotion;
import com.d2c.product.model.TopCategory;
import com.d2c.product.query.BrandSearcher;
import com.d2c.product.query.ProductSearcher;
import com.d2c.product.query.PromotionSearcher;
import com.d2c.product.search.model.SearcherDesigner;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.query.DesignerSearchBean;
import com.d2c.product.search.query.ProductProSearchQuery;
import com.d2c.product.search.service.DesignerSearcherService;
import com.d2c.product.search.service.DesignerTagRelationSearcherService;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.service.PromotionService;
import com.d2c.product.service.TopCategoryService;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.d2c.flame.enums.ProductCategoryEnum.*;

/**
 * 品牌
 *
 * @author wwn
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/brand")
public class BrandController extends BaseController {

    @Reference
    private DesignerSearcherService designerSearcherService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Reference
    private MemberAttentionSearcherService memberAttentionSearcherService;
    @Reference
    private MemberLikeSearcherService memberLikeSearcherService;
    @Reference
    private MemberShareSearcherService memberShareSearcherService;
    @Reference
    private MemberFollowSearcherService memberFollowSearcherService;
    @Reference
    private DesignerTagRelationSearcherService designerTagRelationSearcherService;
    @Autowired
    private TopCategoryService topCategoryService;
    @Autowired
    private SettingService settingService;
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private CouponDefQueryService couponDefQueryService;
    @Autowired
    private ArticleService articleService;

    /**
     * 品牌列表
     *
     * @param searcher
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseResult list(BrandSearcher searcher, PageModel page) {
        ResponseResult result = new ResponseResult();
        PageResult<SearcherDesigner> pager = new PageResult<>(page);
        DesignerSearchBean beanSearcher = searcher.convert2DesignerSearchBean();
        if (searcher.getTagId() != null) {
            // 特殊标签ID时处理
            List<String> designerIds = processBySpecialTag(searcher.getTagId());
            if (designerIds.size() != 0) {
                pager.setTotalCount(designerIds.size());
            } else {
                PageResult<String> relationPager = new PageResult<>(page);
                relationPager = designerTagRelationSearcherService.findDesignerByTagId(searcher.getTagId(), page);
                designerIds.addAll(relationPager.getList());
                pager.setTotalCount(relationPager.getTotalCount());
            }
            Map<Long, SearcherDesigner> map = designerSearcherService
                    .findMapByIds(designerIds.toArray(new String[designerIds.size()]), beanSearcher);
            List<SearcherDesigner> brandList = new ArrayList<>();
            designerIds.forEach(id -> {
                SearcherDesigner brand = map.get(Long.valueOf(id));
                if (brand != null) {
                    brandList.add(brand);
                }
            });
            pager.setList(brandList);
        } else {
            pager = designerSearcherService.search(beanSearcher, page);
        }
        JSONArray array = new JSONArray();
        List<SearcherDesigner> brandList = pager.getList();
        try {
            // 登录的判断是否关注
            MemberInfo memberInfo = this.getLoginMemberInfo();
            String[] ids = new String[brandList.size()];
            // 用户关注设计师key
            for (int i = 0; i < brandList.size(); i++) {
                ids[i] = memberInfo.getId() + "_" + brandList.get(i).getId();
            }
            Map<Long, SearcherMemberAttention> memberAttentions = memberAttentionSearcherService.findByIds(ids);
            for (SearcherDesigner searcherBrand : brandList) {
                if (memberAttentions.containsKey(searcherBrand.getId())) {
                    searcherBrand.setAttentioned(1);
                } else {
                    searcherBrand.setAttentioned(0);
                }
                JSONObject obj = searcherBrand.toJson();
                this.getProductList(array, searcherBrand.getId(), obj, searcher);
            }
        } catch (NotLoginException e) {
            for (SearcherDesigner searcherBrand : brandList) {
                searcherBrand.setAttentioned(0);
                JSONObject obj = searcherBrand.toJson();
                this.getProductList(array, searcherBrand.getId(), obj, searcher);
            }
        }
        result.putPage("designers", pager, array);
        return result;
    }

    /**
     * 处理特殊标签，获取品牌ID
     *
     * @param tagId
     * @return
     */
    private List<String> processBySpecialTag(Long tagId) {
        List<String> brands = new ArrayList<>();
        if (SpecialBrandTagEnum.isBrand4NewProduct(tagId)) {
            Long[] topIds = null;
            switch (SpecialBrandTagEnum.getSubModuleName(tagId)) {
                case "FEMALE":
                    topIds = getTopIds(FEMALE, KIDS);
                    break;
                case "MALE":
                    topIds = getTopIds(MALE);
                    break;
                case "FURNITURE":
                    topIds = getTopIds(APPLIANCES, COSMETIC, FURNITURE, PERSONAL);
                    break;
                case "BAG":
                    topIds = getTopIds(BAG, SHOE, ACC, JEWELRY);
                    break;
                default:
                    break;
            }
            List<Long> list = productSearcherQueryService.findNewUpBrandByTopCate(7, 10, topIds);
            brands = list.stream().map(id -> id.toString()).collect(Collectors.toList());
        }
        return brands;
    }

    private Long[] getTopIds(ProductCategoryEnum... ens) {
        List<Long> ids = new ArrayList<>();
        for (ProductCategoryEnum en : ens) {
            if (en.getTopId() == null) {
                TopCategory top = topCategoryService.findByCode(en.getCode());
                if (top == null) {
                    en.setTopId(-1L);
                } else {
                    en.setTopId(top.getId());
                }
            }
            if (en.getTopId() < 0) {
                continue;
            }
            ids.add(en.getTopId());
        }
        return ids.toArray(new Long[]{});
    }

    /**
     * 获取品牌商品
     *
     * @param array
     * @param brandId
     * @param obj
     * @param searcher
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
     * 品牌详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseResult detail(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        // 品牌主体
        SearcherDesigner brand = designerSearcherService.findById(id.toString());
        if (brand == null) {
            throw new BusinessException("品牌不存在！");
        }
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            SearcherMemberAttention memberAttention = memberAttentionSearcherService
                    .findByMemberAndDesignerId(memberInfo.getId(), brand.getId());
            if (memberAttention != null && memberAttention.getDesignerId() != null) {
                brand.setAttentioned(1);
            } else {
                brand.setAttentioned(0);
            }
        } catch (NotLoginException e) {
            brand.setAttentioned(0);
        }
        // 关注品牌的头像
        PageResult<SearcherMemberAttention> pager = memberAttentionSearcherService.findByDesignerId(id,
                new PageModel(1, 10));
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.getHeadPic() == null ? "" : item.getHeadPic()));
        result.put("headPics", array);
        result.put("brand", brand.toJson());
        return result;
    }

    /**
     * 品牌商品列表
     *
     * @param brandId
     * @param page
     * @param searcher
     * @return
     */
    @RequestMapping(value = "/product/{brandId}", method = RequestMethod.GET)
    public ResponseResult productList(@PathVariable Long brandId, PageModel page, ProductSearcher searcher) {
        ResponseResult result = new ResponseResult();
        if (searcher.getSeriesId() != null && searcher.getSeriesId() == 0) {
            searcher.setSeriesId(null);
        }
        ProductProSearchQuery searcherBean = searcher.convertSearchQuery();
        PageResult<SearcherProduct> productPager = productSearcherQueryService.findSaleProductByDesigner(brandId, page,
                searcherBean);
        JSONArray productArray = new JSONArray();
        productPager.getList().forEach(item -> productArray.add(item.toJson()));
        result.putPage("products", productPager, productArray);
        // 全局返利系数
        Setting ratio = settingService.findByCode(Setting.REBATERATIO);
        result.put("ratio", Setting.defaultValue(ratio, new Integer(1)));
        return result;
    }

    /**
     * 品牌系列列表
     *
     * @param searcher
     * @param page
     * @return
     */
    @RequestMapping(value = "/series/list", method = RequestMethod.GET)
    public ResponseResult seriesList(ProductSearcher searcher, PageModel page) {
        ResponseResult result = new ResponseResult();
        ProductProSearchQuery searcherBean = searcher.convertSearchQuery();
        page.setPageSize(40);
        PageResult<JSONObject> pager = productSearcherQueryService.seriesList(searcherBean, page);
        result.putPage("series", pager, pager.getList());
        return result;
    }

    /**
     * 品牌的买家秀
     *
     * @param brandId
     * @param page
     * @return
     */
    @RequestMapping(value = "/share/{brandId}", method = RequestMethod.GET)
    public ResponseResult shareList(@PathVariable Long brandId, PageModel page) {
        ResponseResult result = new ResponseResult();
        MemberShareSearcher searcher = new MemberShareSearcher();
        searcher.setDesignerId(brandId);
        searcher.setStatus(1);
        Long memberInfoId = null;
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            searcher.setMemberId(memberInfo.getId());
            searcher.setAllList(true);
            memberInfoId = memberInfo.getId();
        } catch (Exception e) {
        }
        JSONArray shareArray = new JSONArray();
        PageResult<SearcherMemberShare> pager = memberShareSearcherService.search(searcher.initSearchQuery(), page);
        List<SearcherMemberShare> memberShares = pager.getList();
        if (memberInfoId != null) {
            String[] ids = new String[memberShares.size()];
            for (int i = 0; i < memberShares.size(); i++) {
                ids[i] = memberInfoId + "_" + memberShares.get(i).getId();
            }
            Map<Long, SearcherMemberLike> memberLikes = memberLikeSearcherService.findByIds(ids);
            for (SearcherMemberShare share : pager.getList()) {
                Long shareId = share.getId();
                if (memberLikes.containsKey(shareId)) {
                    share.setLiked(1);
                } else {
                    share.setLiked(0);
                }
                shareArray.add(share.toJson());
            }
        } else {
            for (SearcherMemberShare share : pager.getList()) {
                share.setLiked(0);
                shareArray.add(share.toJson());
            }
        }
        result.putPage("membershares", pager, shareArray);
        return result;
    }

    /**
     * 作品和买家秀数量
     *
     * @param brandId
     * @return
     */
    @RequestMapping(value = "/count/{brandId}", method = RequestMethod.GET)
    public ResponseResult count(@PathVariable Long brandId) {
        ResponseResult result = new ResponseResult();
        int productCount = productSearcherQueryService.countSaleProductByDesigner(brandId);
        MemberShareSearcher searcher = new MemberShareSearcher();
        searcher.setDesignerId(brandId);
        searcher.setStatus(1);
        int shareCount = memberShareSearcherService.count(searcher.initSearchQuery());
        result.put("productCount", productCount);
        result.put("shareCount", shareCount);
        return result;
    }

    /**
     * 关注品牌的会员
     *
     * @param brandId
     * @param page
     * @return
     */
    @RequestMapping(value = "/attention/list/{brandId}", method = RequestMethod.GET)
    public ResponseResult attentionList(@PathVariable Long brandId, PageModel page) {
        ResponseResult result = new ResponseResult();
        PageResult<SearcherMemberAttention> pager = memberAttentionSearcherService.findByDesignerId(brandId, page);
        List<SearcherMemberAttention> list = pager.getList();
        JSONArray array = new JSONArray();
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            Long memberInfoId = memberInfo.getId();
            String[] memberFollowids = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                memberFollowids[i] = memberInfoId + "_" + list.get(i).getMemberId();
            }
            Map<Long, SearcherMemberFollow> follows = memberFollowSearcherService.findByIds(memberFollowids);
            for (SearcherMemberAttention att : pager.getList()) {
                JSONObject obj = att.toJson();
                if (follows.containsKey(att.getMemberId())) {
                    obj.put("follow",
                            follows.get(att.getMemberId()).getFriends() == 1
                                    ? SearcherMemberFollow.FollowType.EACHPOWDER.getCode()
                                    : SearcherMemberFollow.FollowType.FOLLOWED.getCode());
                } else {
                    obj.put("follow", 0);
                }
                this.findByMemberId(obj, att.getMemberId());
                array.add(obj);
            }
        } catch (NotLoginException e) {
            for (SearcherMemberAttention att : pager.getList()) {
                JSONObject obj = att.toJson();
                obj.put("follow", 0);
                array.add(obj);
            }
        }
        result.putPage("attentions", pager, array);
        return result;
    }

    /**
     * 品牌的活动和优惠券
     *
     * @param brandId
     * @return
     */
    @RequestMapping(value = "/relation/{brandId}", method = RequestMethod.GET)
    public ResponseResult promotionInfo(@PathVariable Long brandId) {
        ResponseResult result = new ResponseResult();
        PromotionSearcher promotionSearcher = new PromotionSearcher();
        promotionSearcher.setBrandId(brandId);
        promotionSearcher.setBeginEndTime(new Date());
        promotionSearcher.setEnable(true);
        PageResult<Promotion> pager = promotionService.findBySearcher(promotionSearcher, new PageModel());
        JSONArray promotionArray = new JSONArray();
        pager.getList().forEach(p -> promotionArray.add(p.toJson()));
        result.put("promotions", promotionArray);
        List<CouponDef> list = couponDefQueryService.findAvailableByBrandId(brandId, 1);
        JSONArray couponArray = new JSONArray();
        list.forEach(cp -> couponArray.add(cp.toJson()));
        result.put("couponDefs", couponArray);
        ArticleSearcher search = new ArticleSearcher();
        search.setBrandId(brandId);
        search.setPublished(true);
        PageResult<Article> articlePager = articleService.findBySearcher(search, new PageModel());
        JSONArray articleArray = new JSONArray();
        articlePager.getList().forEach(a -> articleArray.add(a.toJson()));
        result.put("articles", articleArray);
        // 如果没有活动，优惠券，文章就显示其他品牌活动
        if (promotionArray.size() == 0 && couponArray.size() == 0 && articleArray.size() == 0) {
            promotionSearcher.setBrandId(null);
            promotionSearcher.setEndStartTime(new Date());
            promotionSearcher.setBindBrand(true);
            PageResult<Promotion> otherPromotionpager = promotionService.findBySearcher(promotionSearcher,
                    new PageModel());
            JSONArray otherPromotionArray = new JSONArray();
            otherPromotionpager.getList().forEach(p -> otherPromotionArray.add(p.toJson()));
            result.put("otherPromotions", otherPromotionArray);
        }
        return result;
    }

    /**
     * 查询用户最新一条买家秀
     *
     * @param obj
     * @param memberId
     */
    private void findByMemberId(JSONObject obj, Long memberId) {
        SearcherMemberShare share = memberShareSearcherService.findLastedByMemberId(memberId);
        if (StringUtil.isNotBlank(share.getDescription())) {
            obj.put("memberShare", share.getDescription());
        } else if (StringUtils.isNotBlank(share.getPic())) {
            obj.put("memberShare", "发布了图片");
        } else if (StringUtil.isNotBlank(share.getVideo())) {
            obj.put("memberShare", "发布了视频");
        } else {
            obj.put("memberShare", "暂无动态");
        }
    }

}
