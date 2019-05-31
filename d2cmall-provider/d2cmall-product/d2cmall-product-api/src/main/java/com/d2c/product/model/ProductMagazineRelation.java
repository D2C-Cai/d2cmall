package com.d2c.product.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 商品杂志关系
 */
@Table(name = "p_product_magazine_relation")
public class ProductMagazineRelation extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 页面ID
     */
    private Long pageId;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 排序
     */
    private Integer sort;

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
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
