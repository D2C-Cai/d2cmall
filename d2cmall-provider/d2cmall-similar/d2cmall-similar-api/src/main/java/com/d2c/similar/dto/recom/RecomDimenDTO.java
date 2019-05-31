package com.d2c.similar.dto.recom;

import com.d2c.common.api.dto.BaseDTO;

/**
 * 推荐规则维度解析
 */
public class RecomDimenDTO extends BaseDTO {

    private static final long serialVersionUID = -4903284310787030896L;
    private String ruleName;
    private Object fieldValue;
    private Double value;
    private Double weight;
    private Double score;

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

}
