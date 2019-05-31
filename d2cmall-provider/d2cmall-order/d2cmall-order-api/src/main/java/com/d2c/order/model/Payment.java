package com.d2c.order.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.order.enums.PaymentTypeEnum;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付单
 */
@Table(name = "o_payment")
public class Payment extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 单据编号
     */
    private String paymentSn;
    /**
     * 支付状态
     */
    private String paymentStatus = PaymentStatus.ready.name();
    /**
     * 支付类型
     */
    @AssertColumn("支付类型不能为空")
    private Integer paymentType = PaymentTypeEnum.ALIPAY.getCode();
    /**
     * 支付金额
     */
    private BigDecimal totalAmount;
    /**
     * 支付手续费
     */
    private BigDecimal paymentFee = new BigDecimal(0);
    /**
     * 支付人
     */
    private String payer;
    /**
     * 支付时间
     */
    private Date payDate;
    /**
     * 支付流水
     */
    @AssertColumn("支付流水不能为空")
    private String alipaySn;
    /**
     * 会员ID
     */
    private String memberId;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 订单类型
     */
    private String orderType;
    /**
     * 微信支付商户号
     */
    private String mchId;
    /**
     * 微信支付应用号
     */
    private String appId;

    public String getPaymentSn() {
        return paymentSn;
    }

    ;

    public void setPaymentSn(String paymentSn) {
        this.paymentSn = paymentSn;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getPaymentFee() {
        return paymentFee;
    }

    public void setPaymentFee(BigDecimal paymentFee) {
        this.paymentFee = paymentFee;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public String getAlipaySn() {
        return alipaySn;
    }

    public void setAlipaySn(String alipaySn) {
        this.alipaySn = alipaySn;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPaymentTypeName() {
        return PaymentTypeEnum.getByCode(this.getPaymentType()).getDisplay();
    }

    /**
     * 支付状态（准备、超时、作废、成功、不成功）
     */
    public enum PaymentStatus {
        ready(0), timeout(1), invalid(2), success(3), failure(4);
        private static Map<Integer, PaymentStatus> holder = new HashMap<>();

        static {
            for (PaymentStatus paymentStatus : values()) {
                holder.put(paymentStatus.getCode(), paymentStatus);
            }
        }

        private int code;

        PaymentStatus(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

}