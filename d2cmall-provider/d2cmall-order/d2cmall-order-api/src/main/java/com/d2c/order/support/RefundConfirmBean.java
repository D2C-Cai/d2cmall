package com.d2c.order.support;

import com.d2c.common.api.query.model.BaseQuery;

import java.math.BigDecimal;
import java.util.Date;

public class RefundConfirmBean extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 退款编号
     */
    private String refundSn;
    /**
     * 退款账号
     */
    private String backAccountSn;
    /**
     * 退款人名称
     */
    private String backAccountName;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 退款时间
     */
    private Date payDate = null;
    /**
     * 退款单号
     */
    private String paySn;
    /**
     * 退款金额
     */
    private BigDecimal payMoney = new BigDecimal("0");

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getPaySn() {
        return paySn;
    }

    public void setPaySn(String paySn) {
        this.paySn = paySn;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public String getRefundSn() {
        return refundSn;
    }

    public void setRefundSn(String refundSn) {
        this.refundSn = refundSn;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public String getBackAccountSn() {
        return backAccountSn;
    }

    public void setBackAccountSn(String backAccountSn) {
        this.backAccountSn = backAccountSn;
    }

    public String getBackAccountName() {
        return backAccountName;
    }

    public void setBackAccountName(String backAccountName) {
        this.backAccountName = backAccountName;
    }

}
