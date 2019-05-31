package com.d2c.order.third.payment.wxpay.client.card;

import org.json.JSONException;

public class WxCardCash extends WxCard {

    public WxCardCash() throws JSONException {
        init("CASH");
    }

    public void setReductCost(int cost) throws JSONException {
        m_data.put("reduce_cost", cost);
    }

    public void setLeastCost(int cost) throws JSONException {
        m_data.put("least_cost", cost);
    }

}
