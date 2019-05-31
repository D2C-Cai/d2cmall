package com.d2c.similar.dto;

import com.d2c.common.api.dto.BaseDTO;
import com.d2c.similar.dto.similar.SimilarStepDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SimilarDTO extends BaseDTO {

    private static final long serialVersionUID = -4903284310787030896L;
    private String id;
    private Date gmtModified;
    private Object beanId;
    private Object targetId;
    private String schemeName;
    private Object bean;
    private Object target;
    private Double prob;
    private List<SimilarStepDTO> steps = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Object getBeanId() {
        return beanId;
    }

    public void setBeanId(Object beanId) {
        this.beanId = beanId;
    }

    public Object getTargetId() {
        return targetId;
    }

    public void setTargetId(Object targetId) {
        this.targetId = targetId;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Double getProb() {
        return prob;
    }

    public void setProb(Double prob) {
        this.prob = prob;
    }

    public List<SimilarStepDTO> getSteps() {
        return steps;
    }

    public void setSteps(List<SimilarStepDTO> steps) {
        this.steps = steps;
    }

}
