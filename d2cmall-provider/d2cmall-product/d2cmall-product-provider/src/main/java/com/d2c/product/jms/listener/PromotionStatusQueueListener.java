package com.d2c.product.jms.listener;

import com.d2c.common.mq.enums.MqEnum;
import com.d2c.common.mq.listener.AbsMqListener;
import com.d2c.product.model.Promotion;
import com.d2c.product.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.MapMessage;
import javax.jms.Message;

@Component
public class PromotionStatusQueueListener extends AbsMqListener {

    @Autowired
    private PromotionService promotionService;

    public void onMessage(Message message) {
        MapMessage mapMsg = (MapMessage) message;
        try {
            Long id = mapMsg.getLong("id");
            Integer mark = mapMsg.getInt("mark");
            Long date = mapMsg.getLong("date");
            Promotion old = promotionService.findSimpleById(id);
            if (mark == 1) {
                if (Long.valueOf(old.getStartTime().getTime()).equals(date)) {
                    promotionService.doMark(mark == 1, id);
                }
            } else {
                if (Long.valueOf(old.getEndTime().getTime()).equals(date)) {
                    promotionService.doMark(mark == 1, id);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public MqEnum getMqEnum() {
        return MqEnum.TIMING_PROMOTION;
    }

}
