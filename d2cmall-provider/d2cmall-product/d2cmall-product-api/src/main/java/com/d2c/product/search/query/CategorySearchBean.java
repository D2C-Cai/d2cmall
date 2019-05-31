package com.d2c.product.search.query;

import java.io.Serializable;

public class CategorySearchBean implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    private long[] id;
    /**
     * 关键字
     */
    private String keyword;
    /**
     * 一级ID
     */
    private Long topId;
    /**
     * 父级ID
     */
    private Long parentId;
    /**
     * 路径
     */
    private Long path;

    public long[] getId() {
        return id;
    }

    public void setId(long[] id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getTopId() {
        return topId;
    }

    public void setTopId(Long topId) {
        this.topId = topId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getPath() {
        return path;
    }

    public void setPath(Long path) {
        this.path = path;
    }

}

