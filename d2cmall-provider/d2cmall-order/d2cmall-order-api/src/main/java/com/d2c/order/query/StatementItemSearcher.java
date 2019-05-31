package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class StatementItemSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 结算时间
     */
    private Integer settleYear;
    private Integer settleMonth;
    /**
     * 设计师
     */
    private Long designerId;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 门店
     */
    private Long storeId;
    /**
     * 业务开始时间
     */
    private Date transactionStartTime;
    /**
     * 业务结束时间
     */
    private Date transactionEndTime;
    /**
     * 设计师货号
     */
    private String externalSn;
    /**
     * D2C货号
     */
    private String inernalSn;
    /**
     * 订单开始时间
     */
    private Date startTime;
    /**
     * 订单结束时间
     */
    private Date endTime;
    /**
     * 对账单明细id
     */
    private Long statementId;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 对账明细类型
     */
    private Integer type;
    /**
     * D2c条码
     */
    private String barCode;
    /**
     * 订单类型
     */
    private String orderType;
    /**
     * 扣减库存的类型 1：扣减POP库存 0：扣减真实库存
     */
    private Integer pop;
    /**
     * 月份日期(上旬1，中旬2，下旬3，不分0)
     */
    private Integer periodOfMonth;
    /**
     * 来源
     */
    private String fromType;

    public Integer getSettleYear() {
        return settleYear;
    }

    public void setSettleYear(Integer settleYear) {
        this.settleYear = settleYear;
    }

    public Integer getSettleMonth() {
        return settleMonth;
    }

    public void setSettleMonth(Integer settleMonth) {
        this.settleMonth = settleMonth;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
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

    public String getExternalSn() {
        return externalSn;
    }

    public void setExternalSn(String externalSn) {
        this.externalSn = externalSn;
    }

    public String getInernalSn() {
        return inernalSn;
    }

    public void setInernalSn(String inernalSn) {
        this.inernalSn = inernalSn;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getStatementId() {
        return statementId;
    }

    public void setStatementId(Long statementId) {
        this.statementId = statementId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Integer getPop() {
        return pop;
    }

    public void setPop(Integer pop) {
        this.pop = pop;
    }

    public Integer getPeriodOfMonth() {
        return periodOfMonth;
    }

    public void setPeriodOfMonth(Integer periodOfMonth) {
        this.periodOfMonth = periodOfMonth;
    }

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }

}
