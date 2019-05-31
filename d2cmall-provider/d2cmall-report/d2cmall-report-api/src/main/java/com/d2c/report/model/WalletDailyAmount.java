package com.d2c.report.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "rp_wallet_day_amount")
public class WalletDailyAmount extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 统计日期
     */
    private Date calculateDate;
    /**
     * 下单本金
     */
    private BigDecimal readyAmount;
    /**
     * 下单红包
     */
    private BigDecimal readyGiftAmount;
    /**
     * 发货金额
     */
    private BigDecimal deliveryAmount;
    /**
     * 仅退款本金
     */
    private BigDecimal refundAmount;
    /**
     * 仅退款红包
     */
    private BigDecimal refundGiftAmount;
    /**
     * 退款退货本金
     */
    private BigDecimal reshipAmount;
    /**
     * 退款退货红包
     */
    private BigDecimal reshipGiftAmount;

    public Date getCalculateDate() {
        return calculateDate;
    }

    public void setCalculateDate(Date calculateDate) {
        this.calculateDate = calculateDate;
    }

    public BigDecimal getReadyAmount() {
        return readyAmount;
    }

    public void setReadyAmount(BigDecimal readyAmount) {
        this.readyAmount = readyAmount;
    }

    public BigDecimal getReadyGiftAmount() {
        return readyGiftAmount;
    }

    public void setReadyGiftAmount(BigDecimal readyGiftAmount) {
        this.readyGiftAmount = readyGiftAmount;
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

    public BigDecimal getRefundGiftAmount() {
        return refundGiftAmount;
    }

    public void setRefundGiftAmount(BigDecimal refundGiftAmount) {
        this.refundGiftAmount = refundGiftAmount;
    }

    public BigDecimal getReshipAmount() {
        return reshipAmount;
    }

    public void setReshipAmount(BigDecimal reshipAmount) {
        this.reshipAmount = reshipAmount;
    }

    public BigDecimal getReshipGiftAmount() {
        return reshipGiftAmount;
    }

    public void setReshipGiftAmount(BigDecimal reshipGiftAmount) {
        this.reshipGiftAmount = reshipGiftAmount;
    }

    public Integer getPaymentType() {
        return 7;
    }

}
