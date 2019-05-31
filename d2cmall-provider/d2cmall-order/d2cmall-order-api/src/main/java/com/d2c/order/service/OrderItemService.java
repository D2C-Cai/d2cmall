package com.d2c.order.service;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.OrderLog.OrderLogType;
import com.d2c.order.dto.OrderItemDto;
import com.d2c.order.model.Order;
import com.d2c.order.model.OrderItem;
import com.d2c.order.query.OrderSearcher;
import com.d2c.order.query.ProductOrderSumSearcher;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface OrderItemService {

    /**
     * 通过订单商品的消息id，得到实体数据
     *
     * @param orderItemId
     * @return
     */
    OrderItem findById(Long orderItemId);

    /**
     * 通过id，查找封装后的dto对象
     *
     * @param orderItemId
     * @return
     */
    OrderItemDto findOrderItemDtoById(Long orderItemId);

    /**
     * 通过订单id得到订单明细集合
     *
     * @param orderId
     * @return
     */
    List<OrderItem> findByOrderId(Long orderId);

    /**
     * 通过订单id得到订单明细dto集合
     *
     * @param orderId
     * @return
     */
    List<OrderItemDto> findDtoByOrderId(Long orderId);

    /**
     * 通过订单编号
     *
     * @param orderSn
     * @return
     */
    List<OrderItem> findByOrderSn(String orderSn);

    /**
     * 通过订单id得到对应的订单物品
     *
     * @param orderId
     * @return
     */
    List<OrderItem> findSimpleByOrderId(Long orderId);

    /**
     * 根据订单号和购买条码确认订单明细 不存在男女同款时能确定一条明细，存在时可能多条
     *
     * @param orderSn
     * @param barCode
     * @return
     */
    OrderItem findByOrderSnAndSku(String orderSn, String barCode);

    /**
     * 根据快递单号查询订单明细
     *
     * @param deliverySn 快递单号
     * @return
     */
    List<OrderItemDto> findByDeliverySn(String deliverySn);

    /**
     * 查询用户订单明细
     *
     * @param orderItemId
     * @param memberInfoId
     * @return
     */
    OrderItem findByIdAndMemberInfoId(Long orderItemId, Long memberInfoId);

    /**
     * 通过条件查询得到订单明细的分页数据
     *
     * @param queryOrder
     * @param page
     * @return
     */
    PageResult<OrderItemDto> findPageBySearcher(OrderSearcher queryOrder, PageModel page);

    /**
     * 通过条件查询得到订单明细的集合数据
     *
     * @param queryOrder
     * @param page
     * @return
     */
    List<OrderItemDto> findBySearcher(OrderSearcher queryOrder, PageModel page);

    /**
     * 分页查询
     *
     * @param page
     * @param limit
     * @return
     */
    List<OrderItem> findPage(Integer page, Integer limit);

    /**
     * 通过条件查询得到订单明细的集合数据
     *
     * @param queryOrder
     * @param page
     * @return
     */
    PageResult<OrderItemDto> findSimpleBySearcher(OrderSearcher queryOrder, PageModel page);

    /**
     * 通过条件查询得到数量
     *
     * @param queryOrder
     * @return
     */
    Integer countBySearcher(OrderSearcher queryOrder);

    /**
     * 查找发货多少天订单明细
     *
     * @param date
     * @param interval
     * @param page
     * @return
     */
    PageResult<OrderItem> findDeliveredByDate(Date date, int interval, PageModel page);

    /**
     * 查找发货多少天订单明细数量
     *
     * @param date
     * @param interval
     * @return
     */
    int countDeliveredByDate(Date date, int interval);

    /**
     * 商品的销量统计，主要统计当前时间的商品的销售数量
     *
     * @return
     */
    List<HashMap<String, Object>> findProductSales();

    /**
     * 统计品牌30天的销量
     *
     * @param page
     * @param days
     * @return
     */
    PageResult<Map<String, Object>> findBrandSales(PageModel page, int days);

    /**
     * 即时商品销售排行
     *
     * @param page
     * @param searcher
     * @return
     */
    PageResult<Map<String, Object>> findRankingByProduct(PageModel page, ProductOrderSumSearcher searcher);

    /**
     * 销售排行榜
     *
     * @param searcher
     * @return
     */
    int countRankingByProduct(ProductOrderSumSearcher searcher);

    /**
     * 分销订单明细查询
     *
     * @param queryOrder
     * @param page
     * @return
     */
    PageResult<OrderItemDto> findPartnerOrder(OrderSearcher queryOrder, PageModel page);

    /**
     * 已发货代销商品生成对账单
     *
     * @param beginDate
     * @param page
     * @return
     */
    PageResult<OrderItem> findDtoForStatement(Date beginDate, PageModel page);

    /**
     * 商品日统计
     *
     * @param dayTime
     * @param type
     * @param page
     * @return
     */
    PageResult<Map<String, Object>> findProductOrderAnalysis(Date dayTime, String type, PageModel page);

    /**
     * 根据签收时间查询
     *
     * @param date
     * @param page
     * @return
     */
    PageResult<OrderItem> findSignedByDate(Date date, PageModel page);

    /**
     * 根据签收时间查询数量
     *
     * @param date
     * @return
     */
    int countSignedByDate(Date date);

    /**
     * 查找预计发货时间是该时间段的订单
     *
     * @param beginDate
     * @param endDate
     * @param page
     * @return
     */
    PageResult<OrderItem> findExpiredDelivery(Date beginDate, Date endDate, PageModel page);

    /**
     * 通过商品skuid得到明细的数量
     *
     * @param productSkuId
     * @return
     */
    int countByProductSkuId(Long productSkuId);

    /**
     * 统计各个明细状态的数量
     *
     * @param isStore
     * @param storeId
     * @return
     */
    Map<String, Object> getCountsMap(Boolean isStore, Long storeId);

    /**
     * 查询预期调拨的订单明细
     *
     * @param deadline
     * @param page
     * @return
     */
    PageResult<OrderItem> findPopOrderitems(Date deadline, PageModel page);

    /**
     * 查询预期调拨的订单明细
     *
     * @param deadline
     * @return
     */
    int countPopOrderitems(Date deadline);

    /**
     * 查询预期调拨的订单明细
     *
     * @param deadline
     * @param page
     * @return
     */
    PageResult<OrderItem> findNormalOrderitems(Date deadline, PageModel page);

    /**
     * 查询预期调拨的订单明细
     *
     * @param deadline
     * @return
     */
    int countNormalOrderitems(Date deadline);

    /**
     * 创建订单的日志
     *
     * @param orderId
     * @param orderItemId
     * @param logType
     * @param operator
     * @param info
     * @return
     */
    int createOrderLog(Long orderId, Long orderItemId, OrderLogType logType, String operator, String info);

    /**
     * 添加一条订单明细数据
     *
     * @param item
     * @return
     */
    OrderItemDto insert(OrderItem item);

    /**
     * 更新预计发货时间
     *
     * @param id
     * @param estimateDate
     * @param username
     * @return
     */
    int updateEstimateDate(Long id, Date estimateDate, String username);

    /**
     * 更改预计完结时间
     *
     * @param id
     * @param expectDate
     * @return
     */
    int updateExpectDate(Long id, Date expectDate);

    /**
     * 修改物流信息
     *
     * @param id
     * @param deliveryCorpName
     * @param deliverySn
     * @param deliveryBarCode
     * @param creator
     * @return
     */
    int updateDeliveryInfo(Long id, String deliveryCorpName, String deliverySn, String deliveryBarCode, String creator);

    /**
     * 修改真实发货条码
     *
     * @param barcode
     * @param id
     * @param username
     * @return
     */
    int updateBarcodeById(String barcode, Long id, String username);

    /**
     * 更新明细备注
     *
     * @param id
     * @param adminMemo
     * @param username
     * @return
     */
    int updateAdminMemo(Long id, String adminMemo, String username);

    /**
     * 更新拼团状态
     *
     * @param orderSn
     * @param status
     * @return
     */
    int updateCollageStatus(List<String> orderSn, Integer status);

    /**
     * 修改钱包分摊金额
     *
     * @param id
     * @param cashAmount
     * @param giftAmount
     * @return
     */
    int updateAccountAmount(Long id, BigDecimal cashAmount, BigDecimal giftAmount);

    /**
     * 重新打开已经关闭的订单
     *
     * @param orderId
     * @param orderItemId
     * @return
     */
    int doReOpen(OrderItem orderItem);

    /**
     * 明细支付成功
     *
     * @param order
     * @param orderItems
     * @return
     */
    int doPaySuccess(Order order, List<OrderItem> orderItems);

    /**
     * 指定门店发货
     *
     * @param id              明细id
     * @param itemMemo        备注
     * @param deliveryBarCode 发货条码
     * @param storeId         门店id
     * @param store_memo      门店备注
     * @param username
     * @return
     */
    int doBindStore(Long id, String itemMemo, String deliveryBarCode, Long storeId, String storeCode, String store_memo,
                    String username);

    /**
     * 设计师直发
     *
     * @param id
     * @param status
     * @param username
     * @return
     */
    int doDesignerDelivery(Long id, int status, String username);

    /**
     * 针对具体明细发货
     *
     * @param orderItemId
     * @param deliveryType
     * @param deliveryBarCode
     * @param deliveryCorpName
     * @param deliveryNo
     * @param operator
     * @param msg
     * @return
     */
    int doDeliveryItem(Long orderItemId, Integer deliveryType, String deliveryBarCode, String deliveryCorpName,
                       String deliveryNo, String operator, boolean msg, Integer deliveryQuantity);

    /**
     * 用户签收确认
     *
     * @param orderItemId
     * @param operator
     * @param logMemo
     * @param interval
     * @return
     */
    int doSign(Long orderItemId, String operator, String logMemo, Integer interval);

    /**
     * 货到付款签收
     *
     * @param orderItemId
     * @param confirmDate
     * @param operator
     * @param logMemo
     * @param interval
     * @return
     */
    int doCodSign(Long orderItemId, Date confirmDate, String operator, String logMemo, Integer interval);

    /**
     * 店主零售签收
     *
     * @param orderItems
     * @return
     */
    void doDplusSign(List<OrderItem> orderItems);

    /**
     * 明细成功
     *
     * @param id
     * @param diffAmount
     * @return
     */
    int doSuccess(Long id, BigDecimal diffAmount);

    /**
     * 关闭订单 1.只有货到付款的待确认订单允许整单关闭 2.未付款订单的关闭
     *
     * @param itemId
     * @param closeReason
     * @param operator
     * @param status
     * @param itemStatus
     * @return
     */
    int doCloseByOrder(Long id, String itemCloseMan, String itemCloseReason, String status);

    /**
     * 关闭订单明细，如果订单明细中已经全部关闭，则关闭整个订单
     *
     * @param orderItemId
     * @param closer
     * @param closeInfo
     * @return
     */
    int doCloseItem(Long orderItemId, String closer, String closeInfo);

    /**
     * 取消明细发货
     *
     * @param orderItemId
     * @param cancelReason
     * @param operator
     * @return
     */
    int doCancelDelivery(Long orderItemId, String cancelReason, String operator);

    /**
     * 取消门店发货
     *
     * @param orderItemId
     * @param info
     * @param username
     * @return
     */
    int doCancelStore(Long orderItemId, String info, String username);

    /**
     * 门店签收以后，客服不能强制收回指令，锁定明细
     *
     * @param id
     * @param info
     * @param username
     * @return
     */
    int doLock(Long id, String info, String username);

    /**
     * 取消锁定明细
     *
     * @param id       明细id
     * @param info     描述
     * @param username
     * @return
     */
    int doCancelLock(Long id, String info, String username);

    /**
     * 结算
     *
     * @param id
     * @param signDate
     * @param balanceMan
     * @param balanceMoney
     * @param balanceReason
     * @param billNumber
     * @return
     */
    int doBalance(Long id, Date signDate, String balanceMan, BigDecimal balanceMoney, String balanceReason,
                  String billNumber);

    /**
     * 更新结算数据
     *
     * @param id
     * @param balanceMoney
     * @param balanceMan
     * @param balanceReason
     * @return
     */
    int updateBalance(Long id, BigDecimal balanceMoney, String balanceMan, String balanceReason);

    /**
     * 更新赔偿金额
     *
     * @param id
     * @param compansation
     * @return
     */
    int doCompensation(Long id, BigDecimal compensation);

    /**
     * 更新退款单的ID
     *
     * @param refundId
     * @param orderItemId
     * @return
     */
    int doCreateReship(Long reshipId, Long orderItemId);

    /**
     * 更新退货单ID
     *
     * @param reshipId    退货单的id
     * @param orderItemId 订单明细的id
     * @return
     */
    int updateReship(Long reshipId, Long orderItemId);

    /**
     * 更新退款单的ID
     *
     * @param refundId
     * @param orderItemId
     * @return
     */
    int doCreateRefund(Long refundId, Long orderItemId);

    /**
     * 更新退款单的ID
     *
     * @param refundId
     * @param orderItemId
     * @return
     */
    int updateRefund(Long refundId, Long orderItemId);

    /**
     * 更新换货的ID
     *
     * @param exchangeId
     * @param orderItemId
     * @return
     */
    int doCreateExchange(Long exchangeId, Long orderItemId);

    /**
     * 更新换货的ID
     *
     * @param exchangeId
     * @param orderItemId
     * @return
     */
    int updateExchange(Long exchangeId, Long orderItemId);

    /**
     * 修改调拨状态
     *
     * @param id
     * @param requisition
     * @return
     */
    int updateRequisition(Long id, Integer requisition);

    /**
     * 对明细评价
     *
     * @param orderItemId
     * @param commentId
     * @return
     */
    int doComment(Long orderItemId, Long commentId);

    /**
     * 更新优惠分解金额
     *
     * @param id
     * @param couponPromotionAmount
     * @return
     */
    int updateCouponAmount(Long id, BigDecimal couponPromotionAmount);

    /**
     * 根据状态统计订单明细数量
     *
     * @param memberInfoId
     * @return
     */
    Map<String, Object> countGroupByStatus(Long memberInfoId);

    /**
     * 钱包支付的更新本金，赠金
     *
     * @param id
     * @param cashAmount
     * @param giftAmount
     * @return
     */
    int updateWalletAmount(Long id, BigDecimal cashAmount, BigDecimal giftAmount);

    /**
     * 即时商品消费排行汇总查看
     *
     * @param page
     * @param searcher
     * @return
     */
    PageResult<Map<String, Object>> findRankingSummaryByProduct(PageModel page, ProductOrderSumSearcher searcher);

    /**
     * 查询购买过的商品ID
     *
     * @param memberId
     * @return
     */
    List<Long> findProductIdByBuyer(Long memberId);

    /**
     * 查询当天交易成功的订单的金额
     *
     * @param page
     * @return
     */
    PageResult<Map<String, Object>> findDailyFinishAmount(PageModel page);

    /**
     * 查询最近销售的商品
     *
     * @param page
     * @return
     */
    List<Map<String, Object>> findRecentlySales(PageModel page);

    /**
     * 查找最近1个月销售量
     *
     * @param page
     * @param month
     * @return
     */
    List<Map<String, Object>> findRecentlyMonthSales(PageModel page, Integer month);

    /**
     * 计算分销订单7天内销量
     *
     * @param pageModel
     * @param days
     * @return
     */
    List<Map<String, Object>> findPartnerSales(PageModel pageModel, int days);

    /**
     * 查找该用户的订单
     *
     * @param memberInfoId
     * @param pager
     * @return
     */
    List<OrderItem> findByMemberInfoId(Long memberInfoId, PageModel pager);

    /**
     * 用户消费数据
     *
     * @param memberId
     * @param beginDate
     * @param endDate
     * @return
     */
    JSONObject findInfoByMemberId(Long memberId, Date beginDate, Date endDate);

    /**
     * 返利中奖用户支付金额
     *
     * @param beginPaymentDate 付款开始时间
     * @param endPaymentDate   付款结束时间
     * @param beginFinishDate  完结开始时间
     * @param endFinishDate    完结结束时间
     * @return
     */
    List<Map<String, Object>> findActualAmountForAward(Date beginPaymentDate, Date endPaymentDate, Date beginFinishDate,
                                                       Date endFinishDate, List<Long> awardIds);

    /**
     * 查找超期的订单明细
     *
     * @param page
     * @param dateline
     * @return
     */
    PageResult<OrderItem> findCompensation(PageModel page, Date dateline);

    /**
     * 查询草莓订单的物流
     *
     * @return
     */
    PageResult<OrderItem> findWaitUpdateDeliveryCaomeiOrder(PageModel page);

    /**
     * 查询草莓订单的物流数量
     *
     * @return
     */
    Integer countWaitUpdateDeliveryCaomeiOrder();

    /**
     * D+店的全部业绩
     *
     * @param startDate
     * @param endDate
     * @return
     */
    List<Map<String, Object>> findDplusTotalAmount(Date startDate, Date endDate);

    /**
     * D+店的单店业绩
     *
     * @param storeId
     * @param startDate
     * @param endDate
     * @return
     */
    List<Map<String, Object>> findDplusAmountByShop(Long storeId, Date startDate, Date endDate);

    /**
     * D+店订单做对账商品明细
     *
     * @param beginDate
     * @param page
     * @return
     */
    public PageResult<OrderItem> findDplusForStatement(Date beginDate, PageModel page);

}
