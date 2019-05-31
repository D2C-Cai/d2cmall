package com.d2c.member.support;

import java.math.BigDecimal;

public class RedPacketsInfo extends BillInfo {

    private static final long serialVersionUID = 1L;
    /**
     * 赠送金额
     */
    private BigDecimal giftAmount;
    /**
     * 发起人
     */
    private Long initiatorMemberId;

    public RedPacketsInfo() {
        super();
    }

    public RedPacketsInfo(String businessType, String payType) {
        super(businessType, payType);
    }

    public BigDecimal getGiftAmount() {
        return giftAmount;
    }

    public void setGiftAmount(BigDecimal giftAmount) {
        this.giftAmount = giftAmount;
    }

    public Long getInitiatorMemberId() {
        return initiatorMemberId;
    }

    public void setInitiatorMemberId(Long initiatorMemberId) {
        this.initiatorMemberId = initiatorMemberId;
    }

}
