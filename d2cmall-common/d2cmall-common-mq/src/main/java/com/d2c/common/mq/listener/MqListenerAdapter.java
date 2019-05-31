package com.d2c.common.mq.listener;

import com.d2c.common.core.helper.SpringHelper;
import com.d2c.common.mq.enums.MqEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.jms.ConnectionFactory;
import java.util.HashMap;
import java.util.Map;

public class MqListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(MqListenerAdapter.class);
    @Autowired(required = false)
    private ConnectionFactory connectionFactory;

    @PostConstruct
    public void init() {
        if (connectionFactory == null)
            return;
        logger.info("开始初始化消息监听队列......");
        Map<String, BaseMqListener> types = SpringHelper.getBeansOfType(BaseMqListener.class);
        for (BaseMqListener listener : types.values()) {
            MqEnum en = listener.getMqEnum();
            if (en != null) {
                String beanName = listener.getClass().getSimpleName() + "Container";
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("connectionFactory", connectionFactory);
                map.put("destination", en.getDestination());
                map.put("pubSubDomain", en.isTopic());
                map.put("messageListener", listener);
                SpringHelper.registerBean(beanName, SimpleMqListenerContainer.class, map);
                logger.info("获得消息监听者" + listener + ", 监听管理类:" + beanName);
            }
        }
    }

}
