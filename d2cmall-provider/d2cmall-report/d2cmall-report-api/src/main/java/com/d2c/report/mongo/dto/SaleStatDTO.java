package com.d2c.report.mongo.dto;

import com.d2c.common.api.dto.BaseDTO;

/**
 * 买手销售数据
 *
 * @author wull
 */
public class SaleStatDTO extends BaseDTO {

    private static final long serialVersionUID = -2407942537511131669L;
    /**
     * 分销ID
     */
    private Long partnerId;
    /**
     * 销售单数
     */
    private Long count = 0L;
    /**
     * 售后中单数
     */
    private Long afterCount = 0L;
    /**
     * 实付金额
     */
    private Double payAmount = 0.0;
    /**
     * 直接收益金额
     */
    private Double partnerRebates = 0.0;
    /**
     * 团队收益金额
     */
    private Double parentRebates = 0.0;
    /**
     * 间接收益金额
     */
    private Double superRebates = 0.0;
    /**
     * AM收益金额
     */
    private Double masterRebates = 0.0;

    public SaleStatDTO() {
    }

    public SaleStatDTO(Long partnerId) {
        this.partnerId = partnerId;
    }

    public SaleStatDTO add(SaleStatDTO add) {
        count += add.getCount();
        afterCount += add.getAfterCount();
        payAmount += add.getPayAmount();
        partnerRebates += add.getPartnerRebates();
        parentRebates += add.getParentRebates();
        superRebates += add.getSuperRebates();
        masterRebates += add.getMasterRebates();
        return this;
    }
    //*********************************

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getAfterCount() {
        return afterCount;
    }

    public void setAfterCount(Long afterCount) {
        this.afterCount = afterCount;
    }

    public Double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Double payAmount) {
        this.payAmount = payAmount;
    }

    public Double getPartnerRebates() {
        return partnerRebates;
    }

    public void setPartnerRebates(Double partnerRebates) {
        this.partnerRebates = partnerRebates;
    }

    public Double getParentRebates() {
        return parentRebates;
    }

    public void setParentRebates(Double parentRebates) {
        this.parentRebates = parentRebates;
    }

    public Double getSuperRebates() {
        return superRebates;
    }

    public void setSuperRebates(Double superRebates) {
        this.superRebates = superRebates;
    }

    public Double getMasterRebates() {
        return masterRebates;
    }

    public void setMasterRebates(Double masterRebates) {
        this.masterRebates = masterRebates;
    }

}
