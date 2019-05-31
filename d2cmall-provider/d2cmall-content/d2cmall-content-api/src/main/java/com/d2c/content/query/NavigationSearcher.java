package com.d2c.content.query;

import com.d2c.common.api.query.model.BaseQuery;

public class NavigationSearcher extends BaseQuery {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 版本号
     */
    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
