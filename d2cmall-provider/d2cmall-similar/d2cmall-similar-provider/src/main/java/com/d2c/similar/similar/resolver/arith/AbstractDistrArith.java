package com.d2c.similar.similar.resolver.arith;

import com.d2c.similar.entity.SimilarRuleDO;

/**
 * 分布算法
 *
 * @author wull
 */
public abstract class AbstractDistrArith {

    /**
     * 对象
     */
    protected Object bean;
    /**
     * 目标对象
     */
    protected Object target;
    /**
     * 距离
     */
    protected double dist;
    /**
     * 偏差
     */
    protected double deviation;
    protected SimilarRuleDO rule;

    public void init(SimilarRuleDO rule) {
        this.rule = rule;
    }

    protected abstract double doProb();

    public double prob(double dist, Object bean, Object target) {
        this.dist = dist;
        this.bean = bean;
        this.target = target;
        this.deviation = getDeviation();
        return check(doProb());
    }

    protected double getDeviation() {
        return rule.getDeviation();
    }

    public double check(double prob) {
        prob = (prob < 0) ? 0 : prob;
        prob = (prob > 1) ? 1 : prob;
        return prob;
    }

}
