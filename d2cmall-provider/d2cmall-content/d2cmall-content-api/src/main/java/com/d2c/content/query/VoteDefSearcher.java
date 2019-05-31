package com.d2c.content.query;

import com.d2c.common.api.query.model.BaseQuery;

public class VoteDefSearcher extends BaseQuery {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String title;
    private Integer status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
