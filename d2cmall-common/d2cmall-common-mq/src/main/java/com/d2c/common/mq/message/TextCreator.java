package com.d2c.common.mq.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

public class TextCreator extends BaseCreator {

    private static final Logger logger = LoggerFactory.getLogger(TextCreator.class);
    private String text;

    public TextCreator(String text) {
        this.text = text;
    }

    public TextCreator(String text, Long seconds) {
        this.text = text;
        this.seconds = seconds;
    }

    @Override
    public Message createMessage(Session session) throws JMSException {
        TextMessage textMessage = session.createTextMessage(text);
        setMessage(textMessage);
        logger.debug("文本消息:" + text);
        return textMessage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
