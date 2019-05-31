package com.d2c.behavior.listener;

import com.d2c.behavior.mongo.dto.EventDTO;
import com.d2c.common.base.utils.JsonUt;
import com.d2c.common.kafka.listener.BaseKafkaListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TestKafkaListener extends BaseKafkaListener {

    @KafkaListener(id = "d2c", topics = "annotated1")
    public void listenMsg(EventDTO msg) {
        logger.info("收到kafka消息: " + JsonUt.serialize(msg));
    }

}