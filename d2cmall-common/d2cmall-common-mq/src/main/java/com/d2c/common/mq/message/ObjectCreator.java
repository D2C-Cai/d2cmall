package com.d2c.common.mq.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import java.io.Serializable;

public class ObjectCreator extends BaseCreator {

    private static final Logger logger = LoggerFactory.getLogger(ObjectCreator.class);
    private Serializable obj;

    public ObjectCreator(Serializable obj) {
        this.obj = obj;
    }

    public ObjectCreator(Serializable obj, Long seconds) {
        this.obj = obj;
        this.seconds = seconds;
    }

    @Override
    public Message createMessage(Session session) throws JMSException {
        ObjectMessage objectMessage = session.createObjectMessage(obj);
        setMessage(objectMessage);
        logger.debug("队列消息:" + obj);
        return objectMessage;
    }

    public Serializable getObj() {
        return obj;
    }

    public void setObj(Serializable obj) {
        this.obj = obj;
    }

}
