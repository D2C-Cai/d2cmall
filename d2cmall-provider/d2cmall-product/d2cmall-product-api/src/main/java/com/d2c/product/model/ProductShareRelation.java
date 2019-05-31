package com.d2c.product.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 商品买家秀关系
 */
@Table(name = "p_product_share_relation")
public class ProductShareRelation extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 买家秀列表
     */
    private Long shareId;

    public ProductShareRelation() {
    }

    public ProductShareRelation(Long productId, Long shareId) {
        this.productId = productId;
        this.shareId = shareId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getShareId() {
        return shareId;
    }

    public void setShareId(Long shareId) {
        this.shareId = shareId;
    }

}
