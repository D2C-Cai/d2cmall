package com.d2c.behavior.mongo.model.collabor;

import java.io.Serializable;

/**
 * 商品推荐值
 *
 * @author wull
 */
public class ProductRatingDO implements Serializable {

    private static final long serialVersionUID = 5042165429570203812L;
    private Long productId;
    private Double ratring;

    public ProductRatingDO() {
    }

    public ProductRatingDO(Long productId, Double ratring) {
        this.productId = productId;
        this.ratring = ratring;
    }

    public Double getRatring() {
        return ratring;
    }

    public void setRatring(Double ratring) {
        this.ratring = ratring;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

}
