package com.d2c.flame.controller.product;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.cache.redis.RedisHandler;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.security.D2CSign;
import com.d2c.content.model.Theme.ThemeType;
import com.d2c.content.search.model.SearcherTheme;
import com.d2c.content.search.query.ThemeSearcherBean;
import com.d2c.content.search.service.ThemeSearcherService;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.logger.model.MemberSearchInfo;
import com.d2c.logger.model.Remind;
import com.d2c.logger.model.Remind.RemindType;
import com.d2c.logger.service.MemberSearchKeyService;
import com.d2c.logger.service.RemindService;
import com.d2c.member.dto.CommentDto;
import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.member.model.CommentReply;
import com.d2c.member.model.Consult;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.query.CommentSearcher;
import com.d2c.member.query.MemberShareSearcher;
import com.d2c.member.search.model.*;
import com.d2c.member.search.query.CommentSearchBean;
import com.d2c.member.search.query.ConsultSearchBean;
import com.d2c.member.search.query.MemberShareSearchBean;
import com.d2c.member.search.service.*;
import com.d2c.member.service.ConsultService;
import com.d2c.order.model.CouponDef;
import com.d2c.order.model.Setting;
import com.d2c.order.service.CouponDefRelationService;
import com.d2c.order.service.SettingService;
import com.d2c.order.third.payment.alipay.core.pcwap.AlipayBase;
import com.d2c.order.third.payment.alipay.core.pcwap.AlipayCore;
import com.d2c.order.third.payment.alipay.sgin.BASE64;
import com.d2c.product.dto.ProductCombDto;
import com.d2c.product.dto.ProductDto;
import com.d2c.product.model.*;
import com.d2c.product.model.ProductRelation.RelationType;
import com.d2c.product.query.ProductSearcher;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.model.SearcherProductCategory;
import com.d2c.product.search.model.SearcherTopCategory;
import com.d2c.product.search.query.CategorySearchBean;
import com.d2c.product.search.query.ProductProSearchQuery;
import com.d2c.product.search.service.ProductCategorySearcherService;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.search.service.TopCategorySearcherService;
import com.d2c.product.service.*;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 商品
 *
 * @author wwn
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/product")
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private ProductTagService productTagService;
    @Autowired
    private ProductCombService productCombService;
    @Autowired
    private ProductRelationService productRelationService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private BrandCategoryService brandCategoryService;
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private CouponDefRelationService couponDefRelationService;
    @Autowired
    private SettingService settingService;
    @Autowired
    private ConsultService consultService;
    @Reference
    private ConsultSearcherService consultSearcherService;
    @Reference
    private CommentSearcherService commentSearcherService;
    @Reference
    private CommentReplySearcherService commentReplySearcherService;
    @Autowired
    private MemberSearchKeyService memberSearchInfoService;
    @Reference
    private MemberLikeSearcherService memberLikeSearcherService;
    @Reference
    private MemberCollectionSearcherService memberCollectionSearcherService;
    @Reference
    private MemberShareSearcherService memberShareSearcherService;
    @Reference
    private TopCategorySearcherService topCategorySearcherService;
    @Reference
    private ProductCategorySearcherService productCategorySearcherService;
    @Reference
    private ThemeSearcherService themeSearcherService;
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private RemindService remindService;
    @Autowired
    private FlashPromotionService flashPromotionService;
    @Autowired
    private CollagePromotionService collagePromotionService;
    @Reference
    private ProductThirdService productThirdService;
    @Autowired
    private RedisHandler<String, Integer> redisHandler;

    /**
     * 商品详情页
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseResult product(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        // 商品本体
        Long productId = id;
        ProductDto dto = new ProductDto();
        Product product = productService.findById(productId);
        if (product == null || product.getMark() < 0) {
            throw new BusinessException("商品已下架！");
        }
        BeanUtils.copyProperties(product, dto);
        // 是否收藏
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            SearcherMemberCollection smc = memberCollectionSearcherService.findByMemberAndProductId(memberInfo.getId(),
                    productId);
            if (smc != null && smc.getProductId() != null) {
                dto.setCollectioned(1);
            } else {
                dto.setCollectioned(0);
            }
        } catch (NotLoginException e) {
            dto.setCollectioned(0);
        }
        // 商品活动
        SearcherProduct searcherProduct = productSearcherQueryService.findById(productId.toString());
        List<JSONObject> promotions = new ArrayList<>();
        if (product.getGoodPromotionId() != null && product.getGoodPromotionId() > 0) {
            Promotion productPromotion = promotionService.findSimpleById(product.getGoodPromotionId());
            if (!productPromotion.isOver()) {
                promotions.add(productPromotion.toJson());
            }
            if (searcherProduct.isPromotionSoon() || !productPromotion.isOver()) {
                result.put("soonPromotion", productPromotion.toJson());
            }
        }
        if (product.getOrderPromotionId() != null && product.getOrderPromotionId() > 0) {
            Promotion orderPromotion = promotionService.findSimpleById(product.getOrderPromotionId());
            if (!orderPromotion.isOver()) {
                promotions.add(orderPromotion.toJson());
            }
        }
        if (product.getFlashPromotionId() != null && product.getFlashPromotionId() > 0) {
            FlashPromotion flashPromotion = flashPromotionService.findById(product.getFlashPromotionId());
            if (!flashPromotion.isEnd()) {
                result.put("flashPromotion", flashPromotion.toJson());
                Map<String, Integer> stockMap = productService.findFlashStore(id);
                searcherProduct.setStore(stockMap.get("flashSellStock") >= stockMap.get("flashStock") ? 0 : 1);
            }
        }
        if (product.getCollagePromotionId() != null && product.getCollagePromotionId() > 0) {
            CollagePromotion collagePromotion = collagePromotionService.findById(product.getCollagePromotionId());
            if (collagePromotion.isOver() == 1) {
                result.put("collagePromotion", collagePromotion.toJson());
                Map<String, Integer> stockMap = productService.findFlashStore(id);
                searcherProduct.setStore(stockMap.get("flashSellStock") >= stockMap.get("flashStock") ? 0 : 1);
            }
        }
        JSONObject productJson = dto.toJson(searcherProduct);
        result.put("product", productJson);
        result.put("promotions", promotions);
        // 商品优惠券
        List<CouponDef> productDefs = couponDefRelationService.findCouponsByProduct(product.getId());
        List<CouponDef> designerDefs = couponDefRelationService.findCouponsByDesigner(product.getDesignerId());
        productDefs.addAll(designerDefs);
        List<JSONObject> coupons = new ArrayList<>();
        productDefs.forEach(item -> coupons.add(item.toJson()));
        result.put("coupons", coupons);
        // 商品品牌
        Long designerId = product.getDesignerId();
        Brand brand = brandService.findById(designerId);
        JSONObject brandJson = brand.toJson();
        Integer salesCount = productSearcherQueryService.countSaleProductByDesigner(designerId);
        brandJson.put("salesCount", salesCount);
        result.put("brand", brandJson);
        // 国家图标
        if (brand != null) {
            BrandCategory country = brandCategoryService.findById(Long.parseLong(brand.getCountry()));
            result.put("country", country.toJson());
        }
        // 品牌推荐商品
        ProductProSearchQuery searcher = new ProductProSearchQuery();
        searcher.setDesignerId(designerId);
        PageResult<SearcherProduct> pager = productSearcherQueryService.search(searcher, new PageModel(1, 6));
        JSONArray recommendProducts = new JSONArray();
        pager.getList().forEach(item -> recommendProducts.add(item.toJson()));
        result.put("recommendProducts", recommendProducts);
        // 组合商品
        result.put("productComb", getCombProduct(productId));
        // 施力说微信号
        Setting setting = settingService.findByCode(Setting.BOSSWEIXIN);
        JSONObject obj = new JSONObject();
        obj.put("code", "D2C8001");
        obj.put("word", "有任何问题欢迎随时联系，朋友圈常常会有福利哦");
        result.put("weixin", Setting.defaultValue(setting, obj.toJSONString()));
        // 全局返利系数
        Setting ratio = settingService.findByCode(Setting.REBATERATIO);
        result.put("ratio", Setting.defaultValue(ratio, new Integer(1)));
        return result;
    }

    /**
     * 商品详情 h5
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public ResponseResult productDetail(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        ProductDetail pd = productDetailService.findByProductId(id);
        if (pd == null) {
            throw new BusinessException("商品不存在！");
        }
        JSONObject res = pd.toJsonDetail();
        result.put("productData", res);
        return result;
    }

    /**
     * 商品选择 sku
     *
     * @param productId
     * @param color
     * @param size
     * @return
     */
    @RequestMapping(value = "/{productId}/skuInfo", method = RequestMethod.POST)
    public ResponseResult getSkuInfo(@PathVariable Long productId, Long color, Long size) {
        ResponseResult result = new ResponseResult();
        Map<String, Object> data = productSkuService.findBySalesProperties(productId, color, size);
        result.put("compareStore", 3);
        result.put("skuInfo", data);
        return result;
    }

    /**
     * 商品列表
     *
     * @param searcher
     * @param page
     * @param appParams
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseResult list(ProductSearcher searcher, PageModel page, String appParams) {
        ResponseResult result = new ResponseResult();
        if (searcher.getTagId() != null) {
            ProductTag tag = productTagService.findById(searcher.getTagId());
            if (tag != null && tag.getStatus().intValue() == 0) {
                searcher.setTagId(null);
            }
        }
        if (StringUtil.isNotBlank(searcher.getKeywords())) {
            if (checkEncrypt(searcher.getKeywords(), appParams)) {
                this.saveMemberSearchInfo(searcher.getKeywords());
            }
        }
        // 初始化商品排序
        ProductProSearchQuery searcherBean = searcher.convertSearchQuery();
        if (searcherBean.getHasPromotion() != null && searcherBean.getHasPromotion()) {
            searcherBean.setPromotionDate(new Date());
        }
        PageResult<SearcherProduct> pager = productSearcherQueryService.search(searcherBean, page);
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toJson()));
        result.putPage("products", pager, array);
        // 全局返利系数
        Setting ratio = settingService.findByCode(Setting.REBATERATIO);
        result.put("ratio", Setting.defaultValue(ratio, new Integer(1)));
        Setting recSearch = settingService.findByCode(Setting.PRODUCTRECSEARCH);
        if (recSearch != null && recSearch.getStatus() == 1) {
            result.put("recSearch", JSON.parseArray(recSearch.getValue()));
        }
        return result;
    }

    /**
     * 验证是否是app发送的
     *
     * @param keyword
     * @param appParams
     * @return
     */
    private boolean checkEncrypt(String keyword, String appParams) {
        Map<String, String> keywordString = new HashMap<>();
        keywordString.put("keyword", keyword);
        Map<String, String> sPara = AlipayCore.parasFilter(keywordString);
        String mySign = AlipayBase.BuildMysign(sPara, D2CSign.SECRET_KEY);
        sPara.put("sign", mySign);
        String params;
        try {
            params = BASE64.encode(AlipayCore.createLinkString(sPara).getBytes("UTF-8"));
            if (params.equalsIgnoreCase(appParams)) {
                return true;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 添加关键字到会员搜索日志
     *
     * @param keyword
     */
    private void saveMemberSearchInfo(String keyword) {
        String ip = getLoginIp();
        MemberSearchInfo memberSearchInfo = new MemberSearchInfo();
        memberSearchInfo.setKeyword(keyword.trim());
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            memberSearchInfo.setMemberInfoId(memberInfo.getId());
            memberSearchInfo.setCreator(memberInfo.getLoginCode());
            memberSearchInfo.setCreateDate(new Date());
        } catch (NotLoginException e) {
        }
        memberSearchInfo.setIp(ip);
        try {
            if (!"183.129.242.178".equals(ip)) {// ip过滤
                memberSearchInfoService.insert(memberSearchInfo);
            }
        } catch (Exception e) {
        }
    }

    /**
     * 详情页数量
     *
     * @param productId
     * @return
     */
    @RequestMapping(value = "/counts/{productId}", method = RequestMethod.GET)
    public ResponseResult counts(@PathVariable Long productId) {
        ResponseResult result = new ResponseResult();
        // 咨询的数量
        int consultCount = consultSearcherService.countByProductId(productId);
        MemberShareSearchBean search = new MemberShareSearchBean();
        // 买家秀，除去评论部分
        search.setProductId(productId);
        search.setType(1);
        search.setStatus(1);
        // 评价的
        CommentSearchBean commentSearch = new CommentSearchBean();
        commentSearch.setProductId(productId);
        commentSearch.setVerified(true);
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            if (memberInfo != null) {
                search.setMemberId(memberInfo.getId());
                search.setAllList(true);
                commentSearch.setMemberId(memberInfo.getId());
                commentSearch.setAllList(true);
            }
        } catch (NotLoginException e) {
        }
        int shareCount = memberShareSearcherService.count(search);
        // 所有评论
        int totalCount = commentSearcherService.count(commentSearch);
        // 有图评论
        commentSearch.setHasPic(1);
        int picCount = commentSearcherService.count(commentSearch);
        result.put("consultCount", consultCount);
        result.put("shareCount", shareCount);
        result.put("picCount", picCount + shareCount);
        result.put("totalCount", totalCount + shareCount);
        return result;
    }

    /**
     * 以图搜图
     *
     * @param picUrl
     * @param page
     * @return
     */
    @RequestMapping(value = "/searchpic/list", method = RequestMethod.GET)
    public ResponseResult searchByPic(String picUrl, PageModel page) {
        ResponseResult result = new ResponseResult();
        // 用户上传图片搜索
        if (StringUtil.isNotBlank(picUrl)) {
            createLog(picUrl);
            try {
                PageResult<String> productIds = productThirdService.searchByUrl(picUrl, page);
                // 如果没找到相似的就返回空
                if (productIds.getList() != null && productIds.getList().size() > 0) {
                    List<SearcherProduct> list = productSearcherQueryService.findByIds(productIds.getList(), 1);
                    Map<String, SearcherProduct> products = new HashMap<>();
                    for (SearcherProduct product : list) {
                        products.put(product.getId().toString(), product);
                    }
                    JSONArray array = new JSONArray();
                    for (String id : productIds.getList()) {
                        if (products.get(id) != null) {
                            array.add(products.get(id).toJson());
                        }
                    }
                    result.putPage("products", productIds, array);
                    // 全局返利系数
                    Setting ratio = settingService.findByCode(Setting.REBATERATIO);
                    result.put("ratio", Setting.defaultValue(ratio, new Integer(1)));
                    return result;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        throw new BusinessException("未找到相似商品");
    }

    /**
     * 根据IP记录查询次数
     */
    private void createLog(String picUrl) {
        try {
            String ip = getLoginIp();
            // 根据IP记录查询次数，1分钟内超过30的就不允许查了
            Integer count = 0;
            if (redisHandler.get("seache_product_" + ip) != null) {
                count = redisHandler.get("seache_product_" + ip);
            }
            if (count > 30) {
                throw new BusinessException("搜索太频繁，请稍后再试！");
            } else {
                redisHandler.setInMinutes("seache_product_" + ip, count + 1, 1);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 商品筛选关键字
     *
     * @param searcher
     * @return
     */
    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public ResponseResult filter(ProductProSearchQuery searcher) {
        ResponseResult result = new ResponseResult();
        JSONObject obj = productSearcherQueryService.filterProperties(searcher);
        result.put("filter", obj);
        return result;
    }

    /**
     * 服务筛选列表
     *
     * @param searcher
     * @return
     */
    @RequestMapping(value = "/filter/list", method = RequestMethod.GET)
    public ResponseResult serviceList(ProductSearcher searcher) {
        ResponseResult result = new ResponseResult();
        JSONObject obj = new JSONObject();
        ProductProSearchQuery searcherBean = searcher.convertSearchQuery();
        if (searcherBean.getHasPromotion() != null && searcherBean.getHasPromotion()) {
            searcherBean.setPromotionDate(new Date());
        }
        // 服务和商品类型
        obj = productSearcherQueryService.filterService(searcherBean);
        CategorySearchBean cateSearch = new CategorySearchBean();
        PageResult<SearcherTopCategory> topCategory = topCategorySearcherService.search(cateSearch, new PageModel());
        JSONArray array = new JSONArray();
        for (SearcherTopCategory top : topCategory.getList()) {
            JSONObject category = new JSONObject();
            category.put("topCatagory", top);
            category.put("productCategory", productCategorySearcherService.findByTopId(top.getId(), null));
            array.add(category);
        }
        result.put("filter", obj);
        result.put("category", array);
        return result;
    }

    /**
     * 品牌筛选列表
     *
     * @param searcher
     * @param page
     * @return
     */
    @RequestMapping(value = "/brand/list", method = RequestMethod.GET)
    public ResponseResult brandList(ProductSearcher searcher, PageModel page) {
        ResponseResult result = new ResponseResult();
        ProductProSearchQuery searcherBean = searcher.convertSearchQuery();
        PageResult<JSONObject> pager = productSearcherQueryService.brandList(searcherBean, page);
        result.put("pager", pager);
        return result;
    }

    /**
     * 搭配商品和组合商品
     *
     * @param productId
     * @param page
     * @return
     */
    @RequestMapping(value = "/recommend/product", method = RequestMethod.POST)
    public ResponseResult recList(Long productId, PageModel page) {
        ResponseResult result = new ResponseResult();
        // 搭配商品
        List<Long> list = productRelationService.findBySourceId(productId, RelationType.PRODUCT.name());
        List<String> productIds = new ArrayList<>();
        list.forEach(item -> productIds.add(item.toString()));
        List<SearcherProduct> searcherProducts = productSearcherQueryService.findByIds(productIds, 1);
        JSONArray array = new JSONArray();
        for (SearcherProduct product : searcherProducts) {
            if (product.getMark() == 1 && product.getSearch() == 1) {
                array.add(product.toRelationJson());
            }
        }
        result.put("relationProducts", array);
        result.put("productComb", getCombProduct(productId));
        return result;
    }

    /**
     * 组合商品
     */
    private JSONArray getCombProduct(Long productId) {
        // 组合商品
        List<Long> sourceIds = productRelationService.findSourceIdsByRelationId(productId,
                RelationType.PRODUCTCOMB.name());
        JSONArray combList = new JSONArray();
        for (Long id : sourceIds) {
            ProductCombDto productComb = productCombService.findDtoById(id);
            if (productComb != null && productComb.getMark() > 0) {
                int store = 1;
                int mark = 1;
                JSONArray combArray = new JSONArray();
                for (Product product : productComb.getProducts()) {
                    SearcherProduct searcherProduct = productSearcherQueryService.findById(product.getId().toString());
                    if (product.getMark() <= 0) {
                        mark = 0;
                        break;
                    }
                    if (searcherProduct.getStore() == 0) {
                        store = 0;
                    }
                    ProductDto dto = new ProductDto();
                    BeanUtils.copyProperties(product, dto);
                    combArray.add(dto.toJson(searcherProduct));
                }
                JSONObject combObj = productComb.toJson();
                if (mark == 1) {
                    combObj.put("products", combArray);
                    combObj.put("store", store);
                }
                combObj.put("mark", mark);
                combList.add(combObj);
            }
        }
        return combList;
    }

    /**
     * 组合商品
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/comb/{id}", method = RequestMethod.GET)
    public ResponseResult productComb(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        ProductCombDto productComb = productCombService.findDtoById(id);
        if (productComb == null || productComb.getMark() < 0) {
            throw new BusinessException("商品不存在！");
        }
        int store = 1;
        JSONArray combArray = new JSONArray();
        int mark = 1;
        for (Product product : productComb.getProducts()) {
            SearcherProduct searcherProduct = productSearcherQueryService.findById(product.getId().toString());
            if (product.getMark() <= 0) {
                mark = 0;
            }
            if (searcherProduct.getStore() == 0) {
                store = 0;
            }
            ProductDto dto = new ProductDto();
            BeanUtils.copyProperties(product, dto);
            combArray.add(dto.toJson(searcherProduct));
        }
        JSONObject combObj = productComb.toJson();
        combObj.put("products", combArray);
        combObj.put("store", store);
        combObj.put("mark", mark);
        result.put("productComb", combObj);
        return result;
    }

    /**
     * 咨询详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/consult/detail/{id}", method = RequestMethod.GET)
    public ResponseResult consultDetail(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        SearcherConsult consult = consultSearcherService.findById(id.toString());
        result.put("consult", consult.toJson());
        return result;
    }

    /**
     * 新增咨询
     *
     * @param consult
     * @param appTerminal
     * @param appVersion
     * @return
     */
    @RequestMapping(value = "/consult/insert", method = RequestMethod.POST)
    public ResponseResult consultInsert(Consult consult, String appTerminal, String appVersion) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo;
        try {
            memberInfo = this.getLoginMemberInfo();
            consult.setNickName(memberInfo.getDisplayName());
            consult.setMemberId(memberInfo.getId());
            consult.setHeadPic(memberInfo.getHeadPic());
        } catch (NotLoginException e) {
            consult.setNickName("匿名用户");
            consult.setMemberId(0L);
        }
        consult.setDevice(DeviceTypeEnum.divisionDevice(appTerminal));
        consult.setAppVersion(appVersion);
        Product product = productService.findById(consult.getProductId());
        consult.setProductName(product.getName());
        consult.setProductPic(product.getProductImageListFirst());
        consult.setInernalSn(product.getInernalSn());
        Brand brand = brandService.findById(product.getDesignerId());
        consult.setOperation(brand.getOperation());
        consultService.insert(consult);
        return result;
    }

    /**
     * 商品咨询列表
     *
     * @param page
     * @param productId
     * @return
     */
    @RequestMapping(value = "/consult/{productId}", method = RequestMethod.GET)
    public ResponseResult consultList(PageModel page, @PathVariable Long productId) {
        ResponseResult result = new ResponseResult();
        ConsultSearchBean search = new ConsultSearchBean();
        search.setStatus(2);
        search.setProductId(productId);
        PageResult<SearcherConsult> pager = consultSearcherService.search(search, page);
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toJson()));
        result.putPage("consults", pager, array);
        return result;
    }

    /**
     * 商品买家秀列表
     *
     * @param productId
     * @param page
     * @return
     */
    @RequestMapping(value = "/share/{productId}", method = RequestMethod.GET)
    public ResponseResult shareList(@PathVariable Long productId, PageModel page) {
        ResponseResult result = new ResponseResult();
        MemberShareSearcher searcher = new MemberShareSearcher();
        searcher.setProductId(productId);
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
            for (SearcherMemberShare share : memberShares) {
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
     * 商品评论列表
     *
     * @param page
     * @param searcher
     * @param productId
     * @return
     */
    @RequestMapping(value = "/comment/{productId}", method = RequestMethod.GET)
    public ResponseResult commentList(PageModel page, CommentSearcher searcher, @PathVariable Long productId) {
        ResponseResult result = new ResponseResult();
        CommentSearchBean searchBean = new CommentSearchBean();
        searchBean.setHasPic(searcher.getHasPic());
        searchBean.setProductId(productId);
        searchBean.setVerified(true);
        try {
            MemberInfo member = this.getLoginMemberInfo();
            searchBean.setMemberId(member.getId());
            searchBean.setAllList(true);
        } catch (Exception e) {
        }
        JSONArray commentArray = new JSONArray();
        PageResult<SearcherComment> commentsPager = commentSearcherService.search(searchBean, page);
        CommentSearchBean replyBean = new CommentSearchBean();
        replyBean.setCommentIds(new long[commentsPager.getList().size()]);
        for (int i = 0; i < commentsPager.getList().size(); i++) {
            SearcherComment comment = commentsPager.getList().get(i);
            replyBean.getCommentIds()[i] = comment.getId();
        }
        PageResult<SearcherCommentReply> replyPager = commentReplySearcherService.search(replyBean, page);
        HashMap<Long, List<CommentReply>> replyMap = new HashMap<>();
        for (int j = 0; j < replyPager.getList().size(); j++) {
            SearcherCommentReply replySearcher = replyPager.getList().get(j);
            CommentReply reply = new CommentReply();
            BeanUtils.copyProperties(replySearcher, reply);
            if (replyMap.get(reply.getCommentId()) == null) {
                replyMap.put(reply.getCommentId(), new ArrayList<CommentReply>());
            }
            replyMap.get(reply.getCommentId()).add(reply);
        }
        for (int i = 0; i < commentsPager.getList().size(); i++) {
            SearcherComment comment = commentsPager.getList().get(i);
            CommentDto comDto = new CommentDto();
            BeanUtils.copyProperties(comment, comDto);
            List<CommentReply> commentReplys = replyMap.get(comDto.getId());
            if (commentReplys == null)
                commentReplys = new ArrayList<>();
            comDto.setCommentReplys(commentReplys);
            commentArray.add(comDto.toJson());
        }
        result.putPage("comments", commentsPager, commentArray);
        return result;
    }

    /**
     * 商品标签列表
     *
     * @param type
     * @return
     */
    @RequestMapping(value = "/tag/list", method = RequestMethod.GET)
    public ResponseResult tagList(String type) {
        ResponseResult result = new ResponseResult();
        List<ProductTag> list = productTagService.findByType(type);
        JSONArray arrray = new JSONArray();
        list.forEach(tag -> {
            JSONObject obj = new JSONObject();
            obj.put("id", tag.getId());
            obj.put("name", tag.getName());
            arrray.add(obj);
        });
        result.put("tags", arrray);
        return result;
    }

    /**
     * 商品专题列表
     *
     * @param keyword
     * @param t
     * @param c
     * @return
     */
    @RequestMapping(value = "/theme", method = RequestMethod.GET)
    public ResponseResult themeList(String keywords, Long t, Long c) {
        ResponseResult result = new ResponseResult();
        if (StringUtils.isBlank(keywords)) {
            if (c != null) {
                SearcherProductCategory category = productCategorySearcherService.findById(c.toString());
                keywords = category.getName();
            } else if (t != null) {
                SearcherTopCategory topCategory = topCategorySearcherService.findById(t.toString());
                keywords = topCategory.getName();
            }
        }
        JSONArray array = new JSONArray();
        if (StringUtils.isNotBlank(keywords)) {
            ThemeSearcherBean searcher = new ThemeSearcherBean();
            searcher.setStatus(1);
            searcher.setKeyword(keywords);
            searcher.setType(ThemeType.THEME.name());
            PageResult<SearcherTheme> pager = themeSearcherService.search(searcher, new PageModel(1, 3));
            pager.getList().forEach(theme -> array.add(theme.toJson()));
        }
        result.put("themes", array);
        return result;
    }

    /**
     * 到货通知
     *
     * @param sourceId
     * @param type
     * @param mobilemail
     * @return
     */
    @RequestMapping(value = "/remind/{sourceId}", method = RequestMethod.POST)
    public ResponseResult remind(@PathVariable Long sourceId, RemindType type, String mobilemail) {
        ResponseResult result = new ResponseResult();
        Remind remind = new Remind();
        MemberInfo member = this.getLoginMemberInfo();
        if (member != null) {
            remind.setMemberId(member.getId());
            if (mobilemail == null) {
                mobilemail = member.getLoginCode();
            }
        }
        remind.setSourceId(sourceId);
        remind.setType(type.name());
        if (mobilemail.contains("@")) {
            remind.setMail(mobilemail);
        } else {
            remind.setMobile(mobilemail);
        }
        if (!remind.check()) {
            throw new BusinessException("邮箱或手机号格式不正确，添加提醒不成功！");
        }
        remind.setContent(analyseName(type, sourceId));
        remind = remindService.insert(remind);
        if (remind == null) {
            throw new BusinessException("您已经添加提醒！");
        } else if (remind.getId() < 0) {
            throw new BusinessException("添加提醒不成功！");
        }
        return result;
    }

    private String analyseName(RemindType type, Long sourceId) {
        StringBuilder sb = new StringBuilder();
        Product product = productService.findById(sourceId);
        sb.append(product.getName());
        switch (type) {
            case CROWD_BEGIN:
                sb.append("，已经开始预售了，购买请到http://www.d2cmall.com/product/").append(sourceId);
                break;
            case ARRIVAL:
                sb.append("，已经到货了，购买请到http://www.d2cmall.com/product/").append(sourceId);
                break;
            case CUSTOM:
                break;
            default:
                break;
        }
        return sb.toString();
    }

    /**
     * 预售弹框
     *
     * @return
     */
    @RequestMapping(value = "/crowd/remind", method = RequestMethod.GET)
    public ResponseResult crowdRemind() {
        ResponseResult result = new ResponseResult();
        result.setStatus(-1);
        result.setMsg("不显示提醒");
        return result;
    }

    /**
     * 简单商品详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/simple/{id}", method = RequestMethod.GET)
    public ResponseResult simpleProduct(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        ProductDto dto = new ProductDto();
        Product product = productService.findById(id);
        BeanUtils.copyProperties(product, dto);
        SearcherProduct searcherProduct = productSearcherQueryService.findById(id.toString());
        JSONObject json = dto.toJson(searcherProduct);
        result.put("product", json);
        return result;
    }

}
