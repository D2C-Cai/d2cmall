package com.d2c.backend.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HttpProperties {

    @Value("${BOSS_API_URL}")
    private String bossApiUrl;

    public String getBossApiUrl() {
        return bossApiUrl;
    }

    public void setBossApiUrl(String bossApiUrl) {
        this.bossApiUrl = bossApiUrl;
    }

}
