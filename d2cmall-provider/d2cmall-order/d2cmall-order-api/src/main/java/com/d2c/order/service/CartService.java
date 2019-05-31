package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.dto.Cart;
import com.d2c.order.dto.CartItemDto;
import com.d2c.order.model.CartItem;
import com.d2c.order.query.CartItemSearcher;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {

    /**
     * 通过购物车商品的id，得到购物车商品的实体类
     *
     * @param cartItemId
     * @return
     */
    CartItem findById(Long cartItemId);

    /**
     * 查询单条购物车明细
     *
     * @param cartItemId
     * @param memberId
     * @return
     */
    CartItemDto findOneByMemberId(Long cartItemId, Long memberId);

    /**
     * 通过skuId 和memberId 查找
     *
     * @param skuId
     * @param memberId
     * @return
     */
    CartItem findBySkuAndMember(Long skuId, Long memberId);

    /**
     * 得到指定会员有的购物车商品的数量
     *
     * @param memberInfoId
     * @return
     */
    int countByMemberId(Long memberInfoId);

    /**
     * 通过查询条件和分页条件，得到购物车商品的分页实体数据
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<CartItem> findPageBySearcher(CartItemSearcher searcher, PageModel page);

    /**
     * 通过查询条件查询
     *
     * @param searcher
     * @param page
     * @return
     */
    List<CartItem> findBySearcher(CartItemSearcher searcher, PageModel page);

    /**
     * 通过查询条件，得到符合条件的数量
     *
     * @param searcher
     * @return
     */
    int countBySearcher(CartItemSearcher searcher);

    /**
     * 得到指定会员的购物车数据
     *
     * @param memberId 会员id
     * @return
     */
    Cart findCart(Long memberId);

    /**
     * 给该会员添加一条购物车商品数据，并且返回购物车数据
     *
     * @param cartItem     购物车商品
     * @param memberInfoId 会员id
     * @return
     */
    CartItem insert(CartItem cartItem, Long memberInfoId);

    /**
     * 更新指定的会员购物车商品数据，并且返回购物车
     *
     * @param productSkuId
     * @param memberInfoId
     * @param quantity
     * @return
     */
    int updateQuantity(Long productSkuId, Long memberInfoId, int quantity);

    /**
     * 更新指定的会员购物车商品数据，并且返回购物车
     *
     * @param productSkuId
     * @param memberInfoId
     * @param quantity
     * @return
     */
    int updatePrice(Long productSkuId, Long memberInfoId, BigDecimal originalCost, BigDecimal price);

    /**
     * 更改skuId
     *
     * @param cartItemId
     * @param skuId
     * @return
     */
    int updateSku(Long cartItemId, Long skuId, Long memberInfoId);

    /**
     * 通过指定的会员，删除对应的购物车商品数据
     *
     * @param cartItemId
     * @param memberId
     * @return
     */
    int deleteCartItem(Long cartItemId, Long memberId);

    /**
     * 批量删除指定会员的购物车商品数据
     *
     * @param cartItemIds  批量购物车商品的id
     * @param memberInfoId 会员id
     * @return
     */
    int deleteCartItems(List<Long> cartItemIds, Long memberInfoId);

    /**
     * 删除有关会员的所有购物车商品数据
     *
     * @param memberId 会员id
     * @return
     */
    int deleteCart(Long memberId);

    /**
     * 购物车sku更改为已存在sku的购物车
     *
     * @param cartItemId
     * @param skuId
     * @param quantity
     * @param id
     * @return
     */
    int mergeSku(Long cartItemId, Long skuId, int quantity, Long id);

}
