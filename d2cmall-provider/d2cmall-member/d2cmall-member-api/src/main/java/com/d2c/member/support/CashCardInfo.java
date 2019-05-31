package com.d2c.member.support;

import java.math.BigDecimal;

public class CashCardInfo extends BillInfo {

    private static final long serialVersionUID = 1L;
    /**
     * 充值卡金额
     */
    private BigDecimal cardAmount;

    public CashCardInfo() {
        super();
    }

    public CashCardInfo(String businessType, String payType) {
        super(businessType, payType);
    }

    public BigDecimal getCardAmount() {
        return cardAmount;
    }

    public void setCardAmount(BigDecimal cardAmount) {
        this.cardAmount = cardAmount;
    }

}
