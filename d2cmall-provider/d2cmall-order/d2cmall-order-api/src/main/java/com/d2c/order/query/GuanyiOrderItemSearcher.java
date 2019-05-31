package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class GuanyiOrderItemSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 物流是否处理
     */
    private Integer express;
    private Integer expressFall;
    /**
     * 物流做单是否成功
     */
    private Integer expressHandle;
    /**
     * 平台单号
     */
    private String platformCode;
    /**
     * 发货日期
     */
    private Date deliveryStartDate;
    private Date deliveryEndDate;
    /**
     * 物流编号
     */
    private String expressNo;
    /**
     * 管易单号
     */
    private String tradeCode;
    /**
     * sku
     */
    private String itemCode;
    private String orderCode;

    public Integer getExpress() {
        return express;
    }

    public void setExpress(Integer express) {
        this.express = express;
    }

    public Integer getExpressFall() {
        return expressFall;
    }

    public void setExpressFall(Integer expressFall) {
        this.expressFall = expressFall;
    }

    public Integer getExpressHandle() {
        return expressHandle;
    }

    public void setExpressHandle(Integer expressHandle) {
        this.expressHandle = expressHandle;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public Date getDeliveryStartDate() {
        return deliveryStartDate;
    }

    public void setDeliveryStartDate(Date deliveryStartDate) {
        this.deliveryStartDate = deliveryStartDate;
    }

    public Date getDeliveryEndDate() {
        return deliveryEndDate;
    }

    public void setDeliveryEndDate(Date deliveryEndDate) {
        this.deliveryEndDate = deliveryEndDate;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

}
