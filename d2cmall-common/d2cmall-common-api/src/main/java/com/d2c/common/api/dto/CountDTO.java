package com.d2c.common.api.dto;

import java.io.Serializable;

/**
 * 数量的分组
 *
 * @author xh
 */
public class CountDTO<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private T value;
    private int count = 0;

    public CountDTO() {
    }

    public CountDTO(T object, int count) {
        this.value = object;
        this.count = count;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
