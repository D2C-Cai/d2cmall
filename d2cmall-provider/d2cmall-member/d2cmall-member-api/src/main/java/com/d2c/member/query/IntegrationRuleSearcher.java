package com.d2c.member.query;

import com.d2c.common.api.query.model.BaseQuery;

public class IntegrationRuleSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    private String name;
    /**
     * 状态（1：启用，0：未启用，-1：删除）
     */
    private Integer status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
