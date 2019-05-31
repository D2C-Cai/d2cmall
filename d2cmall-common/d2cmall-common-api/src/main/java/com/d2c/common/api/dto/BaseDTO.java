package com.d2c.common.api.dto;

import com.d2c.common.base.utils.JsonUt;

public abstract class BaseDTO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return JsonUt.serialize(this);
    }

}
