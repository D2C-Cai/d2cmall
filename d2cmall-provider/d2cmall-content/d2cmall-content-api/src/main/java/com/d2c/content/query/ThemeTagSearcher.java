package com.d2c.content.query;

import com.d2c.common.api.query.model.BaseQuery;

public class ThemeTagSearcher extends BaseQuery {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private String type;
    /**
     * 是否为固定标签
     */
    private Integer fix;
    /**
     * 上下架
     */
    private Integer status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getFix() {
        return fix;
    }

    public void setFix(Integer fix) {
        this.fix = fix;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
