package com.d2c.similar.helper;

import com.d2c.common.base.utils.BeanUt;

public class SimilarHelper {

    public static Object findId(Object bean) {
        Object id = BeanUt.getValue(bean, "id");
        if (id == null) id = BeanUt.getValue(bean, "_id");
        if (id == null) id = BeanUt.getValue(bean, "productId");
        return id;
    }

}
