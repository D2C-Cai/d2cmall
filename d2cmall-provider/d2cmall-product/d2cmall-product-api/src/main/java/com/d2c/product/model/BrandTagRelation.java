package com.d2c.product.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 品牌标签关系
 */
@Table(name = "p_brand_tag_relation")
public class BrandTagRelation extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 标签ID
     */
    private Long tagId;
    /**
     * 设计师ID
     */
    private Long designerId;
    /**
     * 排序号
     */
    private Integer sort = 0;

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

}
