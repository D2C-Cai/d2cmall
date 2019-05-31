package com.d2c.similar.query;

import com.d2c.common.api.annotation.search.SearchField;
import com.d2c.common.api.query.enums.OperType;
import com.d2c.common.api.query.model.MongoQuery;

public class SimilarMgQuery extends MongoQuery {

    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    @SearchField("target._id")
    private Long productId;
    /**
     * 产品名称
     */
    @SearchField(name = "target.name", oper = OperType.LIKE)
    private String name;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
