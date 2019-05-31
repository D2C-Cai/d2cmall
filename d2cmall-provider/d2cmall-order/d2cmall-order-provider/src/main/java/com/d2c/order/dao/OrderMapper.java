package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.Order;
import com.d2c.order.model.Order.OrderStatus;
import com.d2c.order.query.OrderSaleSearcher;
import com.d2c.order.query.OrderSearcher;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderMapper extends SuperMapper<Order> {

    Order findByTempId(String id);

    Order findByOrderSn(String orderSn);

    List<Long> findRemindOrder(@Param("time1") long time1, @Param("time2") long time2);

    List<Map<String, Object>> findPartnerOrder(@Param("endDate") Date endDate, @Param("pager") PageModel page);

    int countPartnerOrder(@Param("endDate") Date endDate);

    Order findByOrderSnAndMemberInfoId(@Param("orderSn") String orderSn, @Param("memberInfoId") Long memberInfoId);

    Order findByIdAndMemberInfoId(@Param("id") Long id, @Param("memberInfoId") Long memberInfoId);

    int updateOrderStatus(@Param("orderId") Long orderId, @Param("orderStatus") OrderStatus orderStatus);

    int updateTempOrder(Order order);

    int updateAddress(@Param("orderId") Long orderId, @Param("contact") String contact,
                      @Param("province") String province, @Param("city") String city, @Param("district") String district,
                      @Param("address") String address, @Param("receiver") String receiver);

    int updateAdminMemo(@Param("orderId") Long orderId, @Param("adminMemo") String adminMemo);

    int updateCouponAmount(@Param("orderId") Long orderId, @Param("couponId") Long couponId,
                           @Param("couponAmount") BigDecimal couponAmount);

    int updateCloseInfo(@Param("id") Long id, @Param("closeReason") String closeReason,
                        @Param("orderCloseMan") String orderCloseMan, @Param("orderStatus") int orderStatus);

    int updateFlag(@Param("orderId") Long id, @Param("flag") int flag);

    int updateSubstitute(@Param("id") Long id, @Param("substitute") int substitute);

    int updatePaySuccess(@Param("orderId") Long orderId, @Param("paymentId") Long paymentId,
                         @Param("paymentType") Integer paymentType, @Param("alipaySn") String alipaySn,
                         @Param("paidAmount") BigDecimal paidAmount);

    int doDeliveryOrder(@Param("orderId") Long orderId, @Param("deliveryCorpName") String deliveryCorpName,
                        @Param("deliverySn") String deliverySn);

    int deleteById(Long id);

    int updateInvoiced(@Param("id") Long id, @Param("invoiced") int invoiced, @Param("drawer") String drawer);

    List<Order> findByTerminal(@Param("terminal") String terminal, @Param("terminalId") String terminalId);

    int countBargain(@Param("memberId") Long memberId, @Param("startDate") Date startDate,
                     @Param("endDate") Date endDate);

    List<Order> findBy(@Param("memberInfoId") Long memberInfoId, @Param("orderSn") String orderSn,
                       @Param("productName") String productName, @Param("startDate") Date beginOrderDate,
                       @Param("endDate") Date endOrderDate, @Param("orderStatus") OrderStatus[] orderStatus,
                       @Param("itemStatus") String[] itemStatus, @Param("designer") String designer,
                       @Param("commented") Integer commented, @Param("pager") PageModel pager);

    int countBy(@Param("memberInfoId") Long memberInfoId, @Param("orderSn") String orderSn,
                @Param("productName") String productName, @Param("startDate") Date beginOrderDate,
                @Param("endDate") Date endOrderDate, @Param("orderStatus") OrderStatus[] orderStatus,
                @Param("itemStatus") String[] itemStatus, @Param("designer") String designer,
                @Param("commented") Integer commented);

    List<Map<String, Object>> countGropByStatus(@Param("memberInfoId") Long memberInfoId);
    // ************* OrderExport **********************

    List<Order> findBySearcher(@Param("searcher") OrderSearcher searcher, @Param("pager") PageModel pager);

    int countBySearcher(@Param("searcher") OrderSearcher searcher);

    List<Order> findSimpleBySearch(@Param("searcher") OrderSearcher searcher, @Param("pager") PageModel pager);

    int countSimpleBySearch(@Param("searcher") OrderSearcher searcher);

    int countSimpleByMemberId(@Param("memberId") Long memberId);

    List<Map<String, Object>> countGroupByStatus();

    BigDecimal countOrdersAmount(@Param("searcher") OrderSearcher query);

    List<Map<String, Object>> findCountAndAmountGroupByPayType(@Param("searcher") OrderSearcher searcher);

    List<Map<String, Object>> findCountAndAmountGroupByStatus(@Param("searcher") OrderSearcher searcher);

    List<Map<String, Object>> findCountAndAmountGroupByDevice(@Param("searcher") OrderSearcher searcher);

    List<Order> findWaitingSuccessOrder(@Param("orderStatuses") OrderStatus[] orderStatus,
                                        @Param("pager") PageModel pager);

    int countWaitingSuccessOrder(@Param("orderStatuses") OrderStatus[] orderStatus);

    Order findPushMinutesOrder(@Param("minutes") Long minutes);

    List<Long> findExpireOrder(@Param("expireTime") long expireTime, @Param("types") Order.OrderType[] types,
                               @Param("cross") Integer cross, @Param("pager") PageModel pager);

    int countExpireOrder(@Param("expireTime") int expireTime, @Param("types") Order.OrderType[] types,
                         @Param("cross") Integer cross);

    List<Map<String, Object>> findSaleBySearcher(@Param("searcher") OrderSaleSearcher searcher,
                                                 @Param("page") PageModel page);

    int countSaleBySearcher(@Param("searcher") OrderSaleSearcher searcher);

    Map<String, Object> findOrderStat(@Param("memberInfoId") Long memberInfoId);

}
