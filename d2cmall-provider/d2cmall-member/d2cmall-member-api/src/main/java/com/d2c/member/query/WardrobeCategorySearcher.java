package com.d2c.member.query;

import com.d2c.common.api.query.model.BaseQuery;

public class WardrobeCategorySearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 顶级分类名称
     */
    private String topName;
    /**
     * 分类名称
     */
    private String name;

    public String getTopName() {
        return topName;
    }

    public void setTopName(String topName) {
        this.topName = topName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
