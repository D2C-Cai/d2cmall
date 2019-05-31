package com.d2c.product.dto;

import com.d2c.product.model.CrawDesigner;
import com.d2c.product.model.CrawProduct;
import com.d2c.product.search.model.SearcherProduct;

import java.io.Serializable;
import java.util.List;

public class CrawProductDto implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 爬虫品牌
     */
    private CrawDesigner crawDesigner;
    /**
     * 商品
     */
    private SearcherProduct searcherProduct;
    /**
     * 爬虫商品
     */
    private List<CrawProduct> crawProducts;

    public SearcherProduct getSearcherProduct() {
        return searcherProduct;
    }

    public void setSearcherProduct(SearcherProduct searcherProduct) {
        this.searcherProduct = searcherProduct;
    }

    public List<CrawProduct> getCrawProducts() {
        return crawProducts;
    }

    public void setCrawProducts(List<CrawProduct> crawProducts) {
        this.crawProducts = crawProducts;
    }

    public CrawDesigner getCrawDesigner() {
        return crawDesigner;
    }

    public void setCrawDesigner(CrawDesigner crawDesigner) {
        this.crawDesigner = crawDesigner;
    }

}