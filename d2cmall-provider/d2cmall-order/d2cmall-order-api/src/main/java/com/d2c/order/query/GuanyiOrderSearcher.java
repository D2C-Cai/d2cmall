package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class GuanyiOrderSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    private Integer success;
    private Integer burgeonFall;
    private Integer expressFall;
    /**
     * 门店类型
     */
    private String shopCode;
    private String code;
    /**
     * 平台单号
     */
    private String platformCode;
    /**
     * 货号
     */
    private String itemCode;
    /**
     * 物流单号
     */
    private String expressNo;
    /**
     * 会员(相当于D2C店铺)
     */
    private String vipName;
    private String shopName;
    /**
     * 发货日期
     */
    private Date deliveryStartDate;
    private Date deliveryEndDate;
    /**
     * 伯俊做单是否处理成功
     */
    private Integer burgeonHandle;
    /**
     * 物流做单是否成功
     */
    private Integer expressHandle;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getBurgeonFall() {
        return burgeonFall;
    }

    public void setBurgeonFall(Integer burgeonFall) {
        this.burgeonFall = burgeonFall;
    }

    public Integer getExpressFall() {
        return expressFall;
    }

    public void setExpressFall(Integer expressFall) {
        this.expressFall = expressFall;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
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

    public Integer getBurgeonHandle() {
        return burgeonHandle;
    }

    public void setBurgeonHandle(Integer burgeonHandle) {
        this.burgeonHandle = burgeonHandle;
    }

    public Integer getExpressHandle() {
        return expressHandle;
    }

    public void setExpressHandle(Integer expressHandle) {
        this.expressHandle = expressHandle;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

}
