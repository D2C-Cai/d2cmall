package com.d2c.behavior.spark.manage;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.behavior.api.mongo.enums.TagFromEnum;
import com.d2c.behavior.api.mongo.model.TagDO;
import com.d2c.behavior.api.mongo.model.TagTypeDO;
import com.d2c.behavior.api.mongo.model.depict.DepictDO;
import com.d2c.behavior.api.mongo.model.depict.DepictDataDO;
import com.d2c.behavior.api.services.TagService;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.common.mongodb.build.AggrBuild;
import com.d2c.frame.spark.SparkHandler;
import com.d2c.frame.spark.annotation.Spark;
import com.d2c.frame.spark.core.BaseSparkManager;
import com.mongodb.spark.rdd.api.java.JavaMongoRDD;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Component;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户画像 Spark 数据计算
 *
 * @author wull
 */
@Component
public class DepictManager extends BaseSparkManager {

    private static final long serialVersionUID = -6632120680479934902L;
    private static final long PAGE_SIZE = 10000;
    @Reference
    private transient TagService tagService;

    /**
     *
     */
    @Spark(inclz = DepictDataDO.class, outclz = DepictDO.class)
    public void depictData(SparkHandler spark) {
        logger.info("用户画像 Spark 数据计算开始..");
        JavaSparkContext jsc = spark.getJsc();
        spark.addCheckpointDir();
        Map<String, List<TagTypeDO>> typeMap = tagService.findTagTypeMap();
        AggrBuild ab = AggrBuild.build();
        ab.and("done").ne(true);
        ab.add(Aggregation.limit(PAGE_SIZE));
        JavaMongoRDD<Document> rdd = sparkTemplate.load(jsc, ab);
        if (rdd.isEmpty()) return;
        rdd.cache();
        JavaPairRDD<String, DepictDO> prdd = rdd.flatMapToPair(bean -> {
            List<TagTypeDO> types = typeMap.get(bean.getString("dataClz"));
            if (types == null) return null;
            List<Tuple2<String, DepictDO>> resList = new ArrayList<>();
            types.forEach(type -> {
                //根据标签获取需要处理的解析值，例如: orderItem.productPrice
                Object value = BeanUt.getValueExpr(bean.get("data"), type.getInputSpel());
                if (value == null) return;
                TagFromEnum tagFrom = TagFromEnum.getValueOf(type.getTagFrom());
                TagDO select = tagFrom.selectTag(type.getTags(), value);
                DepictDO depict = new DepictDO(bean.getString("personId"), bean.getLong("memberId"), type);
                depict.addTag(select, value);
                resList.add(new Tuple2<String, DepictDO>(depict.getId(), depict));
            });
            return resList.iterator();
        });
        prdd = prdd.reduceByKey((bean, next) -> bean.merge(next))
                .mapValues(bean -> bean.update());
        sparkTemplate.save(prdd.values(), (bean, last) -> last.merge(bean).update());
        sparkTemplate.save(rdd.map(doc -> doc.append("done", true)), DepictDataDO.class);
        //分页读取，单页读满尝试读取下一页
        if (rdd.count() >= PAGE_SIZE) {
            depictData(spark);
        }
    }

}
