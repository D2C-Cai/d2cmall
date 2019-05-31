package com.d2c.similar.dto.similar;

import com.d2c.common.api.dto.BaseDTO;

public class SimilarStepDTO extends BaseDTO {

    private static final long serialVersionUID = -4903284310787030896L;
    private String ruleName;
    private Object value;
    private Object tgValue;
    private Double dist;
    private Double prob;
    private Double weight;
    private Double weightSum;
    private Double wprob;
    private String error;

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getTgValue() {
        return tgValue;
    }

    public void setTgValue(Object tgValue) {
        this.tgValue = tgValue;
    }

    public Double getDist() {
        return dist;
    }

    public void setDist(Double dist) {
        this.dist = dist;
    }

    public Double getProb() {
        return prob;
    }

    public void setProb(Double prob) {
        this.prob = prob;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getWeightSum() {
        return weightSum;
    }

    public void setWeightSum(Double weightSum) {
        this.weightSum = weightSum;
    }

    public Double getWprob() {
        return wprob;
    }

    public void setWprob(Double wprob) {
        this.wprob = wprob;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
