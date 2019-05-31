package com.d2c.order.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 拼团红包组
 */
@Table(name = "o_share_red_group")
public class ShareRedPacketsGroup extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 发起人会员Id
     */
    private Long initiatorMemberId;
    /**
     * 最大人数
     */
    private Integer maxNumber;
    /**
     * 当前人数
     */
    private Integer currentNumber;
    /**
     * 状态 -1已过期 0未瓜分 1是已瓜分
     */
    private Integer status;
    /**
     * 可瓜分金额
     */
    private BigDecimal toltalMoney;
    /**
     * 活动ID
     */
    private Long sharePromotionId;
    /**
     * 活动名称
     */
    private String sharePromotionName;
    /**
     * 最大金额
     */
    private BigDecimal maxMoney;
    /**
     * 最小金额
     */
    private BigDecimal minMoney;

    public ShareRedPacketsGroup() {
    }

    public ShareRedPacketsGroup(ShareRedPacketsPromotion shareRedPacketsPromotion, Long memberId) {
        this.currentNumber = 1;
        this.status = 0;
        this.initiatorMemberId = memberId;
        this.maxNumber = shareRedPacketsPromotion.getMaxNumber().intValue();
        this.toltalMoney = shareRedPacketsPromotion.getTotalMoney();
        this.maxMoney = shareRedPacketsPromotion.getMaxMoney();
        this.minMoney = shareRedPacketsPromotion.getMinMoney();
        this.sharePromotionId = shareRedPacketsPromotion.getId();
        this.sharePromotionName = shareRedPacketsPromotion.getName();
    }

    public Long getInitiatorMemberId() {
        return initiatorMemberId;
    }

    public void setInitiatorMemberId(Long initiatorMemberId) {
        this.initiatorMemberId = initiatorMemberId;
    }

    public Integer getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(Integer maxNumber) {
        this.maxNumber = maxNumber;
    }

    public Integer getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(Integer currentNumber) {
        this.currentNumber = currentNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getToltalMoney() {
        return toltalMoney;
    }

    public void setToltalMoney(BigDecimal toltalMoney) {
        this.toltalMoney = toltalMoney;
    }

    public Long getSharePromotionId() {
        return sharePromotionId;
    }

    public void setSharePromotionId(Long sharePromotionId) {
        this.sharePromotionId = sharePromotionId;
    }

    public String getSharePromotionName() {
        return sharePromotionName;
    }

    public void setSharePromotionName(String sharePromotionName) {
        this.sharePromotionName = sharePromotionName;
    }

    public BigDecimal getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(BigDecimal maxMoney) {
        this.maxMoney = maxMoney;
    }

    public BigDecimal getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(BigDecimal minMoney) {
        this.minMoney = minMoney;
    }

}
