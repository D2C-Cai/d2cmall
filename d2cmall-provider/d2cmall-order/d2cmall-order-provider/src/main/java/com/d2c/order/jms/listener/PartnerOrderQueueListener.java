package com.d2c.order.jms.listener;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.mq.enums.MqEnum;
import com.d2c.common.mq.listener.AbsMqListener;
import com.d2c.member.service.PartnerService;
import com.d2c.order.model.Order;
import com.d2c.order.model.Order.OrderType;
import com.d2c.order.model.OrderItem;
import com.d2c.order.service.OrderItemService;
import com.d2c.order.service.OrderService;
import com.d2c.order.service.tx.PartnerTxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.MapMessage;
import javax.jms.Message;
import java.util.List;

@Component
public class PartnerOrderQueueListener extends AbsMqListener {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Reference
    private PartnerTxService partnerTxService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private PartnerService partnerService;

    @Override
    public void onMessage(Message message) {
        MapMessage mapMsg = (MapMessage) message;
        try {
            String orderSn = mapMsg.getString("orderSn");
            Order order = orderService.findByOrderSn(orderSn);
            if (order == null) {
                logger.info("订单号：" + orderSn + "不存在！");
            }
            if (order.getType() != null && order.getType().equals(OrderType.distribution.name())) {
                return;
            }
            List<OrderItem> orderItems = orderItemService.findByOrderSn(orderSn);
            if (order.getPartnerId() != null) {
                // 生成返利单
                partnerTxService.doOrderBill(order.getPartnerId(), orderItems);
            }
            if (order.getTerminalId() != null) {
                Long inviterId = Long.valueOf(order.getTerminalId().split(":")[1]);
                if (order.getTerminalId().indexOf("first") != -1) {
                    // 首次开店
                    Integer level = Integer.valueOf(order.getTerminalId().split(":")[2]);
                    partnerService.doCreate(inviterId, order.getMemberId(), level);
                }
                // 礼包奖励
                partnerTxService.doOrderGift(inviterId, order, orderItems);
                // 积分奖励
                partnerTxService.doOrderPoint(inviterId, orderItems);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public MqEnum getMqEnum() {
        return MqEnum.PARTNER_ORDER;
    }

}
