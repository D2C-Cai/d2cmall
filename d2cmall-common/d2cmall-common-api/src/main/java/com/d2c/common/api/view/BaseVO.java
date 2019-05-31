package com.d2c.common.api.view;

import com.d2c.common.api.convert.ConvertBean;
import com.d2c.common.base.utils.JsonUt;

public abstract class BaseVO extends ConvertBean implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return JsonUt.serialize(this);
    }

}
