package com.d2c.common.api.convert;

public interface ConvertAble<E, V> {

    public V convert(E bean);

    public E convertBack(V bean);

}
