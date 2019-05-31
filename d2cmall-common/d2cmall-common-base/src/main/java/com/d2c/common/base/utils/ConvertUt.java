package com.d2c.common.base.utils;

import com.d2c.common.base.exception.ConvertException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.BooleanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 对象转换工具类
 *
 * @author wull
 */
public class ConvertUt {

    /**
     * 转换并复制对象
     */
    public static <T> T convertBean(Object bean, Class<T> tgClz) {
        return BeanUt.buildBean(bean, tgClz);
    }

    public static <T> List<T> convertList(List<?> list, Class<T> tgClz) {
        List<T> res = new ArrayList<>(list.size());
        if (ListUt.notEmpty(list)) {
            list.forEach(bean -> {
                res.add(convertBean(bean, tgClz));
            });
        }
        return res;
    }

    /**
     * 常用类型转换器(String, Integer, Long, Double ...)
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertType(final Object value, final Class<T> targetType) {
        return (T) ConvertUtils.convert(value, targetType);
    }

    public static <T> List<T> convertTypeList(List<?> list, final Class<T> targetType) {
        List<T> res = new ArrayList<>(list.size());
        list.forEach(bean -> {
            res.add(convertType(bean, targetType));
        });
        return res;
    }

    public static Boolean toBoolean(Object value) {
        return convertType(value, Boolean.class);
    }

    public static Long toLong(Object value) {
        return convertType(value, Long.class);
    }
    //************************************************

    public static int toIntDef(Object o) {
        return toIntDef(o, 0);
    }

    public static int toIntDef(Object o, final int def) {
        try {
            return toInt(o);
        } catch (Exception e) {
            return def;
        }
    }

    public static int toInt(Object o) {
        if (o == null)
            throw new NullPointerException("不能为空！");
        if (o instanceof Number)
            return ((Number) o).intValue();
        if (o instanceof Boolean)
            return ((Boolean) o) ? 1 : 0;
        if (o instanceof String)
            return Integer.parseInt((String) o);
        throw new ConvertException("类型转换异常: " + o.getClass().getName() + " to int");
    }

    public static Boolean toBoolean(Integer value) {
        return BooleanUtils.toBooleanObject(value);
    }

    public static String toStr(String value, String defaut) {
        return value == null ? defaut : value;
    }

    public static String toStr(String value) {
        return toStr(value, "");
    }

}
