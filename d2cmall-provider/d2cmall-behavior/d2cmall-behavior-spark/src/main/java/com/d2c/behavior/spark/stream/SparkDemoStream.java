package com.d2c.behavior.spark.stream;

import com.d2c.common.kafka.enums.KafkaEnum;
import com.d2c.frame.spark.SparkHandler;
import com.d2c.frame.spark.annotation.SparkStream;
import com.d2c.frame.spark.stream.BaseSparkStream;

/**
 * 用户画像 Spark 数据计算
 *
 * @author wull
 */
//@Component
public class SparkDemoStream extends BaseSparkStream {

    private static final long serialVersionUID = -8507656802700850305L;

    @SparkStream(topicEnum = KafkaEnum.TestTopic, time = 10)
    public void spark(SparkHandler spark) {
        spark.addCheckpointDir();
        spark.getKafkaDStream().foreachRDD(rdd -> {
            rdd.foreach(x -> {
                logger.info("接收kafka数据... topic: " + x);
            });
        });
    }

}
