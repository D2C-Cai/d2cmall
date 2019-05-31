package com.d2c.order.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OrderProperties {

    @Value("${BURGEON_API_URL}")
    private String burgeonApiUrl;
    @Value("${PC_URL}")
    private String pcUrl;
    @Value("${GUANYI_ERP_API_URL}")
    private String guanyiApiUrl;
    @Value("${GUANYI_ERP_APPKEY}")
    private String guanyiAPPKEY;
    @Value("${GUANYI_ERP_SECRET}")
    private String guanyiSecret;
    @Value("${GUANYI_ERP_SESSIONKEY}")
    private String guanyiSessionKey;
    @Value("${KAOLA_API_URL}")
    private String kaolaApiUrl;
    @Value("${KAOLA_APP_KEY}")
    private String KaolaAppKey;
    @Value("${KAOLA_SECRET_KEY}")
    private String KaolaSecretKey;
    @Value("${KAOLA_CHANNEL_ID}")
    private String KaolaChannelId;

    public String getBurgeonApiUrl() {
        return burgeonApiUrl;
    }

    public void setBurgeonApiUrl(String burgeonApiUrl) {
        this.burgeonApiUrl = burgeonApiUrl;
    }

    public String getPcUrl() {
        return pcUrl;
    }

    public void setPcUrl(String pcUrl) {
        this.pcUrl = pcUrl;
    }

    public String getGuanyiApiUrl() {
        return guanyiApiUrl;
    }

    public void setGuanyiApiUrl(String guanyiApiUrl) {
        this.guanyiApiUrl = guanyiApiUrl;
    }

    public String getGuanyiAPPKEY() {
        return guanyiAPPKEY;
    }

    public void setGuanyiAPPKEY(String guanyiAPPKEY) {
        this.guanyiAPPKEY = guanyiAPPKEY;
    }

    public String getGuanyiSecret() {
        return guanyiSecret;
    }

    public void setGuanyiSecret(String guanyiSecret) {
        this.guanyiSecret = guanyiSecret;
    }

    public String getGuanyiSessionKey() {
        return guanyiSessionKey;
    }

    public void setGuanyiSessionKey(String guanyiSessionKey) {
        this.guanyiSessionKey = guanyiSessionKey;
    }

    public String getKaolaAppKey() {
        return KaolaAppKey;
    }

    public void setKaolaAppKey(String kaolaAppKey) {
        KaolaAppKey = kaolaAppKey;
    }

    public String getKaolaSecretKey() {
        return KaolaSecretKey;
    }

    public void setKaolaSecretKey(String kaolaSecretKey) {
        KaolaSecretKey = kaolaSecretKey;
    }

    public String getKaolaChannelId() {
        return KaolaChannelId;
    }

    public void setKaolaChannelId(String kaolaChannelId) {
        KaolaChannelId = kaolaChannelId;
    }

    public String getKaolaApiUrl() {
        return kaolaApiUrl;
    }

    public void setKaolaApiUrl(String kaolaApiUrl) {
        this.kaolaApiUrl = kaolaApiUrl;
    }

}
