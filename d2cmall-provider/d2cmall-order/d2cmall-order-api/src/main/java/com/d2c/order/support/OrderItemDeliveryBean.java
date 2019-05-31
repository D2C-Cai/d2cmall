package com.d2c.order.support;

import com.d2c.common.api.query.model.BaseQuery;

public class OrderItemDeliveryBean extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * SKU条码
     */
    private String barCode;
    /**
     * 快递名称
     */
    private String deliveryCorpName;
    /**
     * 运单编号
     */
    private String deliverySn;
    /**
     * 发货数量
     */
    private Integer deliveryQuantity;

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

    public String getDeliveryCorpName() {
        return deliveryCorpName;
    }

    public void setDeliveryCorpName(String deliveryCorpName) {
        this.deliveryCorpName = deliveryCorpName;
    }

    public String getDeliverySn() {
        return deliverySn;
    }

    public void setDeliverySn(String deliverySn) {
        this.deliverySn = deliverySn;
    }

    public Integer getDeliveryQuantity() {
        return deliveryQuantity;
    }

    public void setDeliveryQuantity(Integer deliveryQuantity) {
        this.deliveryQuantity = deliveryQuantity;
    }

}
