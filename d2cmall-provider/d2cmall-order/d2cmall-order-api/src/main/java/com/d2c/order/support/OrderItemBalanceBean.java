package com.d2c.order.support;

import com.d2c.common.api.query.model.BaseQuery;

import java.math.BigDecimal;
import java.util.Date;

public class OrderItemBalanceBean extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 快递单号
     */
    private String deliverySn;
    /**
     * 结算金额
     */
    private BigDecimal balanceMoney;
    /**
     * 结算理由
     */
    private String balanceReason;
    /**
     * 签收时间
     */
    private Date signDate;
    /**
     * 单据编号
     */
    private String billNumber;

    public String getDeliverySn() {
        return deliverySn;
    }

    public void setDeliverySn(String deliverySn) {
        this.deliverySn = deliverySn;
    }

    public BigDecimal getBalanceMoney() {
        return balanceMoney;
    }

    public void setBalanceMoney(BigDecimal balanceMoney) {
        this.balanceMoney = balanceMoney;
    }

    public String getBalanceReason() {
        return balanceReason;
    }

    public void setBalanceReason(String balanceReason) {
        this.balanceReason = balanceReason;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

}
