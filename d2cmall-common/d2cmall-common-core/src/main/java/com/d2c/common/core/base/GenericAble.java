package com.d2c.common.core.base;

import java.lang.reflect.ParameterizedType;

public abstract class GenericAble<T> {

    private Class<T> typeClz;

    @SuppressWarnings("unchecked")
    protected Class<T> getTypeClazz() {
        if (typeClz == null) {
            typeClz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return typeClz;
    }

}
