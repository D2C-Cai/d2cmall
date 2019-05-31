package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.Product;
import com.d2c.product.query.ProductSearcher;
import com.d2c.product.query.ProductSkuStockSearcher;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ProductMapper extends SuperMapper<Product> {

    List<Product> findByIds(@Param("ids") Long[] ids);

    List<Product> findBySearch(@Param("searcher") ProductSearcher searcher, @Param("pager") PageModel pager);

    int countBySearch(@Param("searcher") ProductSearcher searcher);

    int updateIndex(@Param("id") Long id, @Param("search") int search);

    int updateMark(@Param("id") Long id, @Param("status") int status, @Param("markDate") Date markDate,
                   @Param("adminName") String adminName);

    int updateTopical(@Param("id") Long id, @Param("topical") int topical);

    int updateAd(@Param("id") Long id, @Param("ad") int ad);

    int updateCod(@Param("id") Long id, @Param("cod") int cod);

    int updateCart(@Param("id") Long id, @Param("cart") int cart);

    int updateSubscribe(@Param("id") Long id, @Param("subscribe") int subscribe);

    int updateAfter(@Param("id") Long id, @Param("after") int after);

    int updateCoupon(@Param("id") Long id, @Param("coupon") Integer coupon);

    int updateRemark(@Param("id") Long id, @Param("remark") String remark);

    int updateTags(@Param("id") Long id, @Param("tags") String tags);

    int updateSyncTimestamp(@Param("syncStamp") String syncStamp);

    int doSumProductStore();

    List<Product> findSyncProductStore(@Param("syncStamp") String syncStamp, @Param("pager") PageModel pager);

    /**
     * 汇总商品库存,包括POP库存以及真实库存
     */
    Map<String, Object> findProductStoreBySku(@Param("productId") Long productId);

    int updateSalesPropertyBySku(@Param("productId") Long productId, @Param("salesProperty") String salesProperty);

    int getMaxSort(@Param("id") Long id);

    int updateSort(@Param("id") Long id, @Param("sort") Integer sort);

    int updatePromotionSort(@Param("id") Long id, @Param("gpSort") Integer gpSort, @Param("opSort") Integer opSort,
                            @Param("fpSort") Integer fpSort);

    int delete(@Param("id") Long id, @Param("mark") int mark);

    List<Map<String, Object>> countGroupByMark();

    List<Product> findProductBySn(String sn);

    Product findOneBySn(@Param("sn") String sn);

    int updateUpMarketDate(@Param("id") Long id, @Param("upMarketDate") Date upMarketDate);

    int updateDownMarketDate(@Param("id") Long id, @Param("downMarketDate") Date downMarketDate);

    int updateCategory(@Param("id") Long id, @Param("pcId") Long pcId, @Param("tcId") Long tcId,
                       @Param("pcJson") String pcJson, @Param("tcJson") String tcJson);

    int updatePrice(Product product);

    int doTiming(@Param("id") Long id, @Param("timing") int timing);

    int countStockBySearch(@Param("searcher") ProductSkuStockSearcher searcher);

    List<Product> findStockBySearch(@Param("searcher") ProductSkuStockSearcher searcher,
                                    @Param("pager") PageModel pager);

    List<Product> findByCouponDefId(@Param("pager") PageModel pager, @Param("defineId") Long defineId);

    int countByCouponDefId(@Param("defineId") Long defineId);

    List<Product> findByProductTagId(@Param("tagId") Long tagId, @Param("pager") PageModel pager);

    int countByProductTagId(Long tagId);

    List<Product> findByPromotionId(@Param("promotionId") Long promotionId, @Param("pager") PageModel pager);

    int countByPromotionId(Long promotionId);

    List<Product> findByDesignerId(@Param("designerId") Long designerId, @Param("pager") PageModel pager);

    int countByDesignerId(Long designerId);

    List<Product> findBySourceId(@Param("sourceId") Long sourceId, @Param("type") String type,
                                 @Param("pager") PageModel pager, @Param("mark") Integer mark);

    int countBySourceId(@Param("sourceId") Long relationId, @Param("type") String type, @Param("mark") Integer mark);

    Product findByMemberShareId(Long shareId);

    /**
     * 查询全站推荐
     */
    List<Product> findRecommendedProducts(@Param("startDate") Date startDate, @Param("designerId") Long designerId,
                                          @Param("number") Integer number);

    List<Product> findNoStore(@Param("pager") PageModel pager);

    /**
     * 获取缺货商品数量
     *
     * @return
     */
    int countNoStore();

    List<Long> findByDesignerIdAndBarCode(@Param("designerId") Long designerId, @Param("barCode") String barCode,
                                          @Param("designerIds") List<Long> designerIds);

    int sumPopStore(@Param("id") Long productId);

    int sumSyncStore(@Param("id") Long productId);

    int updateAfterByDesigner(@Param("designerId") Long designerId, @Param("after") Integer after);

    int updateCodByDesigner(@Param("designerId") Long designerId, @Param("cod") Integer cod);

    List<Long> findProductId(@Param("designerId") Long designerId);

    Product findByProductSn(String productSn);

    List<Product> findDeepByCategoryId(@Param("productCategoryId") Long productCategoryId,
                                       @Param("lastDate") Date lastDate, @Param("pager") PageModel pager);

    int updateEstimateDate(@Param("id") Long id, @Param("estimateDate") Date estimateDate,
                           @Param("operator") String operator);

    int updateVideoById(@Param("id") Long id, @Param("video") String video);

    int updateRecomById(@Param("id") Long id, @Param("recom") Double recom, @Param("operRecom") Integer operRecom);

    int updateRebate(@Param("id") Long id, @Param("firstRatio") BigDecimal firstRatio,
                     @Param("secondRatio") BigDecimal secondRatio, @Param("grossRatio") BigDecimal grossRatio);

    int updateGoodPromotion(@Param("id") Long id, @Param("goodPromotionId") Long goodPromotionId);

    int updateOrderPromotion(@Param("id") Long id, @Param("orderPromotionId") Long orderPromotionId);

    int updateAPrice(@Param("id") Long id, @Param("aPrice") BigDecimal aPrice);

    int updateFlashPromotionId(@Param("id") Long id, @Param("flashPromotionId") Long flashPromotionId);

    int updateCollagePromotionId(@Param("id") Long id, @Param("collagePromotionId") Long collagePromotionId);

    int updateAfterMemo(@Param("id") Long id, @Param("afterMemo") String afterMemo);

    int updateSellType(@Param("id") Long id, @Param("sellType") String sellType,
                       @Param("estimateDate") Date estimateDate, @Param("estimateDay") Integer estimateDay,
                       @Param("remark") String remark);

    int upEstimate(@Param("id") Long id, @Param("estimateDate") Date estimateDate,
                   @Param("estimateDay") Integer estimateDay);

    List<Long> findByBrandAndSeries(@Param("brandId") Long brandId, @Param("seriesId") Long seriesId,
                                    @Param("pager") PageModel pager);

    int countByBrandAndSeries(@Param("brandId") Long brandId, @Param("seriesId") Long seriesId);

    int updateKaolaProduct(@Param("id") Long id, @Param("kaolaPrice") BigDecimal kaolaPrice,
                           @Param("taxation") Integer taxation, @Param("shipping") Integer shipping);

    int countKaolaByWaitingForRepair();

    List<Product> findKaolaByWaitingForRepair(@Param("pager") PageModel page);

}
