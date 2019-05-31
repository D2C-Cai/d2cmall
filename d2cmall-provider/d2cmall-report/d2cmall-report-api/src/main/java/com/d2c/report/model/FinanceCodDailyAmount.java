package com.d2c.report.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "rp_cod_amount")
public class FinanceCodDailyAmount extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 统计日期
     */
    private Date calculateDate;
    /**
     * 订单金额(用户下订单的金额)
     */
    private BigDecimal readyAmount = new BigDecimal(0);
    /**
     * 发货金额
     */
    private BigDecimal deliveryAmount = new BigDecimal(0);
    /**
     * 退款金额
     */
    private BigDecimal refundAmount = new BigDecimal(0);
    /**
     * 结算金额
     */
    private BigDecimal balanceAmount = new BigDecimal(0);
    /**
     * 拒收金额
     */
    private BigDecimal refuseAmount = new BigDecimal(0);

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

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Integer getPaymentType() {
        return 3;
    }

    public BigDecimal getRefuseAmount() {
        return refuseAmount;
    }

    public void setRefuseAmount(BigDecimal refuseAmount) {
        this.refuseAmount = refuseAmount;
    }

}
