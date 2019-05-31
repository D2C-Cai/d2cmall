package com.d2c.order.jms.listener;

import com.d2c.common.mq.enums.MqEnum;
import com.d2c.common.mq.listener.AbsMqListener;
import com.d2c.logger.model.SmsLog.SmsLogType;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.logger.support.SmsBean;
import com.d2c.order.model.Order;
import com.d2c.order.model.OrderItem;
import com.d2c.order.service.OrderItemService;
import com.d2c.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.MapMessage;
import javax.jms.Message;
import java.util.List;

@Component
public class OrderRemindQueueListener extends AbsMqListener {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private MsgUniteService msgUniteService;

    @Override
    public void onMessage(Message message) {
        MapMessage mapMsg = (MapMessage) message;
        try {
            Long orderId = mapMsg.getLong("orderId");
            Integer minutes = mapMsg.getInt("minutes");
            if (orderId != null) {
                this.doSendMsg(orderId, minutes);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void doSendMsg(Long orderId, Integer minutes) {
        Order order = orderService.findById(orderId);
        List<OrderItem> list = orderItemService.findSimpleByOrderId(orderId);
        boolean isSend = false;
        if (order.getOrderStatus().intValue() == 1 && order.getPaymentType() != 3) {
            isSend = true;
        }
        if (isSend) {
            String subject = "您有尚未支付的订单。";
            String content = "尊敬的用户，您的订单还有" + minutes + "分钟即将自动关闭，请尽快完成支付，感谢您对D2C的支持。";
            PushBean pushBean = new PushBean(order.getMemberId(), content, 21);
            pushBean.setAppUrl("/details/order/" + order.getOrderSn());
            MsgBean msgBean = new MsgBean(order.getMemberId(), 21, subject, content);
            msgBean.setAppUrl("/details/order/" + order.getOrderSn());
            msgBean.setPic(list.get(0).getProductImg());
            SmsBean smsBean = new SmsBean(null, order.getMemberMobile(), SmsLogType.REMIND, content);
            msgUniteService.sendMsg(smsBean, pushBean, msgBean, null);
        }
    }

    @Override
    public MqEnum getMqEnum() {
        return MqEnum.ORDER_REMIND;
    }

}
