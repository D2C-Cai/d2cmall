package com.d2c.order.support;

import com.d2c.common.api.query.model.BaseQuery;

import java.math.BigDecimal;
import java.util.Date;

public class StatementPayBean extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 对账单编号
     */
    private String sn;
    /**
     * 支付时间
     */
    private Date payDate;
    /**
     * 付款人
     */
    private String payer;
    /**
     * 支付流水号
     */
    private String paySn;
    /**
     * 收款银行
     */
    private String payBank;
    /**
     * 支付金额
     */
    private BigDecimal payMoney;
    /**
     * 备注
     */
    private String adminMemo;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPaySn() {
        return paySn;
    }

    public void setPaySn(String paySn) {
        this.paySn = paySn;
    }

    public String getPayBank() {
        return payBank;
    }

    public void setPayBank(String payBank) {
        this.payBank = payBank;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public String getAdminMemo() {
        return adminMemo;
    }

    public void setAdminMemo(String adminMemo) {
        this.adminMemo = adminMemo;
    }

}
