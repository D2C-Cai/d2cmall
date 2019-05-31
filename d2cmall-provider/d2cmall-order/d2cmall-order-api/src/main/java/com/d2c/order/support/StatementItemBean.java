package com.d2c.order.support;

import com.d2c.common.api.query.model.BaseQuery;

import java.math.BigDecimal;

/**
 * 结算明细的结算金额
 *
 * @author Administrator
 */
public class StatementItemBean extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 商品条码
     */
    private String barCode;
    /**
     * 结算金额
     */
    private BigDecimal settlePrice;
    /**
     * 年份
     */
    private Integer year;
    /**
     * 月份
     */
    private Integer month;
    /**
     * 日
     */
    private Integer day;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public BigDecimal getSettlePrice() {
        return settlePrice;
    }

    public void setSettlePrice(BigDecimal settlePrice) {
        this.settlePrice = settlePrice;
    }

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

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

}
