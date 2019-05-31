package com.d2c.common.base.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 泛型工具类
 *
 * @author wull
 */
public class GenericUt {

    /**
     * 获取泛型对象clz中第index个泛型类
     */
    public static Class<?> getTypeClazz(Class<?> clz) {
        return getTypeClazz(clz, 0);
    }

    public static Class<?> getTypeClazz(Class<?> clz, int index) {
        return getTypeClazz((ParameterizedType) clz.getGenericSuperclass(), index);
    }

    public static Class<?> getTypeClazz(Field field) {
        return getTypeClazz(field, 0);
    }

    public static Class<?> getTypeClazz(Field field, int index) {
        return getTypeClazz((ParameterizedType) field.getGenericType(), index);
    }

    private static Class<?> getTypeClazz(ParameterizedType type, int index) {
        Type[] types = type.getActualTypeArguments();
        if (index >= 0 && index < types.length) {
            return (Class<?>) types[index];
        }
        return null;
    }

}
