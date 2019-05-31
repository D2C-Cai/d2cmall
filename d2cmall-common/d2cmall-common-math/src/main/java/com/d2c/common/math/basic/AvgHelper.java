package com.d2c.common.math.basic;

import java.util.Arrays;
import java.util.List;

/**
 * 平均算法
 *
 * @author wull
 */
public class AvgHelper {

    /**
     * 平均值
     * 公式: (p1  + ... + pn)  / n
     */
    public static double avg(List<Double> probs) {
        double avg = 0;
        for (double p : probs) {
            avg += p;
        }
        return avg / probs.size();
    }

    public static double avg(Double... probs) {
        return avg(Arrays.asList(probs));
    }

    /**
     * 加权平均算法
     * 公式: (p1 * w1) + ... + (pn * wn)  / (w1 + ... + wn)
     */
    public static double avgWeight(List<Double> probs, List<Double> weights) {
        double sw = 0;
        for (double w : weights) {
            sw += w;
        }
        double avg = 0;
        int i = 0;
        for (double p : probs) {
            avg += p * weights.get(i);
            i++;
        }
        return avg / sw;
    }

}
