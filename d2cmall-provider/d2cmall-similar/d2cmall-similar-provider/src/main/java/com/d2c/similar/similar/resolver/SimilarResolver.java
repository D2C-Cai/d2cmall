package com.d2c.similar.similar.resolver;

import com.d2c.common.base.utils.AssertUt;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.common.core.base.resolver.BaseResolver;
import com.d2c.similar.dto.similar.SimilarStepDTO;
import com.d2c.similar.entity.SimilarRuleDO;
import com.d2c.similar.entity.SimilarSchemeDO;
import com.d2c.similar.mongo.model.SimilarDO;
import com.d2c.similar.similar.resolver.arith.AbstractDistrArith;
import com.d2c.similar.similar.resolver.dist.AbstractDistance;

public class SimilarResolver extends BaseResolver<SimilarRuleDO> {

    protected AbstractDistrArith arith;
    protected AbstractDistance distance;
    protected SimilarDO data;
    protected Object value;
    protected Object target;
    protected Double dist;
    protected Double prob;
    // ********************** similar **************************

    /**
     * 计算数据相似度
     */
    public SimilarDO similar(SimilarSchemeDO scheme, Object bean, Object target) {
        data = new SimilarDO(scheme, bean, target);
        // 责任链递归计算
        resolver();
        data.setProb(getAvgProb());
        return data;
    }

    /**
     * 计算数据
     */
    @Override
    protected void doResolver() {
        try {
            value = BeanUt.getValueExpr(data.getBean(), rule.getFieldExpr());
            target = BeanUt.getValueExpr(data.getTarget(), rule.getFieldExpr());
            AssertUt.notNull(value, data.getBeanId() + "的 " + rule.getFieldExpr() + "解析值不能为空");
            AssertUt.notNull(target, data.getTargetId() + "的 " + rule.getFieldExpr() + "解析值不能为空");
            // 相似度距离计算
            dist = distance.exec(value, target);
            // 计算概率得分，按距离和算法进行计算
            prob = arith.prob(dist, value, target);
            addSimilarStep();
        } catch (Exception e) {
//			logger.error(data + "错误..." + e.getMessage());
            addErrorStep(e);
        }
    }

    /**
     * 传递数据到下一参数
     */
    @Override
    public void doNext() {
        SimilarResolver next = (SimilarResolver) this.next;
        next.data = data;
    }

    @Override
    protected void doInit() {
    }

    @Override
    public String toString() {
        return this.rule.getRuleName();
    }

    /**
     * 添加解析步骤
     */
    private SimilarStepDTO addSimilarStep() {
        SimilarStepDTO step = new SimilarStepDTO();
        step.setRuleName(rule.getRuleName());
        step.setValue(value);
        step.setTgValue(target);
        step.setWeight(rule.getWeight());
        step.setDist(dist);
        step.setProb(prob);
        data.addStep(step);
        return step;
    }

    private SimilarStepDTO addErrorStep(Exception e) {
        dist = 0.0;
        prob = 0.0;
        SimilarStepDTO step = addSimilarStep();
        step.setError(e.getMessage());
        return step;
    }

    /**
     * 计算平均相似度
     */
    private double getAvgProb() {
        double probs = 0;
        double wsum = 0;
        for (SimilarStepDTO step : data.getSteps()) {
            wsum += step.getWeight();
        }
        for (SimilarStepDTO step : data.getSteps()) {
            double p = step.getProb() * step.getWeight();
            step.setWprob(p / wsum);
            probs += p;
        }
        return probs / wsum;
    }
    // ***********************************

    public AbstractDistrArith getArith() {
        return arith;
    }

    public void setArith(AbstractDistrArith arith) {
        this.arith = arith;
    }

    public AbstractDistance getDistance() {
        return distance;
    }

    public void setDistance(AbstractDistance distance) {
        this.distance = distance;
    }

}
