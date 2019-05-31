package com.d2c.product.dto;

import com.d2c.product.model.AuctionProduct;

public class AuctionProductDto extends AuctionProduct {

    private static final long serialVersionUID = 1L;
    /**
     * 商品
     */
    private ProductDto product;

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

}
