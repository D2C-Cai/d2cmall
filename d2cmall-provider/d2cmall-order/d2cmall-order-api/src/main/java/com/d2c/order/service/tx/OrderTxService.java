package com.d2c.order.service.tx;

import com.d2c.order.dto.OrderDto;
import com.d2c.order.model.Order.OrderStatus;

public interface OrderTxService {

    /**
     * 创建订单
     *
     * @param order
     * @param coupons
     * @param redPacket
     * @return
     */
    OrderDto doCreateOrder(OrderDto order, String coupons, Integer redPacket);

    /**
     * 关闭订单
     *
     * @param orderId
     * @param closeReason
     * @param operator
     * @param closeStatus
     * @return
     */
    int doCloseOrder(Long orderId, String closeReason, String operator, OrderStatus closeStatus);

    /**
     * 过期未支付订单的关闭
     *
     * @param orderId
     * @return
     */
    int doCloseExpireOrder(Long orderId);

    /**
     * 拼团失败订单的关闭
     *
     * @param orderSn
     * @return
     */
    int doCloseByCollage(String orderSn);

}
