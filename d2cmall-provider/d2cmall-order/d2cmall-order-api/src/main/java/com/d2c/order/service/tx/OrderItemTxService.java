package com.d2c.order.service.tx;

import com.d2c.order.model.Order.OrderStatus;

import java.math.BigDecimal;

public interface OrderItemTxService {

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
    int doCloseByOrder(Long itemId, String closeReason, String operator, OrderStatus status, String itemStatus);

    /**
     * 系统交易成功
     *
     * @param orderItemId
     * @param operator
     * @param off
     * @param diffAmount
     * @return
     */
    int doSysSuccess(Long orderItemId, String operator, boolean off, BigDecimal diffAmount);

    /**
     * 自动交易成功
     *
     * @param orderItemId
     * @param operator
     * @param off
     * @param diffAmount
     * @return
     */
    int doAutoSuccess(Long itemId, String operator, boolean off, BigDecimal diffAmount);

}
