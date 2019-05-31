package com.d2c.order.jms.listener;

import com.d2c.common.mq.enums.MqEnum;
import com.d2c.common.mq.listener.AbsMqListener;
import com.d2c.order.model.RechargeRule;
import com.d2c.order.service.RechargeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.MapMessage;
import javax.jms.Message;

@Component
public class RechargeRuleQueueListener extends AbsMqListener {

    @Autowired
    private RechargeRuleService rechargeRuleService;

    public void onMessage(Message message) {
        MapMessage mapMsg = (MapMessage) message;
        try {
            Long id = mapMsg.getLong("id");
            Integer status = mapMsg.getInt("status");
            Long time = mapMsg.getLong("time");
            String username = mapMsg.getString("username");
            RechargeRule old = rechargeRuleService.findById(id);
            if (old != null) {
                if (status == 1 && Long.toString(old.getStartTime().getTime()).equals(time.toString())) {
                    rechargeRuleService.doMark(id, username, 1);
                } else if (status == 0 && Long.toString(old.getEndTime().getTime()).equals(time.toString())) {
                    rechargeRuleService.doMark(id, username, 0);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public MqEnum getMqEnum() {
        return MqEnum.TIMING_RULE;
    }

}
