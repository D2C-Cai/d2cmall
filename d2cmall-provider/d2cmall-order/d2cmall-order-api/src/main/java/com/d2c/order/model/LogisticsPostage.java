package com.d2c.order.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 物流邮费
 */
@Table(name = "o_logistics_postage")
public class LogisticsPostage extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 指定区域名称
     */
    @AssertColumn("指定区域名称不能为空")
    private String name;
    /**
     * 指定区域编号
     */
    @AssertColumn("指定区域编号不能为空")
    private String code;
    /**
     * 首重
     */
    @AssertColumn("首重不能为空")
    private BigDecimal basicWeight;
    /**
     * 首费
     */
    @AssertColumn("首费不能为空")
    private BigDecimal basicAmount;
    /**
     * 超额部分金额
     */
    @AssertColumn("超额部分金额不能为空")
    private BigDecimal excessAmount;
    /**
     * 超额部分重量
     */
    @AssertColumn("超额部分重量不能为空")
    private BigDecimal excessWeight;
    /**
     * 关联模板id
     */
    private Long templateId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getBasicWeight() {
        return basicWeight;
    }

    public void setBasicWeight(BigDecimal basicWeight) {
        this.basicWeight = basicWeight;
    }

    public BigDecimal getBasicAmount() {
        return basicAmount;
    }

    public void setBasicAmount(BigDecimal basicAmount) {
        this.basicAmount = basicAmount;
    }

    public BigDecimal getExcessAmount() {
        return excessAmount;
    }

    public void setExcessAmount(BigDecimal excessAmount) {
        this.excessAmount = excessAmount;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public BigDecimal getExcessWeight() {
        return excessWeight;
    }

    public void setExcessWeight(BigDecimal excessWeight) {
        this.excessWeight = excessWeight;
    }

}
