package com.d2c.common.base.helper;

import com.d2c.common.base.helper.impl.DebugImpl;

import java.util.HashMap;
import java.util.Map;

public class DebugHelper {

    private static Map<String, DebugImpl> map = new HashMap<>();

    public static void init(Object serv, String key) {
        DebugImpl dg = map.get(key);
        if (dg == null) {
            dg = new DebugImpl(serv, key);
            map.put(key, dg);
        }
    }

    public static void log(String key) {
        DebugImpl dg = map.get(key);
        if (dg == null) {
            dg = new DebugImpl(key);
            map.put(key, dg);
        }
        dg.log();
    }

}
