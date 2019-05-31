package com.d2c.logger.query;

import com.d2c.common.api.query.model.BaseQuery;

public class PopMessageSearcher extends BaseQuery {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
