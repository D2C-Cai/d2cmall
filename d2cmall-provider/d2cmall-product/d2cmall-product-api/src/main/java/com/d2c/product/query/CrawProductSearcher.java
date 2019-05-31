package com.d2c.product.query;

import com.d2c.common.api.query.model.BaseQuery;

public class CrawProductSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 抓取商品的设计师ID
     */
    private Long id;
    /**
     * 商品名
     */
    private String productName;
    /**
     * 店铺名
     */
    private String mallName;
    /**
     * 设计师ID
     */
    private Long designerId;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMallName() {
        return mallName;
    }

    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
