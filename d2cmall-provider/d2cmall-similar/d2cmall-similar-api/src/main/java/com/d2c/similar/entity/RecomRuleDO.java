package com.d2c.similar.entity;

import com.d2c.common.api.model.BaseDO;
import com.d2c.similar.constant.Constant;

import javax.persistence.Table;

/**
 * 商品推荐规则表
 *
 * @author wull
 */
@Table(name = Constant.PLAT_PREFIX + "recom_rule")
public class RecomRuleDO extends BaseDO {

    private static final long serialVersionUID = -2173018153140445680L;
    private String ruleCode;
    private String ruleName;
    private String ruleType;
    private String fieldExpr;
    private Double weight;
    private Double max;
    private String remark;

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getFieldExpr() {
        return fieldExpr;
    }

    public void setFieldExpr(String fieldExpr) {
        this.fieldExpr = fieldExpr;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
