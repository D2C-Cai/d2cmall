package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class ProductOrderSumSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 销售数量排序字段desc或者asc
     */
    private String countsort;
    /**
     * 销售金额排序字段desc或者asc
     */
    private String amountsort;
    /**
     * 开始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 排序类型
     */
    private String orderStr = "totalCount DESC";
    /**
     * 运营小组
     */
    private String operation;

    public String getCountsort() {
        return countsort;
    }

    public void setCountsort(String countsort) {
        this.countsort = countsort;
    }

    public String getAmountsort() {
        return amountsort;
    }

    public void setAmountsort(String amountsort) {
        this.amountsort = amountsort;
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

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

}
