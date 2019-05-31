package com.d2c.order.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 异常支付单
 *
 * @author Administrator
 */
public class AbnormalPaymentDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 支付账号
     */
    private String payer;
    /**
     * 会员id
     */
    private Long memberId;
    /**
     * 总异常单数
     */
    private Integer totalCount;
    /**
     * 总支付金额
     */
    private BigDecimal totalAmount;

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
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

}
