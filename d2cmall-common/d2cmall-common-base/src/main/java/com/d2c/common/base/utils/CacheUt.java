package com.d2c.common.base.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 数值转换工具类
 *
 * @author wull
 */
public class CacheUt {

    /**
     * 设置cache的key值
     */
    public static String getKey(Object... keys) {
        String key = "";
        for (Object o : keys) {
            if (o == null) continue;
            if (StringUtils.isBlank(key)) {
                key = o.toString();
            } else {
                key = key + ":" + o.toString();
            }
        }
        return key;
    }

}
