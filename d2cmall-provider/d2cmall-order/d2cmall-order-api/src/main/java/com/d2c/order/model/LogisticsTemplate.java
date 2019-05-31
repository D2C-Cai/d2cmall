package com.d2c.order.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 物流模板
 */
@Table(name = "o_logistics_template")
public class LogisticsTemplate extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 模板名称
     */
    @AssertColumn("模板名称不能为空")
    private String templateName;
    /**
     * 物流公司名
     */
    @AssertColumn("物流公司名不能为空")
    private String deliveryName;
    /**
     * 物流编号
     */
    private String deliveryCode;
    /**
     * 包邮设置 json
     */
    private String freeCondition;
    /**
     * 默认重量
     */
    @AssertColumn("默认重量不能为空")
    private BigDecimal defalutWeight;
    /**
     * 默认金额
     */
    @AssertColumn("默认金额不能为空")
    private BigDecimal defalutAmount;
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
     * 状态，1启动，0停用
     */
    private Integer status = 1;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public String getFreeCondition() {
        return freeCondition;
    }

    public void setFreeCondition(String freeCondition) {
        this.freeCondition = freeCondition;
    }

    public BigDecimal getDefalutWeight() {
        return defalutWeight;
    }

    public void setDefalutWeight(BigDecimal defalutWeight) {
        this.defalutWeight = defalutWeight;
    }

    public BigDecimal getExcessWeight() {
        return excessWeight;
    }

    public void setExcessWeight(BigDecimal excessWeight) {
        this.excessWeight = excessWeight;
    }

    public BigDecimal getDefalutAmount() {
        return defalutAmount;
    }

    public void setDefalutAmount(BigDecimal defalutAmount) {
        this.defalutAmount = defalutAmount;
    }

    public BigDecimal getExcessAmount() {
        return excessAmount;
    }

    public void setExcessAmount(BigDecimal excessAmount) {
        this.excessAmount = excessAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDeliveryCode() {
        return deliveryCode;
    }

    public void setDeliveryCode(String deliveryCode) {
        this.deliveryCode = deliveryCode;
    }

}
