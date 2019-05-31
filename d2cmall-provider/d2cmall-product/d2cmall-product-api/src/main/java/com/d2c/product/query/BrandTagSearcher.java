package com.d2c.product.query;

import com.d2c.common.api.query.model.BaseQuery;

public class BrandTagSearcher extends BaseQuery {

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
