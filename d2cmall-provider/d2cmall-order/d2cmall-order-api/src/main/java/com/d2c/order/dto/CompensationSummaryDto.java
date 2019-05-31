package com.d2c.order.dto;

import com.d2c.common.api.model.PreUserDO;

import java.math.BigDecimal;

public class CompensationSummaryDto extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 品牌
     */
    private String brandName;
    /**
     * 设计师名称
     */
    private String designerName;
    /**
     * 总赔偿单数
     */
    private Integer totalQuantity = 0;
    /**
     * 已赔偿单数
     */
    private Integer payQuantity = 0;
    /**
     * 总赔偿金额
     */
    private BigDecimal totalAmount = new BigDecimal(0);
    /**
     * 已支付赔偿金额
     */
    private BigDecimal payAmount = new BigDecimal(0);
    /**
     * 设计师ID
     */
    private Long designerId;
    /**
     * 运营小组
     */
    private String operation;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getDesignerName() {
        return designerName;
    }

    public void setDesignerName(String designerName) {
        this.designerName = designerName;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Integer getPayQuantity() {
        return payQuantity;
    }

    public void setPayQuantity(Integer payQuantity) {
        this.payQuantity = payQuantity;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

}
