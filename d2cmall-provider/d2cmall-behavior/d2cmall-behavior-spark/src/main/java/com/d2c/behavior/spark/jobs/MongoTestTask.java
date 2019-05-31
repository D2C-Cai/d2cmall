package com.d2c.behavior.spark.jobs;

import com.d2c.frame.spark.job.BaseSparkTask;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 商品推荐计算任务
 *
 * @author wull
 */
//@Component
public class MongoTestTask extends BaseSparkTask {

    private static final long serialVersionUID = 6654810484276275559L;

    @Scheduled(fixedDelay = 100 * 1000)
    public void execute() {
    }

    public void run(JavaSparkContext jsc) {
//		spark.addCheckpointDir();
//		
//		JavaRDD<Document> ratingDocs = jsc.textFile(dataPath + "/mllib/test2.data").map(s -> {
//			Document doc = new Document();
//			String[] sarray = s.split(",");
//			doc.put("user", Long.parseLong(sarray[0]));
//			doc.put("product", Long.parseLong(sarray[1]));
//			doc.put("rating", Double.parseDouble(sarray[2]));
//			return doc;
//		});
//		Map<String, String> writeOverrides = new HashMap<String, String>();
//	    writeOverrides.put("collection", "personSparkDO");
//	    WriteConfig inWcf = WriteConfig.create(jsc).withOptions(writeOverrides);
//		sparkTemplate.dropCollection(jsc, inWcf);
//		MongoSpark.save(ratingDocs, inWcf);
//		
//
//		JavaMongoRDD<Document> rdd = MongoSpark.load(jsc);
//
//		logger.info("加载数据: " + rdd.count());
//
//		JavaRDD<Rating> ratings = rdd.map(bean -> {
//			return new Rating(bean.get("user", Long.class).intValue(), bean.get("product", Long.class).intValue(),
//					bean.get("rating", Double.class));
//		});
//
//		logger.info("数据初始化完成...");
//		int rank = 10;
//		int numIterations = 10;
//		MatrixFactorizationModel model = ALS.train(JavaRDD.toRDD(ratings), rank, numIterations, 0.01);
//
//		JavaRDD<Document> saveDocs = model.recommendProductsForUsers(20).toJavaRDD().map(value -> {
//			Document doc = new Document();
//			System.out.println("开始读取Tuple._1 = " + value._1);
//			doc.put("user", value._1);
//			List<Map<String, ?>> ratingList = new ArrayList<>();
//			for (Rating rating : value._2) {
//				System.out.println("预测" + rating.user() + "-" + rating.product() + ": " + rating.rating());
//				Map<String, Object> map = new HashMap<>();
//				map.put("product", rating.product());
//				map.put("rating", rating.rating());
//				ratingList.add(map);
//			}
//			doc.put("ratings", ratingList);
//			return doc;
//		});
//
//		sparkTemplate.dropCollection(jsc);
//		MongoSpark.save(saveDocs);
    }

}
