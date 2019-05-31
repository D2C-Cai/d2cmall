package com.d2c.member.query;

import com.d2c.common.api.query.model.BaseQuery;

public class RoleSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 描述
     */
    private String description;
    /**
     * 名称
     */
    private String userName;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
