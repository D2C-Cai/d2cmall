package com.d2c.report.query;

import com.d2c.common.api.query.model.BaseQuery;

public class FinanceDailyAmountSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 开始时间
     */
    private Integer year;
    /**
     * 结束时间
     */
    private Integer month;
    /**
     * 支付类型
     */
    private Integer paymentType;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

}
