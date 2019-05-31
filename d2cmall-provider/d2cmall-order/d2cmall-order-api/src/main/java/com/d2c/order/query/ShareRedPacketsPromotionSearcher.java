package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;

public class ShareRedPacketsPromotionSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 名称
     */
    private String name;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
