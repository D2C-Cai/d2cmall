package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.MemberInfo;
import com.d2c.order.dto.OrderDto;
import com.d2c.order.model.CollageOrder;
import com.d2c.order.model.Order;
import com.d2c.order.model.Order.OrderStatus;
import com.d2c.order.model.OrderItem;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderService {

    /**
     * 通过订单ID，得到订单
     *
     * @param orderSn
     * @return
     */
    Order findById(Long id);

    /**
     * 通过订单编号，得到订单
     *
     * @param orderSn
     * @return
     */
    Order findByOrderSn(String orderSn);

    /**
     * 通过tempId，得到订单
     *
     * @param tempId
     * @return
     */
    OrderDto findByTempId(String tempId);

    /**
     * 通过订单id，查找订单的dto数据
     *
     * @param id
     * @return
     */
    OrderDto findOrderDtoById(Long id);

    /**
     * 初始化分销逻辑
     *
     * @param memberInfo
     * @param orderDto
     * @param parent_id
     * @param level
     * @param qualified
     * @return
     */
    OrderDto doInitPartner(MemberInfo memberInfo, OrderDto orderDto, Long parent_id, Integer level, boolean qualified);

    /**
     * 创建订单
     *
     * @param order
     * @return
     */
    Order insert(Order order);

    /**
     * 删除订单
     *
     * @param orderId
     * @param loginCode
     * @return
     */
    int delete(Long orderId, String loginCode);

    /**
     * 根据订单编号和用户Id获取订单
     *
     * @param orderSn
     * @param memberInfoId
     * @return
     */
    OrderDto doFindByOrderSnAndMemberInfoId(String orderSn, Long memberInfoId);

    /**
     * 根据订单Id和用户Id获取订单
     *
     * @param orderId
     * @param memberInfoId
     * @return
     */
    OrderDto doFindByIdAndMemberInfoId(Long orderId, Long memberInfoId);

    /**
     * 更新订单接受信息和备注
     *
     * @param orderId
     * @param addressId
     * @param code
     * @param memo
     * @param memberInfoId
     * @return
     */
    OrderDto updateTempOrder(Long orderId, Long addressId, String code, String memo, Long memberInfoId);

    /**
     * 更新收货信息
     *
     * @param id
     * @param contact
     * @param province
     * @param city
     * @param district
     * @param address
     * @param reciver
     * @param username
     * @return
     */
    int updateAddress(Long id, String contact, String province, String city, String district, String address,
                      String reciver, String username);

    /**
     * 系统备注（客服端）
     *
     * @param id
     * @param adminMemo
     * @param username
     * @return
     */
    int updateAdminMemo(Long id, String adminMemo, String username);

    /**
     * 更新关闭信息
     *
     * @param id
     * @param closeReason
     * @param orderCloseMan
     * @param orderStatus
     * @return
     */
    int updateCloseInfo(Long id, String closeReason, String orderCloseMan, int orderStatus);

    /**
     * 修改标识
     *
     * @param id
     * @param flag
     * @return
     */
    int updateFlag(Long id, int flag);

    /**
     * 修改代付标志
     *
     * @param id
     * @param substitute
     * @return
     */
    int updateSubstitute(Long id, int substitute);

    /**
     * 更新订单状态
     *
     * @param id
     * @param oldOrderStatus
     * @param newOrderStatus
     * @param operator
     * @return
     */
    int updateOrderStatus(Long id, OrderStatus oldOrderStatus, OrderStatus newOrderStatus, String operator);

    /**
     * 订单支付成功
     *
     * @param orderSn
     * @param paymentId
     * @param paySn
     * @param paymentType
     * @param payedAmount
     * @param cashAmount
     * @param giftAmount
     * @return
     */
    int doPaySuccess(String orderSn, Long paymentId, String paySn, Integer paymentType, BigDecimal payedAmount,
                     BigDecimal cashAmount, BigDecimal giftAmount);

    /**
     * 明细发货完成，调用订单发货完成
     *
     * @param orderId
     * @param deliveryCorpName
     * @param deliverySn
     * @param lastModifyMan
     * @return
     */
    int doDeliveryByItem(Long orderId, String deliveryCorpName, String deliverySn, String operator);

    /**
     * 当订单明细变为最终态，订单相应状态的变更
     *
     * @param orderId
     * @param oldOrderStatus
     * @param orderStatus
     * @param operator
     * @return
     */
    int doSuccessByItem(Long orderId, OrderStatus oldOrderStatus, String operator);

    /**
     * 整单发货
     *
     * @param id
     * @param deliveryCorpName
     * @param deliverySn
     * @param name
     * @return
     */
    int doDeliverOrder(Long id, String deliveryCorpName, String deliverySn, String name, String ip);

    /**
     * 查询需要提醒的订单
     *
     * @param time1
     * @param time2
     * @return
     */
    List<Long> findRemindOrder(long time1, long time2);

    /**
     * 查找分销未付款的单据
     *
     * @param endDate
     * @param pager
     * @return
     */
    PageResult<Map<String, Object>> findPartnerOrder(Date endDate, PageModel pager);

    /**
     * 更新优惠券
     *
     * @param orderId
     * @param couponId
     * @param couponAmount
     * @return
     */
    int updateCouponAmount(Long orderId, Long couponId, BigDecimal couponAmount);

    /**
     * 设置是否已开票
     *
     * @param id
     * @param invoiced
     */
    int updateInvoiced(Long id, int invoiced, String operator);

    /**
     * 根据terminal查询
     *
     * @param terminal
     * @param terminalId
     * @return
     */
    List<Order> findByTerminal(String terminal, String terminalId);

    /**
     * 统计用户在某时间段内待付款和已付款的砍价订单数
     *
     * @param memberId
     * @param startDate
     * @param endDate
     * @return
     */
    int countBargain(Long memberId, Date startDate, Date endDate);

    /**
     * 考拉订单支付
     *
     * @param orderSn
     * @param orderItems
     */
    void pushKaolaOrder(String orderSn, List<OrderItem> orderItems);

    /**
     * 草莓订单创建
     *
     * @param order
     * @param orderItems
     */
    void pushCaomeiOrder(Order order, List<OrderItem> orderItems);

    /**
     * 模拟生成订单
     *
     * @param collageOrder
     */
    int doSimulateOrder(CollageOrder collageOrder);

}
