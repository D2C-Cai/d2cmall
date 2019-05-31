package com.d2c.flame.jms.listener;

import com.d2c.common.core.cache.old.CacheTimerHandler;
import com.d2c.common.mq.enums.MqEnum;
import com.d2c.common.mq.listener.AbsMqListener;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;

@Component
public class MemoryCacheTopicListener extends AbsMqListener {

    protected static final Log logger = LogFactory.getLog(MemoryCacheTopicListener.class);

    @Override
    public void onMessage(Message message) {
        MapMessage mapMsg = (MapMessage) message;
        try {
            String key = mapMsg.getString("mem_cache");
            if (StringUtils.isNotBlank(key)) {
                this.remove(key);
                logger.info("success remove key:" + key);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void remove(String key) {
        if (StringUtils.isNotBlank(key)) {
            for (String k : key.split(",")) {
                CacheTimerHandler.removeCache(k);
            }
        }
    }

    @Override
    public MqEnum getMqEnum() {
        return MqEnum.FLUSH_CACHE;
    }

}
