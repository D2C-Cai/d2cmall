package com.d2c.common.base.helper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class GenericHelper {

    private static Map<String, Type[]> map = new HashMap<>();

    public static Class<?> getTypeClazz(Object object, int index) {
        return getTypeClazz(object.getClass(), index);
    }

    public static Class<?> getTypeClazz(Class<?> clz, int index) {
        String key = clz.getName();
        Type[] types = map.get(key);
        if (types == null) {
            types = ((ParameterizedType) clz.getGenericSuperclass()).getActualTypeArguments();
            map.put(key, types);
        }
        return (Class<?>) types[index];
    }

}
