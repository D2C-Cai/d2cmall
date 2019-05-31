package com.d2c.product.dto;

import com.d2c.product.model.FlashPromotion;
import com.d2c.product.search.model.SearcherProduct;

import java.util.List;

public class FlashPromotionDto extends FlashPromotion {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private List<SearcherProduct> products;

    public List<SearcherProduct> getProducts() {
        return products;
    }

    public void setProducts(List<SearcherProduct> products) {
        this.products = products;
    }

}
