package com.d2c.product.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 商品标签关系
 */
@Table(name = "p_product_tag_relation")
public class ProductTagRelation extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 标签ID
     */
    @AssertColumn("商品ID不能为空")
    private Long tagId;
    /**
     * 产品ID
     */
    private Long productId;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

}
