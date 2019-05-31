package com.d2c.flame.manager;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.behavior.services.EventQueryService;
import com.d2c.cache.redis.annotation.CacheMethod;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.base.utils.ListUt;
import com.d2c.flame.enums.CategoryGroupEnum;
import com.d2c.flame.enums.ProductCategoryEnum;
import com.d2c.member.search.service.MemberCollectionSearcherService;
import com.d2c.order.model.CartItem;
import com.d2c.order.model.OrderItem;
import com.d2c.order.service.CartService;
import com.d2c.order.service.OrderItemService;
import com.d2c.product.model.TopCategory;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.query.ProductProSearchQuery;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.search.view.CartgoryVO;
import com.d2c.product.search.view.ProductVO;
import com.d2c.product.service.TopCategoryService;
import com.d2c.similar.constant.SimilarConst;
import com.d2c.similar.service.RecomService;
import com.d2c.similar.service.SimilarService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SimilarManager {

    @Autowired
    private CartService cartService;
    @Autowired
    private OrderItemService orderItemService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Reference
    private MemberCollectionSearcherService memberCollectionSearcherService;
    @Autowired
    private TopCategoryService topCategoryService;
    @Reference(timeout = 12000)
    private SimilarService similarService;
    @Reference(timeout = 12000)
    private RecomService recomService;
    @Reference(timeout = 12000)
    private EventQueryService eventQueryService;

    /**
     * 获得用户浏览商品ID
     */
    @CacheMethod
    public List<Long> getVisitProductIds(Long memberId, String topType) {
        List<Long> ids = new ArrayList<>();
        Long[] topIds = findTopIds(topType);
        for (Long id : eventQueryService.findVisitProductIds(memberId, SimilarConst.SELECT_LIMIT_MULTIPLY)) {
            // 校验商品id是否为topId品类数据
            if (checkProduceId(id, topIds)) {
                ids.add(id);
            }
        }
        return ListUt.distinct(ids);
    }

    /**
     * 获得已购买，购物车和收藏夹中商品ID
     */
    public List<Long> getUserProductIds(Long memberId) {
        return getUserProductIds(memberId, null);
    }

    @CacheMethod
    public List<Long> getUserProductIds(Long memberId, String topType) {
        List<Long> ids = getCarProductIds(memberId, topType);
        ids.addAll(getCollectionProductIds(memberId, topType));
        ids.addAll(getOrderProductIds(memberId, topType));
        return ListUt.distinct(ids);
    }

    /**
     * 获得购物车中商品ID
     */
    @CacheMethod
    public List<Long> getCarProductIds(Long memberId, String topType) {
        Integer limit = SimilarConst.SIMILAR_PRODUCT_LIMIT;
        List<Long> ids = new ArrayList<>();
        List<CartItem> items = cartService.findCart(memberId).getItems();
        Long[] topIds = findTopIds(topType);
        for (CartItem item : items) {
            // 校验商品id是否为topId品类数据
            if (checkProduceId(item.getProductId(), topIds)) {
                ids.add(item.getProductId());
            }
            if (ids.size() >= limit) {
                break;
            }
        }
        return ids;
    }

    /**
     * 获得收藏夹中商品ID
     */
    @CacheMethod
    public List<Long> getCollectionProductIds(Long memberId, String topType) {
        Integer limit = SimilarConst.SIMILAR_PRODUCT_LIMIT;
        List<Long> ids = new ArrayList<>();
        List<Long> collIds = memberCollectionSearcherService.findProductIdsInCollection(memberId,
                new PageModel(1, 2 * limit));
        Long[] topIds = findTopIds(topType);
        for (Long id : collIds) {
            // 校验商品id是否为topId品类数据
            if (checkProduceId(id, topIds)) {
                ids.add(id);
            }
            if (ids.size() >= limit) {
                break;
            }
        }
        return ids;
    }

    /**
     * 获得我的订单中商品ID
     */
    @CacheMethod
    public List<Long> getOrderProductIds(Long memberId, String topType) {
        Integer limit = SimilarConst.SIMILAR_PRODUCT_LIMIT;
        List<Long> ids = new ArrayList<>();
        List<OrderItem> items = orderItemService.findByMemberInfoId(memberId, new PageModel(1, 2 * limit));
        Long[] topIds = findTopIds(topType);
        for (OrderItem item : items) {
            // 校验商品id是否为topId品类数据
            if (checkProduceId(item.getProductId(), topIds)) {
                ids.add(item.getProductId());
            }
            if (ids.size() >= limit) {
                break;
            }
        }
        return ids;
    }

    @CacheMethod
    public CartgoryVO getCartgoryVO(Long categoryId) {
        ProductProSearchQuery query = new ProductProSearchQuery();
        query.setCategoryId(categoryId);
        query.setStore(1);
        List<SearcherProduct> ls = productSearcherQueryService.search(query, new PageModel(1, 4)).getList();
        List<ProductVO> vls = ProductVO.convertList(ls);
        return new CartgoryVO(vls);
    }

    /**
     * 不同页面的商品对应的一级分类
     */
    @CacheMethod(min = 300)
    public Long[] findTopIds(String topType) {
        if (StringUtils.isBlank(topType)) {
            return null;
        }
        List<Long> ids = new ArrayList<>();
        for (ProductCategoryEnum en : CategoryGroupEnum.getCategorys(topType)) {
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
    // ************** private ***************

    /**
     * 校验商品id是否为topId品类数据
     */
    private boolean checkProduceId(Long productId, Long[] topIds) {
        if (topIds == null) {
            return true;
        }
        SearcherProduct bean = productSearcherQueryService.findById(productId.toString());
        return bean != null && bean.getStore() == 1 && ArrayUtils.contains(topIds, bean.getTopCategoryId());
    }

}
