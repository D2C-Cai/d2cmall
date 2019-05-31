package com.d2c.order.third.kaola.reponse;

import com.alibaba.fastjson.JSONArray;

public class KaolaOrderItemStatus {

    /**
     * 状态描述
     */
    private String desc;
    /**
     * 状态，以这个为准 <br>
     * 0,订单同步失败 1,订单同步成功（等待支付） 2,订单支付成功（等待发货） 3,订单支付失败 4,订单已发货 <br>
     * 5,交易成功 6,订单交易失败（用户支付后不能发货）【最终状态】 7,订单关闭 8,退款成功(分销走线下不做更新) <br>
     * 9,退款失败"(分销走线下不做更新)
     */
    private String status;
    /**
     * 拦截原因
     */
    private String limitReason;
    /**
     * 是否海关拦截
     */
    private String isLimit;
    /**
     * 商品SKU（buyCnt=购买数量，skuId=sku）
     */
    private JSONArray skuList;
    /**
     * 物流公司
     */
    private String deliverName;
    /**
     * 发货单号
     */
    private String deliverNo;
    /**
     * 订单ID
     */
    private String orderId;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLimitReason() {
        return limitReason;
    }

    public void setLimitReason(String limitReason) {
        this.limitReason = limitReason;
    }

    public String getIsLimit() {
        return isLimit;
    }

    public void setIsLimit(String isLimit) {
        this.isLimit = isLimit;
    }

    public JSONArray getSkuList() {
        return skuList;
    }

    public void setSkuList(JSONArray skuList) {
        this.skuList = skuList;
    }

    public String getDeliverName() {
        return deliverName;
    }

    public void setDeliverName(String deliverName) {
        this.deliverName = deliverName;
    }

    public String getDeliverNo() {
        return deliverNo;
    }

    public void setDeliverNo(String deliverNo) {
        this.deliverNo = deliverNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}
