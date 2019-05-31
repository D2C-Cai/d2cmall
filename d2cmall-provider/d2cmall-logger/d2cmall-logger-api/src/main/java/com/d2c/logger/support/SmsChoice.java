package com.d2c.logger.support;

import java.util.concurrent.ConcurrentHashMap;

public class SmsChoice {

    public static ConcurrentHashMap<Region, String> setting = new ConcurrentHashMap<>();

    static {
        setting.put(Region.Domestic, "Emay");
        setting.put(Region.Global, "Emay");
    }

    public enum Region {
        Domestic, Global
    }
}
