package com.d2c.content.query;

import com.d2c.common.api.query.model.BaseQuery;

public class AdResourceSearcher extends BaseQuery {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
