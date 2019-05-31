package com.d2c.behavior.spark.stream;

import com.d2c.behavior.api.mongo.model.depict.DepictDO;
import com.d2c.common.kafka.enums.KafkaEnum;
import com.d2c.frame.spark.SparkHandler;
import com.d2c.frame.spark.annotation.SparkStream;
import com.d2c.frame.spark.stream.BaseSparkStream;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import scala.Tuple2;

/**
 * 用户画像 Spark 数据计算
 *
 * @author wull
 */
//@Component
public class DepictStream extends BaseSparkStream {

    private static final long serialVersionUID = -8507656802700850305L;

    @SparkStream(topicEnum = KafkaEnum.DepictData, out = "DepictDO")
    public void depictData(SparkHandler spark) {
        JavaPairDStream<String, DepictDO> pds = spark.getKafkaDStream().mapToPair(r -> {
//			DepictDataDO data = (DepictDataDO) r.value();
//			logger.debug("开始处理数据... key: " + r.key());
//			TagTypeDO type = data.getTagType();
//			//获取TagDO
//			if(type == null) return null;
//			Object value = SpelUt.parseBean(type.getInputSpel(), data.getData());
//			TagDO select = null;
//			for(TagDO tag :type.getTags()) {
//				if(MyCriteriaResolver.check(tag.getJsonBean(), value)){
//					select = tag;
//					break;
//				}
//			}
//			DepictDO depict = new DepictDO(data.getPersonId(), data.getMemberId(), type);
//			depict.addTag(select, value);
//			return new Tuple2<String, DepictDO>(depict.getId(), depict);
            return new Tuple2<String, DepictDO>("", null);
        });
        pds = updateStateByKey(pds, (bean, last) -> {
            DepictDO depict = last;
            if (depict != null) {
                depict.merge(bean);
                logger.info("****合并数据: " + depict.getId() + "  bean: " + bean.getId());
            } else {
                depict = bean;
                logger.info("****新数据: " + depict.getId());
            }
            return depict.update();
        });
        //持久化数据到文件
        pds.checkpoint(Durations.seconds(10));
        pds.foreachRDD(rdd -> {
//			sparkTemplate.update(rdd.values());
        });
    }

}
