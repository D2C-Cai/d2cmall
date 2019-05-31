package com.d2c.order.jms.listener;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.mq.enums.MqEnum;
import com.d2c.common.mq.listener.AbsMqListener;
import com.d2c.order.service.tx.PartnerTxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.jms.MapMessage;
import javax.jms.Message;
import java.util.Date;

@Component
public class PartnerCashQueueListener extends AbsMqListener {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Reference
    private PartnerTxService partnerTxService;

    @Override
    public void onMessage(Message message) {
        MapMessage mapMsg = (MapMessage) message;
        try {
            Long id = mapMsg.getLong("id");
            String sn = mapMsg.getString("sn");
            partnerTxService.doPaymentCash(id, sn, "sys", new Date(), "sys");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public MqEnum getMqEnum() {
        return MqEnum.PARTNER_CASH;
    }

}
