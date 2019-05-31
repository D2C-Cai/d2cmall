package com.d2c.common.mq.listener;

import com.d2c.common.base.exception.MqException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.io.Serializable;

public abstract class AbsMqListener implements BaseMqListener {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 收到文本消息时覆盖
     */
    public void onTextMessage(String text) {
        logger.debug("收到TextMessage消息:" + text);
    }

    /**
     * 收到对象消息时覆盖
     */
    public void onObjectMessage(Serializable obj) {
        logger.debug("收到ObjectMessage消息:" + obj);
    }

    /**
     * 收到Map消息时覆盖
     */
    public void onMapMessage(MapMessage map) {
        logger.debug("收到MapMessage消息:" + map);
    }
    // ***********************************************************************

    public void onMessage(Message message) {
        try {
            logger.debug("收到测试消息:" + message);
            if (message instanceof TextMessage) {
                // 文本消息
                TextMessage tm = (TextMessage) message;
                this.onTextMessage(tm.getText());
            } else if (message instanceof ObjectMessage) {
                // 对象消息
                ObjectMessage om = (ObjectMessage) message;
                this.onObjectMessage(om.getObject());
            } else if (message instanceof MapMessage) {
                // Map消息
                MapMessage mm = (MapMessage) message;
                this.onMapMessage(mm);
            }
        } catch (JMSException e) {
            throw new MqException("MQ消息接收失败", e);
        }
    }

}
