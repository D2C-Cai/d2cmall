package com.d2c.order.support;

import com.d2c.common.api.query.model.BaseQuery;

public class ExchangeDeliveryBean extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 订单编号
     */
    private String exchangeSn;
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

    public String getExchangeSn() {
        return exchangeSn;
    }

    public void setExchangeSn(String exchangeSn) {
        this.exchangeSn = exchangeSn;
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

}
