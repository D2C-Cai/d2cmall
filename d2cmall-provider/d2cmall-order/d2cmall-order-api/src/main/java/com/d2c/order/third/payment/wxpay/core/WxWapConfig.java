package com.d2c.order.third.payment.wxpay.core;

public class WxWapConfig {

    /**
     * 扫码和JSAPI支付密钥
     */
    public static final String PAY_KEY = "afgv2556sdfbhad4jhkf4erat6sj1hsr";
    public static final String MCH_TAIL = "801";
    public String APPID = "wxdb10043a24fef1e5";
    public String MCH_ID = "1283970801";
    public String RETURN_URL = "http://m.d2cmall.com/member/order";
    public String NOTIFY_URL = "http://www.d2cmall.com/weixinNotify";

    public String getAPPID() {
        return APPID;
    }

    public void setAPPID(String aPPID) {
        APPID = aPPID;
    }

    public String getMCH_ID() {
        return MCH_ID;
    }

    public void setMCH_ID(String mCH_ID) {
        MCH_ID = mCH_ID;
    }

    public String getRETURN_URL() {
        return RETURN_URL;
    }

    public void setRETURN_URL(String rETURN_URL) {
        RETURN_URL = rETURN_URL;
    }

    public String getNOTIFY_URL() {
        return NOTIFY_URL;
    }

    public void setNOTIFY_URL(String nOTIFY_URL) {
        NOTIFY_URL = nOTIFY_URL;
    }

}
