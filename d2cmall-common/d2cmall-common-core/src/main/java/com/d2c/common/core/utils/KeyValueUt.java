package com.d2c.common.core.utils;

import com.d2c.common.core.model.KeyValue;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class KeyValueUt {

    public static Map<Object, Object> toMap(final List<KeyValue> list) {
        final Map<Object, Object> map = new LinkedHashMap<>();
        if (list == null) return map;
        for (KeyValue bean : list) {
            map.put(bean.getKey(), bean.getValue());
        }
        return map;
    }

}
