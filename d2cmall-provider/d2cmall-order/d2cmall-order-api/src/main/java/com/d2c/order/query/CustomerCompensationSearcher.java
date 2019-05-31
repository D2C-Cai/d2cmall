package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.math.BigDecimal;
import java.util.Date;

public class CustomerCompensationSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 会员账号
     */
    private String loginCode;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * sku
     */
    private String productSku;
    /**
     * 赔偿类型
     */
    private Integer type;
    /**
     * 赔偿状态
     */
    private Integer status;
    /**
     * 开始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 最小金额
     */
    private BigDecimal minAmount;
    /**
     * 最大金额
     */
    private BigDecimal maxAmount;
    /**
     * 订单开始时间
     */
    private Date transactionStartTime;
    /**
     * 订单结束时间
     */
    private Date transactionEndTime;

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getProductSku() {
        return productSku;
    }

    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Date getTransactionStartTime() {
        return transactionStartTime;
    }

    public void setTransactionStartTime(Date transactionStartTime) {
        this.transactionStartTime = transactionStartTime;
    }

    public Date getTransactionEndTime() {
        return transactionEndTime;
    }

    public void setTransactionEndTime(Date transactionEndTime) {
        this.transactionEndTime = transactionEndTime;
    }

}
