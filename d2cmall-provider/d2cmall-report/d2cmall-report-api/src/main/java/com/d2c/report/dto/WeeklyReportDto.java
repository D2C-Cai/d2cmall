package com.d2c.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class WeeklyReportDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 老用户在此期间再次购买人数
     */
    private Integer oldMemberCount;
    /**
     * 老用户在此期间再次购买金额
     */
    private BigDecimal oldMemberAmount;
    /**
     * 新用户在此期间首次购买人数
     */
    private Integer newMemberCount;
    /**
     * 新用户在此期间首次购买金额
     */
    private BigDecimal newMemberAmount;
    /**
     * 老用户在此期间首次购买人数
     */
    private Integer oldMemberFirstCount;
    /**
     * 老用户在此期间首次购买金额
     */
    private BigDecimal oldMemberFirstAmount;
    /**
     * 总购买人数
     */
    private Integer totalCount;
    /**
     * 总购买金额
     */
    private BigDecimal totalAmount;
    /**
     * 会员注册人数
     */
    private Integer totalRegister;
    /**
     * 游客新增人数
     */
    private Integer totalTourist;
    /**
     * 分销订单中分销自购的金额
     */
    private BigDecimal partnerBySelfAmount;
    /**
     * 分销订单中购买者是分销且他购的金额
     */
    private BigDecimal partnerForOtherAmount;
    /**
     * 分销订单下单者是普通用户的金额
     */
    private BigDecimal ordinaryAmount;
    /**
     * 新的分销者中下的分销订单的人数
     */
    private Integer newPartnerCount;
    /**
     * 新的分销者中下普通单的人数
     */
    private Integer newOrdinaryCount;
    /**
     * 老的分销者中下分销单的人数
     */
    private Integer oldPartnerCount;
    /**
     * 新的分销者中下普通单的人数
     */
    private Integer oldOrdinaryCount;

    public Integer getOldMemberCount() {
        return oldMemberCount;
    }

    public void setOldMemberCount(Integer oldMemberCount) {
        this.oldMemberCount = oldMemberCount;
    }

    public BigDecimal getOldMemberAmount() {
        return oldMemberAmount;
    }

    public void setOldMemberAmount(BigDecimal oldMemberAmount) {
        this.oldMemberAmount = oldMemberAmount;
    }

    public Integer getNewMemberCount() {
        return newMemberCount;
    }

    public void setNewMemberCount(Integer newMemberCount) {
        this.newMemberCount = newMemberCount;
    }

    public BigDecimal getNewMemberAmount() {
        return newMemberAmount;
    }

    public void setNewMemberAmount(BigDecimal newMemberAmount) {
        this.newMemberAmount = newMemberAmount;
    }

    public Integer getOldMemberFirstCount() {
        return oldMemberFirstCount;
    }

    public void setOldMemberFirstCount(Integer oldMemberFirstCount) {
        this.oldMemberFirstCount = oldMemberFirstCount;
    }

    public BigDecimal getOldMemberFirstAmount() {
        return oldMemberFirstAmount;
    }

    public void setOldMemberFirstAmount(BigDecimal oldMemberFirstAmount) {
        this.oldMemberFirstAmount = oldMemberFirstAmount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getTotalRegister() {
        return totalRegister;
    }

    public void setTotalRegister(Integer totalRegister) {
        this.totalRegister = totalRegister;
    }

    public Integer getTotalTourist() {
        return totalTourist;
    }

    public void setTotalTourist(Integer totalTourist) {
        this.totalTourist = totalTourist;
    }

    public BigDecimal getPartnerBySelfAmount() {
        return partnerBySelfAmount;
    }

    public void setPartnerBySelfAmount(BigDecimal partnerBySelfAmount) {
        this.partnerBySelfAmount = partnerBySelfAmount;
    }

    public BigDecimal getPartnerForOtherAmount() {
        return partnerForOtherAmount;
    }

    public void setPartnerForOtherAmount(BigDecimal partnerForOtherAmount) {
        this.partnerForOtherAmount = partnerForOtherAmount;
    }

    public BigDecimal getOrdinaryAmount() {
        return ordinaryAmount;
    }

    public void setOrdinaryAmount(BigDecimal ordinaryAmount) {
        this.ordinaryAmount = ordinaryAmount;
    }

    public Integer getNewPartnerCount() {
        return newPartnerCount;
    }

    public void setNewPartnerCount(Integer newPartnerCount) {
        this.newPartnerCount = newPartnerCount;
    }

    public Integer getNewOrdinaryCount() {
        return newOrdinaryCount;
    }

    public void setNewOrdinaryCount(Integer newOrdinaryCount) {
        this.newOrdinaryCount = newOrdinaryCount;
    }

    public Integer getOldPartnerCount() {
        return oldPartnerCount;
    }

    public void setOldPartnerCount(Integer oldPartnerCount) {
        this.oldPartnerCount = oldPartnerCount;
    }

    public Integer getOldOrdinaryCount() {
        return oldOrdinaryCount;
    }

    public void setOldOrdinaryCount(Integer oldOrdinaryCount) {
        this.oldOrdinaryCount = oldOrdinaryCount;
    }

}
