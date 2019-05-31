package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.math.BigDecimal;
import java.util.Date;

public class AbnormalPaymentSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 支付账号
     */
    private String payer;
    /**
     * 订单数最小值
     */
    private Integer orderNum;
    /**
     * 支付金额最大值
     */
    private BigDecimal payAmount;
    /**
     * 支付开始时间
     */
    private Date beginDate;
    /**
     * 支付结束时间
     */
    private Date endDate;
    /**
     * 订单类型
     */
    private String orderType;

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

}
