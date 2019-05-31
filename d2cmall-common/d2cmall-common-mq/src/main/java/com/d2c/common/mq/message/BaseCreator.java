package com.d2c.common.mq.message;

import org.apache.activemq.ScheduledMessage;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;

public abstract class BaseCreator implements MessageCreator {

    protected Message message;
    protected Long seconds;

    /**
     * 参数配置
     */
    protected void setProperty() throws JMSException {
        // 消息延迟
        if (seconds != null && seconds > 0) {
            message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, seconds * 1000);
        }
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) throws JMSException {
        this.message = message;
        setProperty();
    }

    public Long getSeconds() {
        return seconds;
    }

    public void setSeconds(Long seconds) {
        this.seconds = seconds;
    }

}
