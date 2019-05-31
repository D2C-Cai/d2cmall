package com.d2c.common.kafka.serializer;

import com.d2c.common.base.utils.BeanUt;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class ObjectSerializer implements Serializer<Object> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, Object data) {
        return BeanUt.toByte(data);
    }

    @Override
    public void close() {
    }

}
