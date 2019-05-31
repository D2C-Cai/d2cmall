package com.d2c.similar.dto;

import com.d2c.common.api.dto.EntityDTO;
import com.d2c.similar.entity.SimilarRuleDO;

import java.util.List;

public class SimilarSchemeDTO extends EntityDTO {

    private static final long serialVersionUID = -4903284310787030896L;
    private String schemeName;
    private Integer categoryId;
    private Integer maxSize;
    private List<SimilarRuleDO> rules;

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }

    public List<SimilarRuleDO> getRules() {
        return rules;
    }

    public void setRules(List<SimilarRuleDO> rules) {
        this.rules = rules;
    }

}
