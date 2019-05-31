package com.d2c.similar.entity;

import com.d2c.common.api.model.BaseDO;
import com.d2c.similar.constant.Constant;

import javax.persistence.Table;

/**
 * 离散型多维度数据模板
 *
 * @author wull
 */
@Table(name = Constant.PLAT_PREFIX + "dimen_tpl")
public class DimenTplDO extends BaseDO {

    private static final long serialVersionUID = 7902814112969375973L;
    private String ruleCode;
    private String dimenName;
    private String lowName;
    private String highName;
    private Double defValue;
    private Double lowValue;
    private Double highValue;
    private String remark;

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getDimenName() {
        return dimenName;
    }

    public void setDimenName(String dimenName) {
        this.dimenName = dimenName;
    }

    public String getLowName() {
        return lowName;
    }

    public void setLowName(String lowName) {
        this.lowName = lowName;
    }

    public String getHighName() {
        return highName;
    }

    public void setHighName(String highName) {
        this.highName = highName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getDefValue() {
        return defValue;
    }

    public void setDefValue(Double defValue) {
        this.defValue = defValue;
    }

    public Double getLowValue() {
        return lowValue;
    }

    public void setLowValue(Double lowValue) {
        this.lowValue = lowValue;
    }

    public Double getHighValue() {
        return highValue;
    }

    public void setHighValue(Double highValue) {
        this.highValue = highValue;
    }

}
