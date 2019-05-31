package com.d2c.common.api.convert;

public abstract class ConvertBean {

    public static <V> V convert(Object bean, Class<V> resClz) {
        return ConvertHelper.convert(bean, resClz);
    }

    public <E> E convertBack(Class<E> resClz) {
        return ConvertHelper.convert(this, resClz);
    }

}
