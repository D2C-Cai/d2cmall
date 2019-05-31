package com.d2c.product.dto;

import com.d2c.product.model.Product;
import com.d2c.product.model.ProductComb;

import java.util.List;

public class ProductCombDto extends ProductComb {

    private static final long serialVersionUID = 1L;
    /**
     * 商品列表
     */
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
