package com.d2c.common.kafka;

import com.d2c.common.kafka.enums.KafkaTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * Kafka 管理类
 *
 * @author wull
 */
public class KafkaHandler {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private int i = 0;
    @Autowired
    private KafkaTemplate<Object, Object> template;

    public ListenableFuture<SendResult<Object, Object>> send(KafkaTopic topic, Object data) {
        return send(topic.topic(), data);
    }

    public ListenableFuture<SendResult<Object, Object>> send(KafkaTopic topic, Object key, Object data) {
        return send(topic.topic(), key, data);
    }

    public ListenableFuture<SendResult<Object, Object>> send(String topic, Object data) {
        return send(topic, i++, data);
    }

    public ListenableFuture<SendResult<Object, Object>> send(String topic, Object key, Object data) {
        logger.info("kafka 消息队列: " + topic + " - " + key + " - " + data);
        return template.send(topic, key, data);
    }

}
