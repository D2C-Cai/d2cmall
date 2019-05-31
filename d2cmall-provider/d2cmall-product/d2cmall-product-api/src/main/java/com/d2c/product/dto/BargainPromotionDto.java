package com.d2c.product.dto;

import com.d2c.product.model.BargainPromotion;
import com.d2c.product.model.Product;

public class BargainPromotionDto extends BargainPromotion {

    private static final long serialVersionUID = 1L;
    /**
     * 商品
     */
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
