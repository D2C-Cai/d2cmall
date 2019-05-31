package com.d2c.jms.listener;

import com.d2c.common.mq.enums.MqEnum;
import com.d2c.common.mq.listener.AbsMqListener;
import com.d2c.logger.support.SmsChoice;
import com.d2c.logger.support.SmsChoice.Region;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.TextMessage;

@Component
public class SmsChoiceTopicListener extends AbsMqListener {

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            String choice = textMessage.getText();
            SmsChoice.setting.put(Region.Domestic, choice);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public MqEnum getMqEnum() {
        return MqEnum.SMS_CHOICE;
    }

}
