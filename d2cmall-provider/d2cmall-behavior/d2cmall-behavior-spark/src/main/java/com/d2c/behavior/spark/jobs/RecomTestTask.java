package com.d2c.behavior.spark.jobs;

import com.d2c.frame.spark.job.BaseSparkTask;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 商品推荐计算任务
 *
 * @author wull
 */
//@Component
public class RecomTestTask extends BaseSparkTask {

    private static final long serialVersionUID = -5117594668837434457L;

    @Scheduled(fixedDelay = 10 * 1000)
    public void execute() {
    }
//	public void run(JavaSparkContext jsc) {
//
//		spark.addCheckpointDir();
//
//		// Load and parse the data
//		String path = dataPath + "/mllib/test2.data";
//		JavaRDD<String> data = jsc.textFile(path);
//		JavaRDD<Rating> ratings = data.map(s -> {
//			String[] sarray = s.split(",");
//			return new Rating(Integer.parseInt(sarray[0]), Integer.parseInt(sarray[1]), Double.parseDouble(sarray[2]));
//		});
//
//		// Build the recommendation model using ALS
//		int rank = 10;
//		int numIterations = 10;
//		MatrixFactorizationModel model = ALS.train(JavaRDD.toRDD(ratings), rank, numIterations, 0.01);
//
//		// for(Rating rating : model.recommendProducts(5, 4)){
//		// System.out.println("预测" + rating.user() + "-" + rating.product() + ":
//		// " + rating.rating());
//		// }
//		// for(Rating rating : model.recommendUsers(1, 5)){
//		// System.out.println("喜欢商品 " + rating.product() +"的用户 " + rating.user()
//		// + ": " + rating.rating());
//		// }
//
//		model.recommendProductsForUsers(20).toJavaRDD().foreach(t -> {
//			System.out.println("开始读取Tuple._1 = " + t._1);
//			for (Rating rating : t._2) {
//				System.out.println("预测" + rating.user() + "-" + rating.product() + ": " + rating.rating());
//			}
//		});
//
//		// data.foreach(new VoidFunction<String>() {
//		// private static final long serialVersionUID = 1L;
//		// @Override
//		// public void call(String t) throws Exception {
//		// System.out.println("test: " + t);
//		// }
//		// });
//
//		// System.out.println("预测1-1:" + model.predict(1, 1));
//
//		// 校验RMSE（root-mean-square error）评估误差是否收敛
//		// rmse(ratings, model);
//
//		// Save and load model
//		// model.save(jsc.sc(), "target/tmp/myCollaborativeFilter");
//		// MatrixFactorizationModel sameModel =
//		// MatrixFactorizationModel.load(jsc.sc(),
//		// "target/tmp/myCollaborativeFilter");
//
//	}
//
//	/**
//	 * 校验RMSE（root-mean-square error）评估误差是否收敛
//	 * <p>
//	 * 当RMSE值变化很小时，就可以认为结果已经收敛 <br>
//	 * （预测数据 - 初始数据）的平方差
//	 */
//	public void rmse(JavaRDD<Rating> ratings, MatrixFactorizationModel model) {
//		// Evaluate the model on rating data
//		JavaRDD<Tuple2<Object, Object>> userProducts = ratings.map(r -> new Tuple2<>(r.user(), r.product()));
//		// 初始数据
//		JavaPairRDD<Tuple2<Integer, Integer>, Double> predictions = JavaPairRDD
//				.fromJavaRDD(model.predict(JavaRDD.toRDD(userProducts)).toJavaRDD()
//						.map(r -> new Tuple2<>(new Tuple2<>(r.user(), r.product()), r.rating())));
//		// 预测数据, 交集两者非空
//		JavaRDD<Tuple2<Double, Double>> ratesAndPreds = JavaPairRDD
//				.fromJavaRDD(ratings.map(r -> new Tuple2<>(new Tuple2<>(r.user(), r.product()), r.rating())))
//				.join(predictions).values();
//		// mean 累计平均
//		double MSE = ratesAndPreds.mapToDouble(pair -> {
//			double err = pair._1() - pair._2();
//			return err * err;
//		}).mean();
//		System.out.println("Mean Squared Error = " + MSE);
//	}
}
