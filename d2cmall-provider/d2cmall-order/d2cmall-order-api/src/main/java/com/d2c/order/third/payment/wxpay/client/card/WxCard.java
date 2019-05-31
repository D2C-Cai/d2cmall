package com.d2c.order.third.payment.wxpay.client.card;

import org.json.JSONException;

public abstract class WxCard {

    /**
     * 创建卡券URL
     */
    public static final String cardUrl = "https://api.weixin.qq.com/card/create?access_token=";
    /**
     * 获取卡券信息URL
     */
    public static final String checkUrl = "https://api.weixin.qq.com/card/get?access_token=";
    /**
     * 解密code URL
     */
    public static final String decryptUrl = "https://api.weixin.qq.com/card/code/decrypt?access_token=";
    /**
     * 卡券核销URL
     */
    public static final String consumeUrl = "https://api.weixin.qq.com/card/code/consume?access_token=";
    protected WxCardBaseInfo m_baseInfo;
    protected org.json.JSONObject m_requestData;
    protected org.json.JSONObject m_data;
    protected String m_cardType;

    public WxCard() throws JSONException {
        m_baseInfo = new WxCardBaseInfo();
        m_requestData = new org.json.JSONObject();
    }

    void init(String cardType) throws JSONException {
        m_cardType = cardType;
        org.json.JSONObject obj = new org.json.JSONObject();
        obj.put("card_type", m_cardType.toUpperCase());
        m_data = new org.json.JSONObject();
        m_data.put("base_info", m_baseInfo.m_data);
        obj.put(m_cardType.toLowerCase(), m_data);
        m_requestData.put("card", obj);
    }

    public org.json.JSONObject getJSONObject() {
        return m_requestData;
    }

    public String toJsonString() {
        return m_requestData.toString();
    }

    public String toString() {
        return toJsonString();
    }

    public WxCardBaseInfo getBaseInfo() {
        return m_baseInfo;
    }

}
