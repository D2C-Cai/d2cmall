package com.d2c.order.third.payment.wxpay.refund;

import com.d2c.order.third.payment.wxpay.core.WxAppConfig;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class RefundConfig301 extends WXPayConfig {

    private String appID;
    private String refund_notify_url;

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.home"));
    }

    public String getRefund_notify_url() {
        return refund_notify_url;
    }

    public void setRefund_notify_url(String refund_notify_url) {
        this.refund_notify_url = refund_notify_url;
    }

    @Override
    String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    @Override
    String getMchID() {
        return "1286659301";
    }

    @Override
    String getKey() {
        return WxAppConfig.PAY_KEY;
    }

    @Override
    InputStream getCertStream() {
        InputStream in = null;
        try {
            in = new FileInputStream(System.getProperty("user.home") + "/conf/apiclient_cert301.p12");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return in;
    }

    @Override
    IWXPayDomain getWXPayDomain() {
        return new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {
            }

            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
    }

}
