package com.d2c.common.math.arith;

import com.d2c.common.math.utils.ProbUt;

import java.util.Arrays;
import java.util.List;

/**
 * 贝叶斯算法
 * 说明: 已知某事件在某条件下的概率，计算该事件再次发生的可能性
 *
 * @author wull
 */
public class BayesArith {

    /**
     * 朴素贝叶斯概率
     * 公式: P1*P2..Pn
     */
    public static double simpleBayes(List<Double> probs) {
        double p = 1;
        if (probs.size() == 0) {
            return ProbUt.MIN_PROB;
        }
        for (double prob : probs) {
            p = p * prob;
        }
        return p;
    }

    public static double simpleBayes(Double... probs) {
        return simpleBayes(Arrays.asList(probs));
    }
    //*****************************************************

    /**
     * 贝叶斯联合概率，根据多个概率联合得到某事件概率
     * 默认假设：贝叶斯先验概率为50%，且所有事件为独立事件。（先验概率可加入到等式）
     * 公式: P1*P2..Pn / (P1*P2..Pn + (1-P1)*(1-P2)...(1-Pn))
     *
     * @param values 多事件概率
     * @return 贝叶斯联合概率
     */
    public static double uniteBayes(List<Double> probs) {
        double p = 1;
        double unp = 1;
        if (probs.size() == 0) {
            return ProbUt.MIN_PROB;
        }
        for (double prob : probs) {
            prob = ProbUt.checkBayes(prob);
            p = p * prob;
            unp = unp * (1 - prob);
        }
        return p / (p + unp);
    }

    public static double uniteBayes(Double... probs) {
        return uniteBayes(Arrays.asList(probs));
    }

    public static double uniteBayes(List<Double> list, Double... probs) {
        list.addAll(Arrays.asList(probs));
        return uniteBayes(list);
    }

}
