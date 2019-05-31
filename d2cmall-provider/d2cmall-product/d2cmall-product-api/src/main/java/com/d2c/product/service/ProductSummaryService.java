package com.d2c.product.service;

import com.d2c.product.model.ProductSummary;

import java.util.List;

/**
 * 产品数据汇总（product_summary）
 */
public interface ProductSummaryService {

    /**
     * 保存产品数据汇总信息
     *
     * @param summary
     * @return
     */
    ProductSummary insert(ProductSummary summary);

    /**
     * 更新商品id对应的总销量
     *
     * @param sales     总销量
     * @param productId 产品id
     * @return
     */
    int updateSalesCount(Integer sales, Long productId);

    /**
     * 更新商品id对应的查看数
     *
     * @param clicks    次数
     * @param productId 产品id
     * @return
     */
    int updateViewsCount(Integer clicks, Long productId);

    /**
     * 更新商品id对应的加入购物车次数
     *
     * @param clicks    次数
     * @param productId 产品id
     * @return
     */
    int updateCartsCount(Integer clicks, Long productId);

    /**
     * 更新商品id对应的加入订单数次数
     *
     * @param clicks    次数
     * @param productId 产品id
     * @return
     */
    int updateOrdersCount(Integer clicks, Long productId);

    /**
     * 更新商品喜欢数
     *
     * @param count
     * @param productId
     * @return
     */
    int updateLikeCount(Integer count, Long productId);

    /**
     * 根据产品id获取对应的汇总数据
     *
     * @param productId 产品id
     * @return
     */
    ProductSummary findByProductId(Long productId);

    /**
     * 更新咨詢数量
     *
     * @param consults
     * @param productId
     * @return
     */
    int updateConsultsCount(Integer consults, Long productId);

    /**
     * 更新评价数量
     *
     * @param sales
     * @param productId
     * @return
     */
    int updateCommentsCount(Integer comments, Long productId);

    /**
     * 更新最近销售量
     *
     * @param id
     * @return
     */
    int updateRecentlySales(Long productId, Integer recentlySales);

    /**
     * 更新分销销量
     *
     * @param productId
     * @param sales
     * @return
     */
    int updatePartnerSales(Long productId, Integer sales);

    /**
     * 查找所有有最近销售的商品ID
     *
     * @return
     */
    List<Long> findAllRecentlySales();

    /**
     * 查找所有分销销量
     *
     * @return
     */
    List<Long> findAllPartnerSales();

}
