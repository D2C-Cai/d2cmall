package com.d2c.similar.similar.resolver.dist;

import com.d2c.similar.entity.SimilarRuleDO;

/**
 * 相似度距离算法
 *
 * @author wull
 */
public abstract class AbstractDistance {

    protected SimilarRuleDO rule;
    protected Object value;
    protected Object target;

    public void init(SimilarRuleDO rule) {
        this.rule = rule;
        doInit();
    }

    public void doInit() {
    }

    protected abstract double doExec();

    public double exec(Object value, Object target) {
        this.value = value;
        this.target = target;
        return doExec();
    }

}
