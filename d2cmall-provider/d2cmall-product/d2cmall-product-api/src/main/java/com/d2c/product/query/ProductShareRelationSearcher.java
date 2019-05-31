package com.d2c.product.query;

import com.d2c.common.api.query.model.BaseQuery;

public class ProductShareRelationSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 买家秀ID
     */
    private Long shareId;

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
