package com.d2c.product.query;

import com.d2c.common.api.query.model.BaseQuery;

public class ProductTagRelationSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    private Long id;
    private ProductSearcher productSearcher;

    public ProductSearcher getProductSearcher() {
        return productSearcher;
    }

    public void setProductSearcher(ProductSearcher productSearcher) {
        this.productSearcher = productSearcher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
