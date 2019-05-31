package com.d2c.order.third.payment.wxpay.core;

public class WxXcxConfig {

    /**
     * 小程序支付密钥
     */
    public static final String PAY_KEY = "hkoughiuyfjhbvcdlou875412jh879ij";
    public static final String MCH_TAIL = "301";
    public String APP_ID = "wx58eb0484ce91f38f";
    public String MCH_ID = "1286659301";
    public String APP_SECRET = "079e9b84d3174c0ba41bd47ff5ce8e89";
    public String WX_URL = "https://api.weixin.qq.com/sns/jscode2session?";
    public String NOTIFY_URL = "https://appserver.d2cmall.com/weixinNotify";

    public String getAPP_ID() {
        return APP_ID;
    }

    public void setAPP_ID(String aPP_ID) {
        APP_ID = aPP_ID;
    }

    public String getMCH_ID() {
        return MCH_ID;
    }

    public void setMCH_ID(String mCH_ID) {
        MCH_ID = mCH_ID;
    }

    public String getAPP_SECRET() {
        return APP_SECRET;
    }

    public void setAPP_SECRET(String aPP_SECRET) {
        APP_SECRET = aPP_SECRET;
    }

    public String getWX_URL() {
        return WX_URL;
    }

    public void setWX_URL(String wX_URL) {
        WX_URL = wX_URL;
    }

    public String getNOTIFY_URL() {
        return NOTIFY_URL;
    }

    public void setNOTIFY_URL(String nOTIFY_URL) {
        NOTIFY_URL = nOTIFY_URL;
    }

}
