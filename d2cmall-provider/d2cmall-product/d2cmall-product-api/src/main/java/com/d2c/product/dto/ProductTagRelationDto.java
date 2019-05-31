package com.d2c.product.dto;

import com.d2c.product.model.Product;
import com.d2c.product.model.ProductTag;
import com.d2c.product.model.ProductTagRelation;

public class ProductTagRelationDto extends ProductTagRelation {

    private static final long serialVersionUID = 1L;
    /**
     * 商品
     */
    private Product product;
    /**
     * 标签
     */
    private ProductTag tag;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductTag getTag() {
        return tag;
    }

    public void setTag(ProductTag tag) {
        this.tag = tag;
    }

}
