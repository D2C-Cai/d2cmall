package com.d2c.member.support;

import java.math.BigDecimal;

public class OrderInfo extends BillInfo {

    private static final long serialVersionUID = 1L;
    /**
     * 消费金额
     */
    private BigDecimal cashAmount;
    /**
     * 赠送金额
     */
    private BigDecimal giftAmount;
    /**
     * 仅限在期限内使用 1：是，0：否
     */
    private Integer limited = 0;
    /**
     * 不退的赠送金额
     */
    private BigDecimal limitGiftAmount = new BigDecimal(0);

    public OrderInfo() {
        super();
    }

    public OrderInfo(String businessType, String payType) {
        super(businessType, payType);
    }

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    public BigDecimal getGiftAmount() {
        return giftAmount;
    }

    public void setGiftAmount(BigDecimal giftAmount) {
        this.giftAmount = giftAmount;
    }

    public Integer getLimited() {
        return limited;
    }

    public void setLimited(Integer limited) {
        this.limited = limited;
    }

    public BigDecimal getLimitGiftAmount() {
        return limitGiftAmount;
    }

    public void setLimitGiftAmount(BigDecimal limitGiftAmount) {
        this.limitGiftAmount = limitGiftAmount;
    }

}
