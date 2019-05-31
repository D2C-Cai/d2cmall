package com.d2c.similar.dto;

import com.d2c.common.api.dto.EntityDTO;
import com.d2c.similar.entity.DimenKeyDO;
import com.d2c.similar.entity.DimenTplDO;

import java.util.List;

public class SimilarRuleDTO extends EntityDTO {

    private static final long serialVersionUID = -4903284310787030896L;
    private Integer schemeId;
    private String ruleCode;
    private String ruleName;
    private Boolean isDimen;
    private Double deviation; //偏差
    private Double weight;
    private String remark;
    private List<DimenKeyDO> dimenKeys;
    private List<DimenTplDO> dimenTpls;

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

    public Boolean getIsDimen() {
        return isDimen;
    }

    public void setIsDimen(Boolean isDimen) {
        this.isDimen = isDimen;
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

    public List<DimenKeyDO> getDimenKeys() {
        return dimenKeys;
    }

    public void setDimenKeys(List<DimenKeyDO> dimenKeys) {
        this.dimenKeys = dimenKeys;
    }

    public List<DimenTplDO> getDimenTpls() {
        return dimenTpls;
    }

    public void setDimenTpls(List<DimenTplDO> dimenTpls) {
        this.dimenTpls = dimenTpls;
    }

}
