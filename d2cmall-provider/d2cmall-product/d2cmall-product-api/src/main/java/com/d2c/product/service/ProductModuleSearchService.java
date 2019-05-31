package com.d2c.product.service;

import com.d2c.product.model.*;
import com.d2c.product.search.model.SearcherProduct;

import java.util.Date;

public interface ProductModuleSearchService {

    /**
     * 重建
     *
     * @param productId
     * @return
     */
    int rebuild(Long productId);

    /**
     * 插入
     *
     * @param productId
     * @return
     */
    int insert(Long productId);

    /**
     * 删除
     *
     * @param productId
     * @return
     */
    int remove(Long productId);

    /**
     * 更新
     *
     * @param product
     * @return
     */
    int update(SearcherProduct product);

    /**
     * 上下架
     *
     * @param productId
     * @param mark
     * @param upMarketDate
     * @return
     */
    int updateMark(Long productId, int mark, Date upMarketDate);

    /**
     * 更新标签
     *
     * @param productId
     * @param tags
     * @return
     */
    int updateTags(Long productId, String tags);

    /**
     * 组建模型
     *
     * @param productId
     * @return
     */
    SearcherProduct getSearchDto(Long productId);

    /**
     * 组建模型
     *
     * @param product
     * @param summary
     * @param detail
     * @return
     */
    SearcherProduct getSearchDto(Product product, ProductSummary summary, ProductDetail detail);

    /**
     * 组建模型
     *
     * @param productId
     * @param productPromotion
     * @return
     */
    SearcherProduct getSearchDto(Long productId, Promotion productPromotion);

    /**
     * 组建模型
     *
     * @param productId
     * @param flashPromotion
     * @return
     */
    SearcherProduct getSearchDto(Long productId, FlashPromotion flashPromotion);

    /**
     * 组建模型
     *
     * @param productId
     * @param collagePromotion
     * @return
     */
    SearcherProduct getSearchDto(Long productId, CollagePromotion collagePromotion);

    /**
     * 更新售罄标志
     *
     * @param productId
     * @param store
     * @return
     */
    int updateStore(Long productId);

    /**
     * 更新售后
     *
     * @param productId
     * @param after
     * @return
     */
    int updateAfter(Long productId, int after);

    /**
     * 门店试衣
     *
     * @param productId
     * @param subscribe
     * @return
     */
    int updateSubscribe(Long productId, int subscribe);

    /**
     * 更新最近销售量
     *
     * @param productId
     * @param recentlySales
     * @return
     */
    int updateRecentlySales(Long productId, Integer recentlySales);

    /**
     * 更新活动
     *
     * @param productId
     * @param promotion
     * @return
     */
    int updatePromotion(Long productId, Promotion promotion);

    /**
     * 更新限时购商品活动
     *
     * @param productId
     * @param flashPromotion
     * @return
     */
    int updateFlashPromotion(Long productId, FlashPromotion flashPromotion);

    /**
     * 更新拼团商品活动
     *
     * @param productId
     * @param collagePromotion
     * @return
     */
    int updateCollagePromotion(Long productId, CollagePromotion collagePromotion);

    /**
     * 更新分销销量
     *
     * @param productId
     * @param sales
     * @return
     */
    int updatePartnerSales(Long productId, Integer sales);

}
