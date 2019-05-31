package com.d2c.behavior.test;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.SparkSession;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class JavaWordTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void test() {
        logger.info("开始执行测试....");
        SparkSession spark = SparkSession.builder().master("local").appName("JavaWordCount").getOrCreate();
        JavaRDD<String> lines = spark.read().textFile("README.md").javaRDD();
        JavaRDD<String> words = lines.flatMap(s -> Arrays.asList(Pattern.compile(" ").split(s)).iterator());
        JavaPairRDD<String, Integer> ones = words.mapToPair(s -> new Tuple2<>(s, 1));
        JavaPairRDD<String, Integer> counts = ones.reduceByKey((i1, i2) -> i1 + i2);
        List<Tuple2<String, Integer>> output = counts.collect();
        for (Tuple2<?, ?> tuple : output) {
            System.out.println(tuple._1() + ": " + tuple._2());
        }
        spark.stop();
    }

}
