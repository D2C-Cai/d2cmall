package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;

public class CouponDefGroupSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
