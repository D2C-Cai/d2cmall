package com.d2c.similar.mongo.model;

import com.d2c.common.mongodb.model.SuperMongoDO;
import com.d2c.similar.dto.similar.SimilarStepDTO;
import com.d2c.similar.entity.SimilarSchemeDO;
import com.d2c.similar.helper.SimilarHelper;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class SimilarDO extends SuperMongoDO {

    private static final long serialVersionUID = -4903284310787030896L;
    @Id
    private String id;
    @Indexed
    private Object beanId;
    @Indexed
    private Object targetId;
    private String schemeName;
    private Object bean;
    private Object target;
    @Indexed
    private Double prob;
    private List<SimilarStepDTO> steps = new ArrayList<>();

    public SimilarDO() {
    }

    public SimilarDO(SimilarSchemeDO scheme, Object bean, Object target) {
        this.schemeName = scheme.getSchemeName();
        this.beanId = SimilarHelper.findId(bean);
        this.targetId = SimilarHelper.findId(target);
        this.bean = bean;
        this.target = target;
    }

    @Override
    public String toString() {
        return schemeName + "计算 " + beanId + "-" + targetId + " 相似度";
    }

    public String initId() {
        id = beanId + "-" + targetId;
        return id;
    }

    public void addStep(SimilarStepDTO step) {
        steps.add(step);
    }
    // **********************************************

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getBeanId() {
        return beanId;
    }

    public void setBeanId(Object beanId) {
        this.beanId = beanId;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public Object getTargetId() {
        return targetId;
    }

    public void setTargetId(Object targetId) {
        this.targetId = targetId;
    }

    public Double getProb() {
        return prob;
    }

    public void setProb(Double prob) {
        this.prob = prob;
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

    public List<SimilarStepDTO> getSteps() {
        return steps;
    }

    public void setSteps(List<SimilarStepDTO> steps) {
        this.steps = steps;
    }

}
