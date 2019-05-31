package com.d2c.common.core.cache;

import com.d2c.common.base.utils.JsonUt;

import java.io.Serializable;

public class CacheBean implements Serializable {

    private static final long serialVersionUID = 5426494620491161300L;
    private Object value;
    private Long outTime;

    public CacheBean(Object value, long keepTime) {
        this.value = value;
        this.outTime = System.currentTimeMillis() + keepTime;
    }

    public CacheBean(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return JsonUt.serialize(this);
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Long getOutTime() {
        return outTime;
    }

    public void setOutTime(Long outTime) {
        this.outTime = outTime;
    }

}
