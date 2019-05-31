package com.d2c.common.math.arith;

import com.d2c.common.math.utils.ProbUt;
import org.apache.commons.math3.distribution.NormalDistribution;

/**
 * 概率算法
 * <p>按两者距离和偏差值，来计算两者相似度概率
 *
 * @author wull
 */
public class ProbArith {

    /**
     * 正态曲线函数
     */
    private static final NormalDistribution nd = new NormalDistribution();
    private static final double maxProb = nd.density(0);

    /**
     * 按正态曲线(高斯曲线)计算概率
     *
     * @param dist      两者距离
     * @param deviation 偏差
     * @return 分布概率值
     */
    public static double normalProb(double dist, double deviation) {
        double prob = nd.density(dist / deviation) / maxProb;
        return ProbUt.checkProb(prob);
    }

    /**
     * 简单线性算法
     * <p>prob = 1 - dist/deviation
     * <br>相似度随着距离从 0 - deviation 线性下降
     * <br>dist = 0 时 prob = 1;
     * <br>dist = deviation 时 prob = 0;
     *
     * @param dist      两者距离
     * @param deviation 偏差
     * @return 分布概率值
     */
    public static double lineProb(double dist, double deviation) {
        if (deviation < 0) {
            deviation = 1;
        }
        return ProbUt.checkProb(1 - (dist / deviation));
    }

}
