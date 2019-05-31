package com.d2c.common.math.utils;

public class ProbUt {

    //默认概率
    public static final double DEF_PROB = 0.5;
    //概率小于0时MIN_BAYES_PROB，概率大于1时为 (1 - MIN_BAYES_PROB)
    public static final double MIN_PROB = 0.0001;

    /**
     * 概率数值校验
     * <p>概率值必须 0 < prob < 1 之间
     *
     * @param prob
     * @return prob 概率值
     */
    public static double checkProb(double prob) {
        prob = (prob < 0) ? 0 : prob;
        prob = (prob > 1) ? 1 : prob;
        return prob;
    }

    /**
     * 贝叶斯概率数值校验
     * <p>概率值必须 0.0001 < prob < 0.9999 之间
     *
     * @param prob
     * @return prob 概率值
     */
    public static double checkBayes(double prob) {
        return checkProb(prob, MIN_PROB);
    }

    public static double checkProb(double prob, double minProb) {
        double maxProb = 1 - minProb;
        prob = (prob < minProb) ? minProb : prob;
        prob = (prob > maxProb) ? maxProb : prob;
        return prob;
    }

}
