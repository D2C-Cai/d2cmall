package com.d2c.common.base.func;

import java.io.Serializable;

@FunctionalInterface
public interface Function<T, R> extends Serializable {

    R call(T v);

}