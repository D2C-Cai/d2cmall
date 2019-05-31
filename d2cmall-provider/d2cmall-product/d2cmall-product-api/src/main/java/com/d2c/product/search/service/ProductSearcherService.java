package com.d2c.product.search.service;

import com.d2c.product.search.model.SearcherProduct;

import java.util.Date;

public interface ProductSearcherService {

    public static final String TYPE_PRODUCT = "typeproduct";
    public static final String TYPE_REC_PRODUCT = "typerecproduct";

    /**
     * 添加商品搜索数据
     *
     * @param product
     * @return
     */
    int insert(SearcherProduct product);

    /**
     * 添加商品搜索数据
     *
     * @param product
     * @param liveId
     * @return
     */
    int insertRec(SearcherProduct product, Long liveId, Integer no);

    /**
     * 重建商品搜索数据
     *
     * @param product
     * @return
     */
    int rebuild(SearcherProduct product);

    /**
     * 更新商品搜索数据
     *
     * @param product
     * @return
     */
    int update(SearcherProduct product);

    /**
     * 更新商品推荐值 *
     *
     * @param recomScore
     * @param operRecom
     * @return
     */
    int updateRecom(Long productId, Double recomScore, Boolean operRecom);

    /**
     * 更新商品活动
     *
     * @param product
     * @return
     */
    int updateGoodPromotion(SearcherProduct product);

    /**
     * 去除商品活动
     *
     * @param productId
     * @return
     */
    int removeGoodPromotion(Long productId);

    /**
     * 更新订单活动
     *
     * @param productId
     * @param orderPromotion
     * @return
     */
    int updateOrderPromotion(SearcherProduct product);

    /**
     * 去除订单活动
     *
     * @param productId
     * @return
     */
    int removeOrderPromotion(Long productId);

    /**
     * 更新限时购活动
     *
     * @param productId
     * @param searcherProduct
     * @return
     */
    int updateFlashPromotion(SearcherProduct searcherProduct);

    /**
     * 移除限时购商品id
     *
     * @param productId
     * @return
     */
    int removeFlashPromotion(Long productId);

    /**
     * 更新拼团活动
     *
     * @param searcherProduct
     * @return
     */
    int updateCollagePromotion(SearcherProduct searcherProduct);

    /**
     * 提出拼团商品id
     *
     * @param productId
     * @return
     */
    int removeCollagePromotion(Long productId);

    /**
     * 更新上下架标志
     *
     * @param productId
     * @param mark
     * @return
     */
    int updateMark(Long productId, int mark, Date upMarketDate);

    /**
     * 更新售罄标志
     *
     * @param productId
     * @param store
     * @return
     */
    int updateStore(Long productId, int store, int spot);

    /**
     * 更新商品标签
     *
     * @param productId
     * @param store
     * @return
     */
    int updateTags(Long productId, String tags);

    /**
     * 更新上下架标志
     *
     * @param productId
     * @param mark
     * @return
     */
    int updateRecMark(Long productId, Long liveId, int mark);

    /**
     * 直播商品推荐
     *
     * @param productId
     * @param liveId
     * @param recommend
     * @return
     */
    int doRecommend(Long productId, Long liveId, Integer recommend);

    /**
     * 移除商品搜索数据
     *
     * @param productId
     * @return
     */
    int remove(Long productId);

    /**
     * 清空索引
     */
    void removeAll();

    /**
     * 移除商品搜索数据
     *
     * @param productId
     * @param liveId
     * @return
     */
    int removeRec(Long productId, Long liveId);

    /**
     * 清空索引
     */
    void removeRecAll();

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
     * 索引可见
     *
     * @param id
     * @param search
     * @return
     */
    int updateIndex(Long id, Integer search);

    /**
     * 更新最近销售量
     *
     * @param productId
     * @param recentlySales
     * @return
     */
    int updateRecentlySales(Long productId, Integer recentlySales);

    /**
     * 更新7天分销销量
     *
     * @param productId
     * @param sales
     * @return
     */
    int updatePartnerSales(Long productId, int sales);

    /**
     * 更新限时购库存
     *
     * @param productId
     * @param quantity
     * @return
     */
    int updateFlashSellStock(Long productId, int quantity);

}
