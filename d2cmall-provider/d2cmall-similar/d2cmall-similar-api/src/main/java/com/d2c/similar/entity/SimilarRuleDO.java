package com.d2c.similar.entity;

import com.d2c.common.api.model.BaseUserDO;
import com.d2c.similar.constant.Constant;

import javax.persistence.Table;

/**
 * 类似商品计算规则
 *
 * @author wull
 */
@Table(name = Constant.PLAT_PREFIX + "similar_rule")
public class SimilarRuleDO extends BaseUserDO {

    private static final long serialVersionUID = 7902814112969375973L;
    private Integer schemeId;
    private String ruleCode;
    private String ruleName;
    private String ruleType;
    private Boolean isDimen;
    private String fieldExpr;
    private String fieldType;
    private Double deviation; //偏差
    private Double weight;
    private String remark;

    public Integer getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(Integer schemeId) {
        this.schemeId = schemeId;
    }

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

    public Boolean getIsDimen() {
        return isDimen;
    }

    public void setIsDimen(Boolean isDimen) {
        this.isDimen = isDimen;
    }

    public String getFieldExpr() {
        return fieldExpr;
    }

    public void setFieldExpr(String fieldExpr) {
        this.fieldExpr = fieldExpr;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public Double getDeviation() {
        return deviation;
    }

    public void setDeviation(Double deviation) {
        this.deviation = deviation;
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
