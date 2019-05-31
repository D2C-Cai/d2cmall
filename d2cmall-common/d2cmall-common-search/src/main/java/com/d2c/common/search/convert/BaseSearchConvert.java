package com.d2c.common.search.convert;

import com.d2c.common.api.model.BaseParentDO;
import com.d2c.common.base.utils.ConvertUt;
import com.d2c.common.search.model.ParentSearchDO;

import java.lang.reflect.ParameterizedType;

public abstract class BaseSearchConvert<T extends ParentSearchDO<?>, E extends BaseParentDO<?>> {

    //****************************************************************************
    private Class<T> searchClz;
    private Class<E> entityClz;

    public T convertTo(E entity) {
        return ConvertUt.convertBean(entity, getSearchClz());
    }

    public E convertBack(T search) {
        return ConvertUt.convertBean(search, getEntityClz());
    }

    @SuppressWarnings("unchecked")
    protected Class<T> getSearchClz() {
        if (searchClz == null) {
            searchClz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return searchClz;
    }

    @SuppressWarnings("unchecked")
    protected Class<E> getEntityClz() {
        if (entityClz == null) {
            entityClz = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        }
        return entityClz;
    }

}
