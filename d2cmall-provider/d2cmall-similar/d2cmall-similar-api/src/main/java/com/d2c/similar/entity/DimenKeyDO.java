package com.d2c.similar.entity;

import com.d2c.common.api.model.BaseDO;
import com.d2c.similar.constant.Constant;

import javax.persistence.Table;

/**
 * 离散型 key-value数据对应距离
 *
 * @author wull
 */
@Table(name = Constant.PLAT_PREFIX + "dimen_key")
public class DimenKeyDO extends BaseDO {

    private static final long serialVersionUID = 7902814112969375973L;
    private Integer ruleId;
    private String fieldName;
    private String fieldValue;
    private Double dist;

    public DimenKeyDO() {
    }

    public DimenKeyDO(Integer ruleId, String fieldName, String fieldValue) {
        this.ruleId = ruleId;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public Double getDist() {
        return dist;
    }

    public void setDist(Double dist) {
        this.dist = dist;
    }

}
