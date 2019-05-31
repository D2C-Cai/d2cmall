package com.d2c.report.model;

import com.d2c.common.api.model.PreUserDO;
import com.d2c.report.enums.PaymentTypeEnum;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "rp_online_amount")
public class FinanceOnlineDailyAmount extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 统计日期
     */
    private Date calculateDate;
    /**
     * 在线支付： 预收金额
     */
    private BigDecimal preAmount = new BigDecimal(0);
    /**
     * 发货金额
     */
    private BigDecimal deliveryAmount = new BigDecimal(0);
    /**
     * 在线支付：退款金额
     */
    private BigDecimal refundAmount = new BigDecimal(0);
    /**
     * 支付类型
     */
    private Integer paymentType;
    private BigDecimal reshipAmount = new BigDecimal(0);

    public FinanceOnlineDailyAmount() {
    }

    public FinanceOnlineDailyAmount(Date calculateDate, Integer paymentType) {
        super();
        this.calculateDate = calculateDate;
        this.paymentType = paymentType;
    }

    public Date getCalculateDate() {
        return calculateDate;
    }

    public void setCalculateDate(Date calculateDate) {
        this.calculateDate = calculateDate;
    }

    public BigDecimal getDeliveryAmount() {
        return deliveryAmount;
    }

    public void setDeliveryAmount(BigDecimal deliveryAmount) {
        this.deliveryAmount = deliveryAmount;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public BigDecimal getPreAmount() {
        return preAmount;
    }

    public void setPreAmount(BigDecimal preAmount) {
        this.preAmount = preAmount;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getReshipAmount() {
        return reshipAmount;
    }

    public void setReshipAmount(BigDecimal reshipAmount) {
        this.reshipAmount = reshipAmount;
    }

    public String getPaymentTypeName() {
        return PaymentTypeEnum.getByCode(this.getPaymentType()).getDisplay();
    }

    public Integer getType() {
        return 1;
    }

}
