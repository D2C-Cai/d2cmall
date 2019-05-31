package com.d2c.flame.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HttpProperties {

    public static final String USERAGENT = "FROMAPP";
    @Value("${MOBILE_URL}")
    private String mobileUrl;

    public String getMobileUrl() {
        return mobileUrl;
    }

    public void setMobileUrl(String mobileUrl) {
        this.mobileUrl = mobileUrl;
    }

}
