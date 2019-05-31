package com.d2c.behavior.spark.manage;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.behavior.api.mongo.model.TagTypeDO;
import com.d2c.behavior.api.services.TagService;
import com.d2c.common.base.utils.JsonUt;
import com.d2c.frame.spark.SparkHandler;
import com.d2c.frame.spark.annotation.Spark;
import com.d2c.frame.spark.core.SparkTemplate;
import com.d2c.order.model.OrderItem;
import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.config.WriteConfig;
import com.mongodb.spark.rdd.api.java.JavaMongoRDD;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.recommendation.ALS;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;
import org.apache.spark.sql.SparkSession;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scala.Tuple2;

import java.util.*;
import java.util.regex.Pattern;

/**
 * REST服务
 *
 * @author wull
 */
@Component
public class TestManager {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SparkTemplate sparkTemplate;
    @Reference
    private TagService tagService;

    @Spark
    public void test(SparkHandler spark) {
        logger.info("调用 spark mongo test.." + spark);
        List<TagTypeDO> list = tagService.findTagType(OrderItem.class);
        logger.info("=========> 获得标签TagTypeDO.." + JsonUt.serialize(list));
    }

    @Spark
    public void basic(SparkSession sparkSession) {
        JavaRDD<String> lines = sparkSession.read().textFile("README.md").javaRDD();
        JavaRDD<String> words = lines.flatMap(s -> Arrays.asList(Pattern.compile(" ").split(s)).iterator());
        JavaPairRDD<String, Integer> ones = words.mapToPair(s -> new Tuple2<>(s, 1));
        JavaPairRDD<String, Integer> counts = ones.reduceByKey((i1, i2) -> i1 + i2);
        List<Tuple2<String, Integer>> output = counts.collect();
        for (Tuple2<?, ?> tuple : output) {
            System.out.println(tuple._1() + ": " + tuple._2());
        }
    }

    @Spark(in = "sparkTestDO", out = "sparkTestOutDO")
    public Object spark(SparkHandler spark) {
        logger.info("调用 spark mongo.." + spark);
        JavaSparkContext jsc = spark.getJsc();
        spark.addCheckpointDir();
        JavaRDD<Document> ratingDocs = jsc.textFile("data/mllib/test2.data").map(s -> {
            Document doc = new Document();
            String[] sarray = s.split(",");
            doc.put("user", Long.parseLong(sarray[0]));
            doc.put("product", Long.parseLong(sarray[1]));
            doc.put("rating", Double.parseDouble(sarray[2]));
            return doc;
        });
        Map<String, String> writeOverrides = new HashMap<String, String>();
        writeOverrides.put("collection", "personSparkDO");
        WriteConfig inWcf = WriteConfig.create(jsc).withOptions(writeOverrides);
        sparkTemplate.dropCollection(jsc, inWcf);
        MongoSpark.save(ratingDocs, inWcf);
        JavaMongoRDD<Document> rdd = MongoSpark.load(jsc);
        logger.info("加载数据: " + rdd.count());
        JavaRDD<Rating> ratings = rdd.map(bean -> {
            return new Rating(bean.get("user", Long.class).intValue(), bean.get("product", Long.class).intValue(),
                    bean.get("rating", Double.class));
        });
        logger.info("数据初始化完成...");
        int rank = 10;
        int numIterations = 10;
        MatrixFactorizationModel model = ALS.train(JavaRDD.toRDD(ratings), rank, numIterations, 0.01);
        JavaRDD<Document> saveDocs = model.recommendProductsForUsers(20).toJavaRDD().map(value -> {
            Document doc = new Document();
            System.out.println("开始读取Tuple._1 = " + value._1);
            doc.put("user", value._1);
            List<Map<String, ?>> ratingList = new ArrayList<>();
            for (Rating rating : value._2) {
                System.out.println("预测" + rating.user() + "-" + rating.product() + ": " + rating.rating());
                Map<String, Object> map = new HashMap<>();
                map.put("product", rating.product());
                map.put("rating", rating.rating());
                ratingList.add(map);
            }
            doc.put("ratings", ratingList);
            return doc;
        });
        sparkTemplate.dropCollection(jsc);
        MongoSpark.save(saveDocs);
        return saveDocs.collect();
    }

}
