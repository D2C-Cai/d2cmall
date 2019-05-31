package com.d2c.common.api.convert;

import com.d2c.common.base.helper.GenericHelper;
import com.d2c.common.base.utils.BeanUt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseConvert<E, V> implements ConvertAble<E, V> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public V convert(E bean) {
        return afterConvert(BeanUt.buildBean(bean, getCovertToClz()));
    }

    protected V afterConvert(V view) {
        return view;
    }

    @Override
    public E convertBack(V bean) {
        return afterConvertBack(BeanUt.buildBean(bean, getCovertFromClz()));
    }

    protected E afterConvertBack(E view) {
        return view;
    }

    @SuppressWarnings("unchecked")
    public Class<E> getCovertFromClz() {
        return (Class<E>) GenericHelper.getTypeClazz(this, 0);
    }

    @SuppressWarnings("unchecked")
    public Class<V> getCovertToClz() {
        return (Class<V>) GenericHelper.getTypeClazz(this, 1);
    }

}
