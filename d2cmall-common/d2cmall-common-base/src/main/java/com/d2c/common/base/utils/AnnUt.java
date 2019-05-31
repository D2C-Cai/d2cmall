package com.d2c.common.base.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Ann注解工具类
 *
 * @author wull
 */
public class AnnUt {

    /**
     * 获取默认值
     * <p>返回第一个非空值，为空则返回后面默认值
     * <p> 如: AnnUt.getValue(null, "", " ", "defaut") = "defaut"
     * <br>    AnnUt.getValue(null, "123", " ", "defaut") = "123"
     */
    public static String getValue(String... values) {
        String res = null;
        for (String v : values) {
            if (StringUtils.isNotBlank(v)) {
                return v;
            } else {
                res = v;
            }
        }
        return res;
    }

    /**
     * 注解值是否为默认值
     */
    public static boolean isDef(String str) {
        return StringUtils.isBlank(str);
    }

    public static boolean isNotDef(String str) {
        return StringUtils.isNotBlank(str);
    }

    public static boolean isDef(Number v) {
        if (v == null) return true;
        return v.intValue() < 0;
    }

    public static boolean isNotDef(Number v) {
        return !isDef(v);
    }

    public static boolean isDef(Class<?> clz) {
        return Void.class.equals(clz);
    }

    public static boolean isNotDef(Class<?> clz) {
        return !isDef(clz);
    }

}
