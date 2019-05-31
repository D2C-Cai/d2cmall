package com.d2c.content.query;

import com.d2c.common.api.query.model.BaseQuery;

public class SectionSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * SubModule的ID
     */
    private Long moduleId;
    /**
     * 标题
     */
    private String title;
    /**
     * 类型(100之内为系统内置，无需定义内容，新增之后，不允许修改)
     */
    private Integer type;

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
