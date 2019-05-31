package com.d2c.common.kafka.enums;

public enum KafkaEnum implements KafkaTopic {
    DepictData("订单数据画像"),
    TestTopic("Kafka测试组");
    String remark;

    KafkaEnum(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return remark;
    }

    @Override
    public String topic() {
        return name();
    }
}
