package com.d2c.product.query;

import com.d2c.common.api.query.model.BaseQuery;

public class BrandTagRelationSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 编码
     */
    private String code;
    /**
     * 标签ID
     */
    private Long tagId;
    /**
     * 设计师ID
     */
    private Long designerId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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

}
