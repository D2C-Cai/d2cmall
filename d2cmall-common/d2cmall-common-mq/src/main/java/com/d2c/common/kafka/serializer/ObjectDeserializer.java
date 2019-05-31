package com.d2c.common.kafka.serializer;

import com.d2c.common.base.utils.BeanUt;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class ObjectDeserializer implements Deserializer<Object> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public Object deserialize(String topic, byte[] data) {
        return BeanUt.toObject(data);
    }

    @Override
    public void close() {
    }

}
