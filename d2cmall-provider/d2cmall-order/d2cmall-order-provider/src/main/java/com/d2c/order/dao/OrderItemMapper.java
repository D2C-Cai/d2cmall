package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.OrderItem;
import com.d2c.order.query.OrderSearcher;
import com.d2c.order.query.ProductOrderSumSearcher;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface OrderItemMapper extends SuperMapper<OrderItem> {

    List<OrderItem> findBySearcher(@Param("queryOrder") OrderSearcher queryOrder, @Param("pager") PageModel pager);

    int countBySearcher(@Param("queryOrder") OrderSearcher queryOrder);

    List<OrderItem> findByMemberInfoId(@Param("memberInfoId") Long memberInfoId, @Param("pager") PageModel pager);

    OrderItem findByIdAndMemberInfoId(@Param("id") Long id, @Param("memberInfoId") Long memberInfoId);

    List<OrderItem> findByOrderId(Long id);

    List<OrderItem> findByOrderSn(String orderSn);

    OrderItem findByRefundId(Long refundId);

    OrderItem findByOrderSnAndSku(@Param("orderSn") String orderSn, @Param("barCode") String barCode);

    List<OrderItem> findByDeliverySn(String deliverySn);

    List<OrderItem> findPartnerOrder(@Param("queryOrder") OrderSearcher queryOrder, @Param("pager") PageModel pager);

    int countPartnerOrder(@Param("queryOrder") OrderSearcher queryOrder);

    List<OrderItem> findDeliveredByDate(@Param("date") Date date, @Param("interval") int interval,
                                        @Param("pager") PageModel pager);

    int countDeliveredByDate(@Param("date") Date date, @Param("interval") int interval);

    List<OrderItem> findSignedByDate(@Param("date") Date date, PageModel page);

    int countSignedByDate(@Param("date") Date date);

    List<Map<String, Object>> findProductOrderAnalysis(@Param("startDayTime") Date startDayTime,
                                                       @Param("endDayTime") Date endDayTime, @Param("type") String type, @Param("pager") PageModel page);

    int countProductOrderAnalysis(@Param("startDayTime") Date startDayTime, @Param("endDayTime") Date endDayTime,
                                  @Param("type") String type);

    List<OrderItem> findCodStatement(@Param("beginDate") Date beginDate, @Param("page") PageModel page);

    int countCodStatement(@Param("beginDate") Date beginDate);

    List<OrderItem> findForStatement(@Param("beginDate") Date beginDate, @Param("page") PageModel page);

    int countForStatement(@Param("beginDate") Date beginDate);

    List<OrderItem> findPopOrderitems(@Param("deadline") Date deadline, @Param("pager") PageModel page);

    int countPopOrderitems(@Param("deadline") Date deadline);

    List<OrderItem> findNormalOrderitems(@Param("deadline") Date deadline, @Param("pager") PageModel page);

    int countNormalOrderitems(@Param("deadline") Date deadline);

    List<Map<String, Object>> findRankingByProduct(@Param("pager") PageModel page,
                                                   @Param("searcher") ProductOrderSumSearcher searcher);

    int countRankingByProduct(@Param("searcher") ProductOrderSumSearcher searcher);

    List<OrderItem> findExpiredDelivery(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate,
                                        @Param("page") PageModel page);

    int countExpiredDelivery(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

    List<Map<String, Object>> findRankingSummaryByProduct(@Param("pager") PageModel page,
                                                          @Param("searcher") ProductOrderSumSearcher searcher);

    int countRankingSummaryByProduct(@Param("searcher") ProductOrderSumSearcher searcher);

    List<Map<String, Object>> findDailyFinishAmount(@Param("page") PageModel page);

    int countDailyFinishAmount();

    List<Long> findProductIdByBuyer(Long memberId);

    List<Map<String, Object>> findRecentlySales(@Param("pager") PageModel page);

    List<Map<String, Object>> findRecentlyMonthSales(@Param("pager") PageModel page, @Param("month") Integer month);

    List<Map<String, Object>> findPartnerSales(@Param("pager") PageModel pager, @Param("days") int days);

    String findBrandsByMemberId(@Param("memberId") Long memberId, @Param("size") Integer size,
                                @Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

    OrderItem findMaxCostByMemberId(@Param("memberId") Long memberId, @Param("beginDate") Date beginDate,
                                    @Param("endDate") Date endDate);

    BigDecimal findSumCostByMemberId(@Param("memberId") Long memberId, @Param("beginDate") Date beginDate,
                                     @Param("endDate") Date endDate);

    Integer findRankByMoney(@Param("money") BigDecimal money, @Param("beginDate") Date beginDate,
                            @Param("endDate") Date endDate);

    List<HashMap<String, Object>> findProductSales();

    List<Map<String, Object>> findBrandSales(@Param("days") int days, @Param("pager") PageModel pager);

    int countBrandSales(@Param("days") int days);

    List<Map<String, Object>> countGroupByStatus(@Param("memberInfoId") Long memberInfoId);

    List<Map<String, Object>> countGroupByStatusAndStore(@Param("isStore") Boolean isStore,
                                                         @Param("storeId") Long storeId);

    int countWaitComment(@Param("memberInfoId") Long memberInfoId);

    int countByProductSkuId(Long productSkuId);

    int countMemberId();

    int updateWalletAmount(@Param("id") Long id, @Param("cashAmount") BigDecimal cashAmount,
                           @Param("giftAmount") BigDecimal giftAmount);

    int updateCouponAmount(@Param("id") Long id, @Param("couponAmount") BigDecimal couponAmount);

    int updateBarcodeById(@Param("barcode") String barcode, @Param("id") Long id);

    int updateAdminMemo(@Param("id") Long id, @Param("itemMemo") String adminMemo);

    int updateCloseInfo(@Param("orderId") Long orderId, @Param("itemCloseReason") String itemCloseReason,
                        @Param("itemCloseMan") String itemCloseMan);

    int updateBalance(@Param("id") Long id, @Param("balanceMoney") BigDecimal balanceMoney,
                      @Param("balanceMan") String balanceMan, @Param("balanceReason") String balanceReason);

    int updateRefund(@Param("refundId") Long refundId, @Param("id") Long id);

    int updateReship(@Param("reshipId") Long reshipId, @Param("id") Long id);

    int updateExchange(@Param("exchangeId") Long exchangeId, @Param("id") Long id);

    int updateRequisition(@Param("id") Long id, @Param("requisition") Integer requisition);

    int updateEstimateDate(@Param("id") Long id, @Param("estimateDate") Date estimateDate);

    int updateExpectDate(@Param("id") Long id, @Param("expectDate") Date expectDate);

    int updateComment(@Param("id") Long id, @Param("commentId") Long commentId);

    int updateStatus(@Param("id") Long id, @Param("status") int status);

    int updateBusType(@Param("id") Long id, @Param("busType") String busType);

    int updateCollageStatus(@Param("orderSn") List<String> orderSn, @Param("status") Integer status);

    int updateDeliveryInfo(@Param("id") Long id, @Param("deliverySn") String deliverySn,
                           @Param("deliveryCorpName") String deliveryCorpName, @Param("deliveryBarCode") String deliveryBarCode);

    int updateAccountAmount(@Param("id") Long id, @Param("cashAmount") BigDecimal cashAmount,
                            @Param("giftAmount") BigDecimal giftAmount);

    int doPaySuccess(@Param("id") Long id, @Param("paymentType") Integer paymentType, @Param("pop") Integer pop,
                     @Param("cashAmount") BigDecimal cashAmount, @Param("giftAmount") BigDecimal giftAmount,
                     @Param("estimateDate") Date estimateDate);

    int doBindStore(@Param("id") Long id, @Param("itemMemo") String itemMemo,
                    @Param("deliveryBarCode") String deliveryBarCode, @Param("storeId") Long storeId,
                    @Param("store_memo") String store_memo);

    int doDeliveryItem(@Param("orderItemId") Long orderItemId, @Param("deliveryType") Integer deliveryType,
                       @Param("deliveryBarCode") String deliveryBarCode, @Param("deliveryCorpName") String deliveryCorpName,
                       @Param("deliverySn") String deliverySn, @Param("deliveryQuantity") Integer deliveryQuantity);

    int doDesignerDelivery(@Param("id") Long id, @Param("deliveryType") int deliveryType);

    int doSign(@Param("id") Long id, @Param("signDate") Date signDate, @Param("expectDate") Date expectDate);

    int doSuccess(@Param("id") Long id, @Param("diffAmount") BigDecimal diffAmount);

    int doAutoSuccess(long expireTime);

    int doClose(@Param("id") Long id, @Param("itemCloseMan") String itemCloseMan,
                @Param("itemCloseReason") String itemCloseReason, @Param("status") String status);

    int doLock(@Param("id") Long id, @Param("info") String info);

    int doBalance(@Param("id") Long id, @Param("balanceMan") String balanceMan,
                  @Param("balanceMoney") BigDecimal balanceMoney, @Param("balanceReason") String balanceReason,
                  @Param("billNumber") String billNumber);

    int doCompensation(@Param("id") Long id, @Param("compansation") BigDecimal compansation);

    int cancelDelivery(@Param("id") Long id);

    int cancelStore(@Param("id") Long id);

    int cancelClose(@Param("id") Long id);

    int cancelBalance(@Param("id") Long id);

    int cancelLock(@Param("id") Long id, @Param("info") String info);

    /**
     * 第一次提交创建
     *
     * @param reshipId
     * @param orderItemId
     * @return
     */
    int doCreateReship(@Param("reshipId") Long reshipId, @Param("orderItemId") Long orderItemId);

    /**
     * 第一次提交创建
     *
     * @param reshipId
     * @param orderItemId
     * @return
     */
    int doCreateRefund(@Param("refundId") Long refundId, @Param("orderItemId") Long orderItemId);

    /**
     * 第一次提交创建
     *
     * @param reshipId
     * @param orderItemId
     * @return
     */
    int doCreateExchange(@Param("exchangeId") Long exchangeId, @Param("orderItemId") Long orderItemId);

    List<Map<String, Object>> findActualAmountForAward(@Param("beginPaymentDate") Date beginPaymentDate,
                                                       @Param("endPaymentDate") Date endPaymentDate, @Param("beginFinishDate") Date beginFinishDate,
                                                       @Param("endFinishDate") Date endFinishDate, @Param("awardIds") List<Long> awardIds);

    int countCompensation(@Param("deadline") Date deadline);

    List<OrderItem> findCompensation(@Param("page") PageModel page, @Param("deadline") Date deadline);

    List<OrderItem> findWaitUpdateDeliveryCaomeiOrder(@Param("page") PageModel page);

    Integer countWaitUpdateDeliveryCaomeiOrder();

    /**
     * D+店的业绩
     *
     * @param startDate
     * @param endDate
     * @return
     */
    List<Map<String, Object>> findDplusTotalAmount(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * D+店的单店业绩
     *
     * @param storeId
     * @param startDate
     * @param endDate
     * @return
     */
    List<Map<String, Object>> findDplusAmountByShop(@Param("storeId") Long storeId, @Param("startDate") Date startDate,
                                                    @Param("endDate") Date endDate);

    List<OrderItem> findDplusForStatement(@Param("beginDate") Date beginDate, @Param("page") PageModel page);

    int countDplusForStatement(@Param("beginDate") Date beginDate);

}
