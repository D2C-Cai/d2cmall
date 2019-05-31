package com.d2c.similar.recom.resolver;

import com.d2c.common.base.utils.BeanUt;
import com.d2c.common.core.base.resolver.BaseResolver;
import com.d2c.similar.dto.recom.RecomDimenDTO;
import com.d2c.similar.entity.RecomRuleDO;
import com.d2c.similar.mongo.model.RecomDO;
import com.d2c.similar.recom.enums.RecomRuleType;

/**
 * 商品推荐解析器
 *
 * @author wull
 */
public class RecomResolver extends BaseResolver<RecomRuleDO> {

    protected RecomDO recom;
    protected RecomDimenDTO dimen;
    // ********************** similar **************************

    /**
     * 计算数据
     */
    public RecomDO resolver(RecomDO bean) {
        recom = bean;
        //责任链递归计算
        resolver();
        double score = 0;
        for (RecomDimenDTO dm : recom.getDimens()) {
            score += dm.getScore();
        }
        recom.setScore(score);
        return recom;
    }

    /**
     * 计算数据
     */
    @Override
    protected void doResolver() {
        Object fieldValue = BeanUt.getValueExpr(recom.getData(), rule.getFieldExpr());
        Double value = RecomRuleType.getRecomValue(rule, fieldValue);
        dimen = new RecomDimenDTO();
        dimen.setRuleName(rule.getRuleName());
        dimen.setFieldValue(fieldValue);
        dimen.setValue(value);
        dimen.setWeight(rule.getWeight());
        dimen.setScore(value * dimen.getWeight());
        recom.addRecomDimen(dimen);
    }

    /**
     * 传递数据到下一参数
     */
    @Override
    public void doNext() {
        RecomResolver next = (RecomResolver) this.next;
        next.recom = recom;
    }

    @Override
    protected void doInit() {
    }

}
