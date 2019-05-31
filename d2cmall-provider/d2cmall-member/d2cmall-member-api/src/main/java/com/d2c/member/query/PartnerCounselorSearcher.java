package com.d2c.member.query;

import com.d2c.common.api.query.model.BaseQuery;

public class PartnerCounselorSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 顾问昵称
     */
    private String name;
    /**
     * 状态
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
