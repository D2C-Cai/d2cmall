package com.d2c.product.search.model;

import java.io.Serializable;

public class SearcherDesignerTagRelation implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    private Long designerId;
    /**
     * 标签ID
     */
    private Long tagId;
    /**
     * 排序号
     */
    private Integer sort = 0;

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

}
