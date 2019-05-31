package com.d2c.order.service.tx;

import com.d2c.order.model.Order;
import com.d2c.order.model.OrderItem;
import com.d2c.order.model.PartnerCash;
import com.d2c.order.model.PartnerItem.PartnerLogType;
import com.d2c.order.model.PartnerWithhold;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface PartnerTxService {

    /**
     * 生成返利
     *
     * @param partnerId
     * @param orderItems
     * @return
     */
    int doOrderBill(Long partnerId, List<OrderItem> orderItems);

    /**
     * 礼包奖励
     *
     * @param inviterId
     * @param order
     * @param orderItems
     * @return
     */
    int doOrderGift(Long inviterId, Order order, List<OrderItem> orderItems);

    /**
     * 积分奖励
     *
     * @param inviterId
     * @param orderItems
     * @return
     */
    int doOrderPoint(Long inviterId, List<OrderItem> orderItems);

    /**
     * 完结返利
     *
     * @param id
     * @param operator
     * @param off
     * @param diffAmount
     * @param own
     * @param partnerAmount
     * @return
     */
    int doBillSuccess(Long id, String operator, boolean off, BigDecimal diffAmount, boolean own,
                      BigDecimal partnerAmount);

    /**
     * 申请提现
     *
     * @param partnerCash
     * @return
     */
    PartnerCash doApplyCash(PartnerCash partnerCash);

    /**
     * 拒绝提现
     *
     * @param id
     * @param refuseReason
     * @param refuseMan
     * @param confirmOperateMan
     * @return
     */
    int doRefuseCash(Long id, String refuseReason, String refuseMan, String confirmOperateMan);

    /**
     * 同意提现
     *
     * @param id
     * @param paySn
     * @param payMan
     * @param payDate
     * @param operator
     * @return
     */
    int doPaymentCash(Long id, String paySn, String payMan, Date payDate, String operator);

    /**
     * 其他奖励
     *
     * @param partnerId
     * @param inviteId
     * @param toLoginCode
     * @param amount
     * @param operator
     * @return
     */
    int doAward(Long partnerId, Long inviteId, String toLoginCode, BigDecimal amount, String operator);

    /**
     * 礼包奖励
     *
     * @param partnerId
     * @param amount
     * @param operator
     * @param orderId
     * @param orderSn
     * @return
     */
    int doGift(Long partnerId, BigDecimal amount, String operator, Long orderId, String orderSn);

    /**
     * 订单返利
     *
     * @param id
     * @param amount
     * @param actualAmount
     * @param operator
     * @param billId
     * @param billSn
     * @param logType
     * @return
     */
    int doRebate(Long id, BigDecimal amount, BigDecimal actualAmount, String operator, Long billId, String billSn,
                 PartnerLogType logType);

    /**
     * 差价扣减
     *
     * @param partnerWithhold
     * @param type
     * @return
     */
    PartnerWithhold doWithhold(PartnerWithhold partnerWithhold, PartnerLogType type);

}
