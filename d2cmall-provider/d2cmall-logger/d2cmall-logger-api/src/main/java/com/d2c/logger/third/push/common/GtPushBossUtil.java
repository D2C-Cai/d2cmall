package com.d2c.logger.third.push.common;

import com.gexin.rp.sdk.http.IGtPush;

public class GtPushBossUtil extends IGtPush {

    public static String appId = "ksLw8GgFao9777HVpYKQd3";
    public static String appKey = "OixnM0GtKp7eVWF2kY8JP2";
    public static String masterSecret = "5jFnVOIC3c81aIOJobtqK2";
    private static IGtPush push;
    public GtPushBossUtil(String appKey, String masterSecret) {
        super(appKey, masterSecret);
    }
    // public static String appId = "v4OJemVL8D6e7KDE76vLv7";
    // public static String appKey = "ERAP5UNxtB6MzPIl5w0Xb3";
    // public static String masterSecret = "2H0zp8qEje7zTuszM01hL1";

    public synchronized static IGtPush getInstance() {
        if (push == null) {
            try {
                push = new IGtPush(appKey, masterSecret);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return push;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        GtPushBossUtil.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        GtPushBossUtil.appKey = appKey;
    }

    public String getMasterSecret() {
        return masterSecret;
    }

    public void setMasterSecret(String masterSecret) {
        GtPushBossUtil.masterSecret = masterSecret;
    }

}
