package com.d2c.flame.controller.similar;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.d2c.behavior.services.EventQueryService;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.api.response.ResultHandler;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.common.base.utils.ListUt;
import com.d2c.common.base.utils.RandomUt;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.flame.enums.SearchFromEnum;
import com.d2c.flame.manager.SimilarManager;
import com.d2c.member.model.MemberInfo;
import com.d2c.order.model.OrderItem;
import com.d2c.order.service.OrderItemService;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.query.ProductProSearchQuery;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.search.view.ProductVO;
import com.d2c.similar.constant.SimilarConst;
import com.d2c.similar.service.RecomService;
import com.d2c.similar.service.SimilarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 推荐商品和相似商品
 *
 * @author wull
 */
@RestController
@RequestMapping(value = "/v3/api/similar")
public class SimilarController extends BaseController {

    @Autowired
    private OrderItemService orderItemService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Reference(timeout = 12000)
    private SimilarService similarService;
    @Reference(timeout = 12000)
    private RecomService recomService;
    @Reference(timeout = 12000)
    private EventQueryService eventQueryService;
    @Autowired
    private SimilarManager similarManager;

    /**
     * 首页混合推荐商品
     * <p>
     * API: /v3/api/similar/product/mix
     *
     * @param topType  首页模块名称
     * @param page     分页{@link PageModel}
     * @param interval 商品品类接口间隔，默认不添加
     */
    @RequestMapping(value = "/product/mix", method = RequestMethod.GET)
    public ResponseResult productMixture(String topType, PageModel page, Integer interval) {
        Integer minPageSize = 40;
        if (page.getPageSize() < minPageSize) {
            page.setPageSize(minPageSize);
        }
        ProductProSearchQuery query = new ProductProSearchQuery();
        query.setTopIds(similarManager.findTopIds(topType));
        query.setStore(1);
        PageResult<SearcherProduct> pager = productSearcherQueryService.search(query, page);
        List<Object> list = new ArrayList<>();
        for (SearcherProduct bean : pager.getList()) {
            list.add(setSearchFrom(bean, SearchFromEnum.RECOM));
        }
        MemberInfo memberInfo = this.getLoginUnCheck();
        if (memberInfo != null) {
            // 浏览商品的相似商品
            try {
                List<Long> ids = similarManager.getVisitProductIds(memberInfo.getId(), topType);
                if (!ids.isEmpty()) {
                    for (Object bean : similarService.findTopRandomByIds(ids, page.getPageSize() / 2)) {
                        try {
                            list.add(setSearchFrom(castProduct(bean), SearchFromEnum.SIMILAR_VISIT));
                        } catch (Exception e) {
                            logger.error("首页混合--浏览相似商品--类型转换失败..." + e.getMessage());
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("首页混合--浏览相似商品 --获取相似商品失败....", e);
            }
            // 加购收藏的相似商品
            try {
                List<Long> ids = similarManager.getUserProductIds(memberInfo.getId(), topType);
                if (!ids.isEmpty()) {
                    for (Object bean : similarService.findTopRandomByIds(ids, page.getPageSize() / 2)) {
                        try {
                            list.add(setSearchFrom(castProduct(bean), SearchFromEnum.SIMILAR_PRODUCT));
                        } catch (Exception e) {
                            logger.error("首页混合--推荐商品--商品类型转换失败..." + e.getMessage());
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("首页混合推荐商品 -- 获取相似商品失败....", e);
            }
        }
        list = ListUt.distinct(list);
        list = RandomUt.randomList(list);
        list = addCartgoryVO(list, interval);
        return ResultHandler.successAppPage("recommends", pager, list);
    }

    /**
     * 全球购-精选好货-混合推荐商品
     * <p>
     * API: /v3/api/similar/product/world/list
     */
    @RequestMapping(value = "/product/world/list", method = RequestMethod.GET)
    public ResponseResult productWorldList(PageModel page) {
        ProductProSearchQuery query = new ProductProSearchQuery();
        query.setWorldTrade(true);
        query.setStore(1);
        PageResult<SearcherProduct> pager = productSearcherQueryService.search(query, page);
        List<SearcherProduct> list = RandomUt.randomList(pager.getList());
        return ResultHandler.successAppPage("recommends", pager, toView(list));
    }

    private List<Object> addCartgoryVO(List<Object> list, Integer interval) {
        if (interval == null)
            return list;
        List<Object> resList = new ArrayList<>();
        List<Long> cartgoryIds = new ArrayList<>();
        int i = 1;
        for (Object bean : list) {
            resList.add(bean);
            if (i == interval) {
                if (bean instanceof ProductVO) {
                    ProductVO vo = (ProductVO) bean;
                    if (cartgoryIds.contains(vo.getCategoryId())) {
                        interval++;
                    } else {
                        resList.add(similarManager.getCartgoryVO(vo.getCategoryId()));
                        cartgoryIds.add(vo.getCategoryId());
                        interval += interval;
                    }
                }
            }
            i++;
        }
        return resList;
    }

    private List<ProductVO> toView(List<SearcherProduct> beans) {
        if (beans == null)
            return null;
        List<ProductVO> list = new ArrayList<>();
        beans.forEach(bean -> {
            list.add(bean.toView());
        });
        return list;
    }

    private ProductVO setSearchFrom(SearcherProduct bean, SearchFromEnum searchFrom) {
        ProductVO vo = bean.toView();
        vo.setSearchFrom(searchFrom.name());
        return vo;
    }

    /**
     * 情报站
     * <p>
     * API: /v3/api/similar/promotion/top/{limit}
     * <p>
     * 1.显示加购、收藏的商品与其它有相似商品（订单)活动的商品，取有活动的{limit}个商品 <br>
     * 2.推荐值中有活动的商品，总共展示{limit}个商品
     *
     * @param limit 页面显示数量
     * @return 返回商品列表 {@link SearcherProduct}
     */
    @ResponseBody
    @RequestMapping(value = "/promotion/top/{limit}", method = RequestMethod.GET)
    public Response findPromotionTop(@PathVariable Integer limit, String topType) {
        MemberInfo memberInfo = this.getLoginUnCheck();
        List<?> resList = null;
        if (memberInfo != null) {
            try {
                List<Long> ids = similarManager.getUserProductIds(memberInfo.getId(), topType);
                if (!ids.isEmpty()) {
                    resList = similarService.findTopPromotionByIds(ids, limit);
                }
            } catch (Exception e) {
                logger.error("情报站 -- 获取相似商品失败....", e);
            }
        }
        // 默认推荐数据
        if (resList == null || resList.isEmpty()) {
            ProductProSearchQuery query = new ProductProSearchQuery();
            query.setTopIds(similarManager.findTopIds(topType));
            query.setHasPromotion(true);
            query.setStore(1);
            resList = productSearcherQueryService
                    .search(query, new PageModel(1, limit * SimilarConst.SELECT_LIMIT_MULTIPLY)).getList();
            resList = RandomUt.randomList(resList, limit);
        }
        return ResultHandler.success(getJsonList(resList));
    }

    /**
     * 榜单推荐
     * <p>
     * API: /v3/api/similar/category/top/{limit}
     * <p>
     * 1.按照我收藏加购订单行为的商品品类，展示前{limit}个品类商品销量，销量最高的排第一 <br>
     * 2.推荐前{limit}个品类第一的商品
     *
     * @param limit 页面显示数量
     * @return 返回商品列表 {@link SearcherProduct}
     */
    @ResponseBody
    @RequestMapping(value = "/category/top/{limit}", method = RequestMethod.GET)
    public Response findTopOneCategory(@PathVariable Integer limit, String topType) {
        MemberInfo memberInfo = this.getLoginUnCheck();
        Map<Long, Object> resMap = null;
        if (memberInfo != null) {
            try {
                List<Long> ids = similarManager.getUserProductIds(memberInfo.getId(), topType);
                if (!ids.isEmpty()) {
                    resMap = similarService.findTopOneCategoryByIds(ids, limit);
                }
            } catch (Exception e) {
                logger.error("榜单推荐 -- 获取相似商品失败....", e);
            }
        }
        // 默认推荐数据
        if (resMap == null || resMap.isEmpty()) {
            resMap = recomService.findTopOneCategory(limit);
        }
        return ResultHandler.success(getJsonList(resMap.values()));
    }

    /**
     * 榜单推荐更多
     * <p>
     * API: /v3/api/similar/category/{categoryId}/top/{limit}
     * <p>
     * 根据商品品类获取用户对应的商品相似推荐
     *
     * @param limit 页面显示数量
     * @return 返回商品列表 {@link SearcherProduct}
     */
    @ResponseBody
    @RequestMapping(value = "/category/{categoryId}/top/{limit}", method = RequestMethod.GET)
    public Response findTopCategory(@PathVariable Long categoryId, @PathVariable Integer limit) {
        MemberInfo memberInfo = this.getLoginUnCheck();
        List<?> resList = null;
        if (memberInfo != null) {
            try {
                List<Long> ids = similarManager.getUserProductIds(memberInfo.getId());
                if (!ids.isEmpty()) {
                    resList = similarService.findTopCategoryById(ids, categoryId, limit);
                }
            } catch (Exception e) {
                logger.error("榜单推荐 -- 获取相似商品失败....", e);
            }
        }
        // 默认推荐数据
        if (resList == null || resList.isEmpty()) {
            ProductProSearchQuery query = new ProductProSearchQuery();
            query.setCategoryId(categoryId);
            query.setStore(1);
            resList = productSearcherQueryService.search(query, new PageModel(1, limit)).getList();
        }
        return ResultHandler.success(getJsonList(resList));
    }

    /**
     * 猜你喜欢
     * <p>
     * API: /v3/api/similar/top/{limit}
     * <p>
     * 1.如果用户登录有加购，订单，收藏等行为则按照商品相似度排序 <br>
     * 2.未登录/登录没有加购，订单，收藏等行为按照推荐值排序
     *
     * @param limit 页面显示数量
     * @return 返回商品列表 {@link SearcherProduct}
     */
    @ResponseBody
    @RequestMapping(value = "/top/{limit}", method = RequestMethod.GET)
    public Response findTopByUser(@PathVariable Integer limit, String topType) {
        MemberInfo memberInfo = this.getLoginUnCheck();
        List<?> resList = findTop(memberInfo, limit, topType);
        return ResultHandler.success(getJsonList(resList));
    }

    /**
     * 猜你喜欢
     * <p>
     * API: /v3/api/similar/user/{memberInfoId}/top/{limit}
     *
     * @param memberInfoId 查询用户ID
     * @param limit        页面显示数量
     * @return 返回商品列表 {@link SearcherProduct}
     */
    @ResponseBody
    @RequestMapping(value = "/user/{memberInfoId}/top/{limit}", method = RequestMethod.GET)
    public Response findTopByUser(@PathVariable Long memberInfoId, @PathVariable Integer limit, String topType) {
        this.getLoginMemberInfo();
        List<?> resList = findTop(memberInfoService.findById(memberInfoId), limit, topType);
        return ResultHandler.success(getJsonList(resList));
    }

    private List<?> findTop(MemberInfo memberInfo, Integer limit, String topType) {
        List<?> resList = null;
        if (memberInfo != null) {
            try {
                List<Long> ids = similarManager.getUserProductIds(memberInfo.getId(), topType);
                if (!ids.isEmpty()) {
                    resList = similarService.findTopRandomByIds(ids, limit);
                }
            } catch (Exception e) {
                logger.error("猜你喜欢 -- 获取相似商品失败....", e);
            }
        }
        // 默认推荐数据
        if (resList == null || resList.isEmpty()) {
            resList = findTopRecom(limit, topType);
            resList = RandomUt.randomList(resList, limit);
        }
        return resList;
    }

    private List<?> findTopRecom(Integer limit, String topType) {
        ProductProSearchQuery query = new ProductProSearchQuery();
        query.setTopIds(similarManager.findTopIds(topType));
        query.setStore(1);
        return productSearcherQueryService.search(query, new PageModel(1, limit * SimilarConst.SELECT_LIMIT_MULTIPLY))
                .getList();
    }

    /**
     * 商品详情-相似商品: 为你推荐，根据单一商品
     * <p>
     * API: /v3/api/similar/{id}/top/{limit}
     *
     * @param id    商品ID
     * @param limit 页面显示数量
     * @return 返回商品列表 {@link SearcherProduct}
     */
    @ResponseBody
    @RequestMapping(value = "/{id}/top/{limit}", method = RequestMethod.GET)
    public Response findTop(@PathVariable Integer id, @PathVariable Integer limit) {
        List<?> resList = null;
        try {
            resList = similarService.findTopRandom(id, limit);
        } catch (Exception e) {
            logger.error("商品详情 -- 获取相似商品失败....", e);
        }
        // 默认推荐数据
        if (resList == null || resList.isEmpty()) {
            resList = productSearcherQueryService.findTopRecom(limit * SimilarConst.SELECT_LIMIT_MULTIPLY);
            resList = RandomUt.randomList(resList, limit);
        }
        return ResultHandler.success(getJsonList(resList));
    }

    /**
     * 下单推荐: 根据我的某一订单推荐商品
     * <p>
     * API: /v3/api/similar/order/{orderId}/top/{limit}
     *
     * @param orderId 订单ID
     * @param limit   页面显示数量
     * @return 返回商品列表 {@link SearcherProduct}
     */
    @ResponseBody
    @RequestMapping(value = "/order/{orderId}/top/{limit}", method = RequestMethod.GET)
    public Response findTopByOrderId(@PathVariable Long orderId, @PathVariable Integer limit) {
        List<?> resList = null;
        try {
            List<Object> ids = getProductIdsByOrderId(orderId, limit);
            if (!ids.isEmpty()) {
                resList = similarService.findTopRandomByIds(ids, limit);
            }
        } catch (Exception e) {
            logger.error("下单推荐 -- 获取相似商品失败....", e);
        }
        // 默认推荐数据
        if (resList == null || resList.isEmpty()) {
            resList = productSearcherQueryService.findTopRecom(limit * SimilarConst.SELECT_LIMIT_MULTIPLY);
            resList = RandomUt.randomList(resList, limit);
        }
        return ResultHandler.success(getJsonList(resList));
    }

    /**
     * 购物车-相似商品: 为你推荐，根据所有购物车内商品
     * <p>
     * API: /v3/api/similar/cart/top/{limit}
     *
     * @param limit 页面显示数量
     * @return 返回商品列表 {@link SearcherProduct}
     */
    @ResponseBody
    @RequestMapping(value = "/cart/top/{limit}", method = RequestMethod.GET)
    public Response findCartTop(@PathVariable Integer limit) {
        MemberInfo memberInfo = this.getLoginUnCheck();
        List<?> resList = null;
        if (memberInfo != null) {
            try {
                List<Long> ids = similarManager.getCarProductIds(memberInfo.getId(), null);
                if (!ids.isEmpty()) {
                    resList = similarService.findTopRandomByIds(ids, limit);
                }
            } catch (Exception e) {
                logger.error("购物车 -- 获取相似商品失败....", e);
            }
        }
        // 默认推荐数据
        if (resList == null || resList.isEmpty()) {
            resList = productSearcherQueryService.findTopRecom(limit * SimilarConst.SELECT_LIMIT_MULTIPLY);
            resList = RandomUt.randomList(resList, limit);
        }
        return ResultHandler.success(getJsonList(resList));
    }

    /**
     * 收藏夹-相似商品: 为你推荐，根据所有收藏夹内商品
     * <p>
     * API: /v3/api/similar/collection/top/{limit}
     *
     * @param limit 页面显示数量
     * @return 返回商品列表 {@link SearcherProduct}
     */
    @ResponseBody
    @RequestMapping(value = "/collection/top/{limit}", method = RequestMethod.GET)
    public Response findCollectionTop(@PathVariable Integer limit) {
        MemberInfo memberInfo = this.getLoginUnCheck();
        List<?> resList = null;
        if (memberInfo != null) {
            try {
                List<Long> ids = similarManager.getCollectionProductIds(memberInfo.getId(), null);
                if (!ids.isEmpty()) {
                    resList = similarService.findTopRandomByIds(ids, limit);
                }
            } catch (Exception e) {
                logger.error("收藏夹 -- 获取相似商品失败....", e);
            }
        }
        // 默认推荐数据
        if (resList == null || resList.isEmpty()) {
            resList = productSearcherQueryService.findTopRecom(limit * SimilarConst.SELECT_LIMIT_MULTIPLY);
            resList = RandomUt.randomList(resList, limit);
        }
        return ResultHandler.success(getJsonList(resList));
    }

    @ResponseBody
    @RequestMapping(value = "/brand/{brandId}/{limit}", method = RequestMethod.GET)
    public Response findByBrand(@PathVariable Long brandId, @PathVariable Integer limit) {
        List<?> resList = null;
        ProductProSearchQuery query = new ProductProSearchQuery();
        query.setDesignerId(brandId);
        PageResult<SearcherProduct> pager = productSearcherQueryService.search(query, new PageModel());
        List<Long> list = new ArrayList<>();
        for (SearcherProduct bean : pager.getList()) {
            list.add(bean.getProductId());
        }
        try {
            resList = similarService.findByTargetIds(list, limit);
        } catch (Exception e) {
            logger.error("商品详情 -- 获取相似商品失败....", e);
        }
        // 默认推荐数据
        if (resList == null || resList.isEmpty()) {
            resList = new ArrayList<>();
        }
        return ResultHandler.success(getJsonList(resList));
    }
    // ********************** private **************************

    /**
     * 获得我的某一个订单中的商品ID列表
     */
    private List<Object> getProductIdsByOrderId(Long orderId, Integer limit) {
        List<Object> ids = new ArrayList<>();
        List<OrderItem> items = orderItemService.findByOrderId(orderId);
        for (OrderItem item : items) {
            ids.add(item.getProductId());
        }
        return ids;
    }

    private JSONArray getJsonList(Collection<?> list) {
        JSONArray array = new JSONArray();
        for (Object bean : list) {
            SearcherProduct product = castProduct(bean);
            if (product != null) {
                array.add(product.toJson());
            }
        }
        return array;
    }

    private SearcherProduct castProduct(Object bean) {
        SearcherProduct product = null;
        if (bean instanceof SearcherProduct) {
            product = (SearcherProduct) bean;
        }
        if (product == null) {
            product = BeanUt.apply(new SearcherProduct(), bean);
        }
        return product;
    }

}
