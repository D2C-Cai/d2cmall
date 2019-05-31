package com.d2c.product.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 商品分销关系
 */
@Table(name = "p_product_partner_relation")
public class ProductPartnerRelation extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 分销店铺ID
     */
    private Long partnerStoreId;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 排序
     */
    private Integer sort;

    public Long getPartnerStoreId() {
        return partnerStoreId;
    }

    public void setPartnerStoreId(Long partnerStoreId) {
        this.partnerStoreId = partnerStoreId;
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
