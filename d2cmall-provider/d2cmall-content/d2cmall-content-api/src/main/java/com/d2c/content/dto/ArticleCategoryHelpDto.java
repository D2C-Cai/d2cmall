package com.d2c.content.dto;

import java.io.Serializable;

public class ArticleCategoryHelpDto implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    protected Long id;
    /**
     * 名称
     */
    protected String name;
    /**
     * 父节点id
     */
    protected Long parentId;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
