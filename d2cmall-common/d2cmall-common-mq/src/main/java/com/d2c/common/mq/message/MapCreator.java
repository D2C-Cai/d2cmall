package com.d2c.common.mq.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Map;
import java.util.Map.Entry;

public class MapCreator extends BaseCreator {

    private static final Logger logger = LoggerFactory.getLogger(MapCreator.class);
    private Map<String, Object> map;

    public MapCreator(Map<String, Object> map) {
        this.map = map;
    }

    public MapCreator(Map<String, Object> map, Long seconds) {
        this.map = map;
        this.seconds = seconds;
    }

    @Override
    public Message createMessage(Session session) throws JMSException {
        MapMessage mapMessage = session.createMapMessage();
        setMessage(mapMessage);
        if (map != null) {
            for (Entry<String, Object> entry : map.entrySet()) {
                mapMessage.setObject(entry.getKey(), entry.getValue());
            }
        }
        logger.debug("队列消息:" + map);
        return mapMessage;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

}
