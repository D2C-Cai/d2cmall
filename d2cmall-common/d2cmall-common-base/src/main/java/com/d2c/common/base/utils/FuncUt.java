package com.d2c.common.base.utils;

import com.d2c.common.base.func.Function;

/**
 * 方法工具类
 */
public class FuncUt {

    public static <T> T getValue(T value, Function<T, T> func) {
        if (func != null) {
            return func.call(value);
        }
        return value;
    }

}
