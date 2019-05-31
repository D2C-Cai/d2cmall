package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;

public class OrderAnalysisSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 报表了类型，type 1是按年月终端分，2是按年月分，3是第三方支付
     */
    public Integer type;
    /**
     * 年
     */
    private Integer year;
    /**
     * 月
     */
    private Integer month;
    /**
     * 设备
     */
    private String device;
    /**
     * 是否是首次购买用户
     */
    private Boolean firstBuy;

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

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Boolean getFirstBuy() {
        return firstBuy;
    }

    public void setFirstBuy(Boolean firstBuy) {
        this.firstBuy = firstBuy;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
