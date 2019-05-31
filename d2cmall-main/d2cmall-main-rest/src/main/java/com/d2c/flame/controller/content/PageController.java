package com.d2c.flame.controller.content;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.core.cache.old.CacheCallback;
import com.d2c.common.core.cache.old.CacheKey;
import com.d2c.common.core.cache.old.CacheTimerHandler;
import com.d2c.content.dto.SectionValueDto;
import com.d2c.content.model.AdResource;
import com.d2c.content.model.Section;
import com.d2c.content.model.SectionValue;
import com.d2c.content.model.SubModule;
import com.d2c.content.model.SubModule.ParentEnum;
import com.d2c.content.query.SectionValueSearcher;
import com.d2c.content.search.model.SearcherSection;
import com.d2c.content.search.query.SectionSearchBean;
import com.d2c.content.search.service.SectionSearcherService;
import com.d2c.content.service.*;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.flame.enums.TopCategoryEnum;
import com.d2c.flame.manager.SimilarManager;
import com.d2c.member.model.MemberDetail;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.model.PiliLive;
import com.d2c.member.query.MemberShareSearcher;
import com.d2c.member.query.PiliLiveSearcher;
import com.d2c.member.search.model.SearcherMemberAttention;
import com.d2c.member.search.model.SearcherMemberLike;
import com.d2c.member.search.model.SearcherMemberShare;
import com.d2c.member.search.model.SearcherMemberShareTag;
import com.d2c.member.search.service.MemberAttentionSearcherService;
import com.d2c.member.search.service.MemberLikeSearcherService;
import com.d2c.member.search.service.MemberShareSearcherService;
import com.d2c.member.search.service.MemberShareTagSearcherService;
import com.d2c.member.service.MemberDetailService;
import com.d2c.member.service.PiliLiveService;
import com.d2c.order.model.Setting;
import com.d2c.order.service.OrderItemService;
import com.d2c.order.service.SettingService;
import com.d2c.product.model.FlashPromotion;
import com.d2c.product.model.FlashPromotion.channelType;
import com.d2c.product.query.ProductSearcher;
import com.d2c.product.search.model.SearcherDesigner;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.query.DesignerSearchBean;
import com.d2c.product.search.query.ProductProSearchQuery;
import com.d2c.product.search.service.DesignerSearcherService;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.service.FlashPromotionService;
import com.d2c.util.date.DateUtil;
import org.elasticsearch.search.sort.SortOrder;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 自定义页面
 *
 * @author Lain
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/page")
public class PageController extends BaseController {

    @Autowired
    private SubModuleQueryService subModuleQueryService;
    @Reference
    private SectionSearcherService sectionSearcherService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Autowired
    private OrderItemService orderItemService;
    @Reference
    private MemberShareSearcherService memberShareSearcherService;
    @Reference
    private MemberAttentionSearcherService memberAttentionSearcherService;
    @Reference
    private DesignerSearcherService designerSearcherService;
    @Reference
    private MemberLikeSearcherService memberLikeSearcherService;
    @Autowired
    private SectionValueService sectionValueService;
    @Autowired
    private SectionService sectionService;
    @Reference
    private MemberShareTagSearcherService memberShareTagSearcherService;
    @Autowired
    private MemberDetailService memberDetailService;
    @Autowired
    private FlashPromotionService flashPromotionService;
    @Autowired
    private AdResourceService adResourceService;
    @Autowired
    private PiliLiveService piliLiveService;
    @Autowired
    private SettingService settingService;
    @Autowired
    private SimilarManager similarManager;
    @Autowired
    private SubModuleService subModuleService;

    /**
     * 主频道下的页面标签
     *
     * @param parent
     * @return
     */
    @RequestMapping(value = "/index/{parent}", method = RequestMethod.GET)
    public ResponseResult index(@PathVariable String parent) {
        ResponseResult result = new ResponseResult();
        if (ParentEnum.MAIN.name().equals(parent)) {
            parent = ParentEnum.HOME.name();
        }
        List<SubModule> subModuleList = subModuleQueryService.findByParent(parent);
        JSONArray subModules = new JSONArray();
        subModuleList.forEach(item -> subModules.add(item.toJson()));
        result.put("subModules", subModules);
        return result;
    }

    /**
     * 子频道下的页面标签
     *
     * @param parent
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "/index/{parent}/{categoryId}", method = RequestMethod.GET)
    public ResponseResult indexCategory(@PathVariable("parent") String parent,
                                        @PathVariable("categoryId") Long categoryId) {
        ResponseResult result = new ResponseResult();
        if (ParentEnum.MAIN.name().equals(parent)) {
            parent = ParentEnum.HOME.name();
        }
        List<SubModule> subModuleList = subModuleQueryService.findByParentAndCategory(parent, categoryId);
        JSONArray subModules = new JSONArray();
        subModuleList.forEach(item -> subModules.add(item.toJson()));
        result.put("subModules", subModules);
        return result;
    }

    /**
     * 页面的内容
     *
     * @param id
     * @param page
     * @param version
     * @return
     */
    @RequestMapping(value = "/submodule/{id}", method = RequestMethod.GET)
    public ResponseResult module(@PathVariable Long id, PageModel page, Integer version) {
        ResponseResult result = new ResponseResult();
        SectionSearchBean searcher = new SectionSearchBean();
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            MemberDetail memberDetail = memberDetailService.findByMemberInfoId(memberInfo.getId());
            searcher.setMemberLevel(memberDetail.getLevel().toString());
        } catch (NotLoginException e) {
            searcher.setMemberLevel("0");
        }
        SubModule subModule = subModuleQueryService.findById(id);
        result.put("subModule", subModule.toJson());
        if (version == null) {
            version = subModule.getVersion();
        }
        result.put("version", version);
        searcher.setModuleId(id);
        searcher.setVersion(version);
        PageResult<SearcherSection> pager = sectionSearcherService.search(searcher, page);
        JSONArray array = new JSONArray();
        for (SearcherSection s : pager.getList()) {
            if (s.getFixed() == 1) {
                array.add(s.toFixJson());
            } else {
                array.add(s.toUnFixJson());
            }
        }
        result.put("index", pager.getPageNumber());
        result.put("pageSize", pager.getPageSize());
        result.put("total", pager.getTotalCount());
        result.put("previous", pager.isForward());
        result.put("next", pager.isNext());
        result.put("content", array);
        return result;
    }

    /**
     * 点击查看全部
     *
     * @param id
     * @param page
     * @return
     */
    @RequestMapping(value = "/section/{id}", method = RequestMethod.GET)
    public ResponseResult section(@PathVariable Long id, PageModel page) {
        ResponseResult result = new ResponseResult();
        Section section = sectionService.findById(id);
        if (section == null) {
            throw new BusinessException("该模块不存在！");
        }
        SectionValueSearcher searcher = new SectionValueSearcher();
        searcher.setModuleId(section.getModuleId());
        searcher.setSectionDefId(section.getId());
        searcher.setStatus(1);
        int totalCount = 1;
        if (section.getFixed() == 0) {
            totalCount = sectionValueService.countBySearcher(searcher);
        }
        PageResult<SectionValueDto> valueDtoPager = sectionValueService.findDtoBySearcher(searcher, page);
        PageResult<SearcherSection> pager = new PageResult<SearcherSection>(page);
        pager.setTotalCount(totalCount);
        JSONArray array = new JSONArray();
        if (section.getFixed() == 0) {
            List<SearcherSection> list = subModuleService.findUnFixSection(section, valueDtoPager, 0);
            list.forEach(item -> array.add(item.toUnFixJson()));
        } else {
            SearcherSection searcherSection = subModuleService.findFixSection(section, 0);
            array.add(searcherSection.toFixJson());
        }
        result.put("index", pager.getPageNumber());
        result.put("pageSize", pager.getPageSize());
        result.put("total", pager.getTotalCount());
        result.put("previous", pager.isForward());
        result.put("next", pager.isNext());
        result.put("content", array);
        return result;
    }

    /**
     * 点击查看全部（非固定模块）
     *
     * @param id
     * @param page
     * @return
     */
    @RequestMapping(value = "/section/items", method = RequestMethod.GET)
    public ResponseResult sectionValue(Long[] sectionIds, PageModel page) {
        ResponseResult result = new ResponseResult();
        SectionValueSearcher searcher = new SectionValueSearcher();
        if (sectionIds == null || sectionIds.length == 0) {
            throw new BusinessException("模块不存在或不符合条件！");
        }
        searcher.setSectionIds(Arrays.asList(sectionIds));
        PageResult<SectionValue> pager = sectionValueService.findBySearcher(searcher, page);
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toJson()));
        result.put("index", pager.getPageNumber());
        result.put("pageSize", pager.getPageSize());
        result.put("total", pager.getTotalCount());
        result.put("previous", pager.isForward());
        result.put("next", pager.isNext());
        result.put("content", array);
        return result;
    }

    /**
     * 热门品牌推荐（我的大牌）
     *
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/mybrand/recommend", method = RequestMethod.GET)
    public ResponseResult brandRecommends(Integer pageSize) {
        ResponseResult result = new ResponseResult();
        if (pageSize == null) {
            pageSize = 2;
        }
        // 如果我登录的情况下取我关注的品牌
        List<Long> designerIds = null;
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            PageResult<SearcherMemberAttention> attentionPager = memberAttentionSearcherService
                    .findByMemberId(memberInfo.getId(), new PageModel(1, 100));
            if (attentionPager.getTotalCount() > 0) {
                designerIds = new ArrayList<Long>();
                for (SearcherMemberAttention attention : attentionPager.getList()) {
                    designerIds.add(attention.getDesignerId());
                }
            }
        } catch (NotLoginException e) {
        }
        // 查询对应品牌有新上架 商品（取10个品牌，每个品牌取6个商品）
        Map<Long, List<SearcherProduct>> map = productSearcherQueryService.findNewUpProductByBrand(10, 6, designerIds);
        JSONArray array = new JSONArray();
        JSONArray brandPic = new JSONArray();
        // 10个品牌的图片和2个品牌的信息
        for (Long key : map.keySet()) {
            SearcherDesigner designer = designerSearcherService.findById(key.toString());
            if (designer == null || designer.getMark() == 0) {
                continue;
            }
            JSONObject brandJson = designer.toRecommendJson();
            brandPic.add(brandJson);
            if (pageSize > array.size()) {
                JSONObject obj = new JSONObject();
                obj.put("brand", brandJson);
                List<SearcherProduct> products = map.get(key);
                JSONArray pArray = new JSONArray();
                products.forEach(p -> pArray.add(p.toJson()));
                obj.put("products", pArray);
                array.add(obj);
            }
        }
        result.put("upMarketBrands", array);
        result.put("recommendBrandPic", brandPic);
        return result;
    }

    /**
     * 热门商品推荐
     *
     * @param subModuleName
     * @param page
     * @return
     */
    @RequestMapping(value = "/product/recommends", method = RequestMethod.GET)
    public ResponseResult productRecommends(String subModuleName, PageModel page) {
        ProductProSearchQuery query = new ProductProSearchQuery();
        if (!StringUtil.isBlank(subModuleName)) {
            query.setTopIds(similarManager.findTopIds(subModuleName));
        }
        query.setStore(1);
        PageResult<SearcherProduct> pageRes = productSearcherQueryService.search(query, page);
        ResponseResult result = new ResponseResult();
        JSONArray array = new JSONArray();
        pageRes.getList().forEach(item -> array.add(item.toJson()));
        result.putPage("recommends", pageRes, array);
        // 全局返利系数
        Setting ratio = settingService.findByCode(Setting.REBATERATIO);
        result.put("ratio", Setting.defaultValue(ratio, new Integer(1)));
        return result;
    }

    /**
     * 最近销售的商品
     *
     * @return
     */
    @RequestMapping(value = "/recently/sales", method = RequestMethod.GET)
    public ResponseResult recentlySales() {
        ResponseResult result = new ResponseResult();
        String key = CacheKey.RECENTLYSALES;
        // 避免对订单表的过度查询，设5分钟缓存
        JSONArray array = CacheTimerHandler.getAndSetCacheValue(key, 5, new CacheCallback<JSONArray>() {
            @Override
            public JSONArray doExecute() {
                List<Map<String, Object>> list = orderItemService.findRecentlySales(new PageModel(1, 50));
                List<String> productIds = new ArrayList<>();
                list.forEach(item -> {
                    productIds.add(item.get("product_id").toString());
                });
                List<SearcherProduct> products = productSearcherQueryService.findByIds(productIds, 1);
                JSONArray productArray = new JSONArray();
                products.forEach(product -> productArray.add(product.toJson()));
                return productArray;
            }
        });
        result.put("recentlySalesProduct", array);
        return result;
    }

    /**
     * 首页限时购
     *
     * @param searcher
     * @return
     */
    @RequestMapping(value = "/recently/flashPromotion", method = RequestMethod.GET)
    public ResponseResult flashPromotion(ProductSearcher searcher) {
        ResponseResult result = new ResponseResult();
        List<FlashPromotion> list = flashPromotionService.findNoEndOrderByScore(channelType.MEMBER.name());
        if (list == null || list.size() == 0) {
            return result;
        }
        FlashPromotion fp = list.get(0);
        for (FlashPromotion fpItem : list) {
            // 获取最近一场
            if (!fpItem.getPromotionScope().equals(fp.getPromotionScope()) || !fp.isOver() && fpItem.isOver()) {
                break;
            }
            fp = fpItem;
        }
        result.put("flashPromotion", fp.toJson());
        searcher.setFlashPromotionId(fp.getId());
        ProductProSearchQuery searcherBean = searcher.convertSearchQuery();
        PageResult<SearcherProduct> productPager = productSearcherQueryService.search(searcherBean,
                new PageModel(1, 1));
        result.put("pic", productPager.getList().size() == 0 ? "" : productPager.getList().get(0).getMainPic());
        return result;
    }

    /**
     * 首页买家秀
     *
     * @param searcher
     * @param subModuleName
     * @return
     */
    @RequestMapping(value = "/share/list", method = RequestMethod.GET)
    public ResponseResult memberShare(MemberShareSearcher searcher, String subModuleName) {
        ResponseResult result = new ResponseResult();
        // 不同页面查询不同买家秀标签的买家秀
        if ("MALE".equals(subModuleName)) {
            SearcherMemberShareTag tag = memberShareTagSearcherService.findByCode("male");
            searcher.setTagId(tag != null ? tag.getId() : null);
        } else {
            List<SearcherMemberShareTag> tags = memberShareTagSearcherService.search(new PageModel(1, 1)).getList();
            searcher.setTagId(tags != null && tags.size() > 0 ? tags.get(0).getId() : null);
        }
        searcher.setStatus(1);
        Long memberInfoId = null;
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            searcher.setMemberId(memberInfo.getId());
            searcher.setAllList(true);
            memberInfoId = memberInfo.getId();
        } catch (Exception e) {
        }
        PageResult<SearcherMemberShare> pager = memberShareSearcherService.search(searcher.initSearchQuery(),
                new PageModel(1, 6));
        JSONArray array = new JSONArray();
        List<SearcherMemberShare> memberShares = pager.getList();
        if (memberInfoId != null) {
            String[] memberLikeids = new String[memberShares.size()];
            for (int i = 0; i < memberShares.size(); i++) {
                memberLikeids[i] = memberInfoId + "_" + memberShares.get(i).getId();
            }
            Map<Long, SearcherMemberLike> memberLikes = memberLikeSearcherService.findByIds(memberLikeids);
            for (SearcherMemberShare memberShare : pager.getList()) {
                Long shareId = memberShare.getId();
                if (memberLikes.containsKey(shareId)) {
                    memberShare.setLiked(1);
                } else {
                    memberShare.setLiked(0);
                }
                JSONObject obj = memberShare.toJson();
                array.add(obj);
            }
        } else {
            for (SearcherMemberShare memberShare : pager.getList()) {
                memberShare.setLiked(0);
                JSONObject obj = memberShare.toJson();
                array.add(obj);
            }
        }
        result.put("shares", array);
        return result;
    }

    /**
     * 首页直播
     *
     * @param searcher
     * @return
     */
    @RequestMapping(value = "/live/list", method = RequestMethod.GET)
    public ResponseResult piliLive(PiliLiveSearcher searcher) {
        ResponseResult result = new ResponseResult();
        searcher.setMark(1);
        PageResult<PiliLive> pager = piliLiveService.findBySearcher(searcher, new PageModel(1, 3));
        List<PiliLive> lives = pager.getList();
        JSONArray array = new JSONArray();
        lives.forEach(item -> array.add(item.toJson()));
        result.putPage("lives", pager, array);
        return result;
    }

    /**
     * 上新品牌，品类等其他信息（20天）
     *
     * @return
     */
    @RequestMapping(value = "/newup/other", method = RequestMethod.GET)
    public ResponseResult newUpInfo() {
        ResponseResult result = new ResponseResult();
        Map<Long, Integer> topCateMapCount = new HashMap<Long, Integer>();
        Map<Long, JSONObject> topCateMap = TopCategoryEnum.getLinkedMap();
        // 上新的品牌
        JSONArray brandArray = productSearcherQueryService.findNewUpGoodsCountGroupBrand(20, 10);
        result.put("brandArray", brandArray);
        // 上新的一级分类数量
        Map<Long, Long> topCountJson = productSearcherQueryService.findNewUpGoodsCountGroupTopCate(20, 20);
        TopCategoryEnum topCategoryEnum = null;
        for (Long topId : topCountJson.keySet()) {
            // 给一级分类再分组
            if ((topCategoryEnum = TopCategoryEnum.getById(topId)) == null) {
                continue;
            }
            Integer count = topCountJson.get(topId) == null ? 0 : topCountJson.get(topId).intValue();
            if (topCateMapCount.containsKey(topCategoryEnum.getCode())) {
                topCateMapCount.put(topCategoryEnum.getCode(), topCateMapCount.get(topCategoryEnum.getCode()) + count);
            } else {
                topCateMapCount.put(topCategoryEnum.getCode(), count);
            }
        }
        JSONArray topCateArray = new JSONArray();
        for (Map.Entry<Long, JSONObject> e : topCateMap.entrySet()) {
            JSONObject topCateJson = topCateMap.get(e.getKey());
            topCateJson.put("count", topCateMapCount.get(e.getKey()) == null ? 0 : topCateMapCount.get(e.getKey()));
            topCateArray.add(topCateJson);
        }
        result.put("topCateArray", topCateArray);
        // 上新页广告
        AdResource adResource = adResourceService.findByAppChannelAndType(AdResource.AppChannelEnum.MAIN.name(),
                AdResource.TypeEnum.NEWUP.name());
        result.put("adResource", adResource != null ? adResource.toJson() : null);
        // 所有上新商品数量
        ProductProSearchQuery searcher = new ProductProSearchQuery();
        searcher.setBeginCreateDate(DateUtil.getIntervalDay(new Date(), -20));
        int total = productSearcherQueryService.count(searcher);
        result.put("total", total);
        return result;
    }

    /**
     * 上新商品列表（20天）
     *
     * @param searcher
     * @param page
     * @return
     */
    @RequestMapping(value = "/newup/goods", method = RequestMethod.GET)
    public ResponseResult newUpGoods(ProductProSearchQuery searcher, PageModel page) {
        ResponseResult result = new ResponseResult();
        if (searcher.getTopId() != null) {
            searcher.setTopIds(TopCategoryEnum.getByCode(searcher.getTopId()).getIds());
            searcher.setTopId(null);
        }
        searcher.setBeginCreateDate(DateUtil.getIntervalDay(new Date(), -20));
        searcher.setSortFields(new String[]{"createDate"});
        searcher.setOrders(new SortOrder[]{SortOrder.DESC});
        searcher.setMark(true);
        PageResult<SearcherProduct> pager = productSearcherQueryService.search(searcher, page);
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toJson()));
        result.putPage("products", pager, array);
        return result;
    }

    /**
     * 上新商品的品牌（20天）
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/newup/goods/brand", method = RequestMethod.GET)
    public ResponseResult newUpBrand(PageModel page, Long topId) {
        ResponseResult result = new ResponseResult();
        Long myMemeberId = null;
        try {
            // 登录的判断是否关注
            MemberInfo memberInfo = this.getLoginMemberInfo();
            myMemeberId = memberInfo.getId();
        } catch (NotLoginException e) {
        }
        // 先查出品牌id列表然后代码分页查询品牌
        Long[] topIds = null;
        if (topId != null) {
            topIds = TopCategoryEnum.getByCode(topId).getIds();
        }
        List<Long> designerIds = productSearcherQueryService.findNewCreateBrandByTopCate(20, 1000, topIds);
        String[] ids = designerIds.stream().skip(page.getStartNumber()).limit(page.getPageSize())
                .map(designerId -> designerId.toString()).toArray(String[]::new);
        PageResult<SearcherDesigner> pager = new PageResult<>(page);
        pager.setTotalCount(designerIds.size());
        DesignerSearchBean beanSearcher = new DesignerSearchBean();
        Map<Long, SearcherDesigner> brands = designerSearcherService.findMapByIds(ids, beanSearcher);
        // 需要品牌信息，3个最新上架的商品和是否关注
        JSONArray array = new JSONArray();
        for (String id : ids) {
            SearcherDesigner brand = brands.get(Long.parseLong(id));
            if (brand != null) {
                JSONObject obj = brand.toJson();
                // 查询3个最新上架的商品
                ProductProSearchQuery searcher = new ProductProSearchQuery();
                searcher.setDesignerId(brand.getId());
                searcher.setSortFields(new String[]{"createDate"});
                searcher.setOrders(new SortOrder[]{SortOrder.DESC});
                searcher.setMark(true);
                PageResult<SearcherProduct> products = productSearcherQueryService.search(searcher,
                        new PageModel(1, 3));
                JSONArray productArray = new JSONArray();
                products.forEach(p -> productArray.add(p.toJson()));
                obj.put("products", productArray);
                // 20天内新上架的商品数量
                searcher.setBeginCreateDate(DateUtil.getIntervalDay(new Date(), -20));
                int count = productSearcherQueryService.count(searcher);
                obj.put("count", count);
                // 是否关注
                if (myMemeberId != null) {
                    SearcherMemberAttention attention = memberAttentionSearcherService
                            .findByMemberAndDesignerId(myMemeberId, brand.getId());
                    obj.put("attentioned", attention != null && attention.getMemberId() != null ? 1 : 0);
                } else {
                    obj.put("attentioned", 0);
                }
                array.add(obj);
            }
        }
        result.putPage("brands", pager, array);
        return result;
    }

    /**
     * 上新商品的品类（20天）
     *
     * @return
     */
    @RequestMapping(value = "/newup/category", method = RequestMethod.GET)
    public ResponseResult newUpCategory() {
        ResponseResult result = new ResponseResult();
        Map<Long, Integer> topCateMapCount = new HashMap<Long, Integer>();
        Map<Long, JSONObject> topCateMap = TopCategoryEnum.getLinkedMap();
        // 上新的一级分类数量
        Map<Long, Long> topCountJson = productSearcherQueryService.findNewUpGoodsCountGroupTopCate(20, 20);
        TopCategoryEnum topCategoryEnum = null;
        for (Long topId : topCountJson.keySet()) {
            // 给一级分类再分组
            if ((topCategoryEnum = TopCategoryEnum.getById(topId)) == null) {
                continue;
            }
            Integer count = topCountJson.get(topId) == null ? 0 : topCountJson.get(topId).intValue();
            if (topCateMapCount.containsKey(topCategoryEnum.getCode())) {
                topCateMapCount.put(topCategoryEnum.getCode(), topCateMapCount.get(topCategoryEnum.getCode()) + count);
            } else {
                topCateMapCount.put(topCategoryEnum.getCode(), count);
            }
        }
        JSONArray topCateArray = new JSONArray();
        for (Map.Entry<Long, JSONObject> e : topCateMap.entrySet()) {
            JSONObject topCateJson = topCateMap.get(e.getKey());
            if (topCateMapCount.get(e.getKey()) == null || topCateMapCount.get(e.getKey()) == 0) {
                continue;
            }
            topCateArray.add(topCateJson);
        }
        result.put("topCateArray", topCateArray);
        return result;
    }

}
