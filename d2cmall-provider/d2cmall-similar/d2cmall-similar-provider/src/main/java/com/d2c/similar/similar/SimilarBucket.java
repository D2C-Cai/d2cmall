package com.d2c.similar.similar;

import com.d2c.common.core.base.bucket.PageBucket;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.similar.entity.SimilarSchemeDO;

import java.util.Date;
import java.util.List;

public class SimilarBucket extends PageBucket<SearcherProduct> {

    private ProductSearcherQueryService service;
    private SimilarSchemeDO scheme;
    private Date lastDate;

    public SimilarBucket(ProductSearcherQueryService serv, SimilarSchemeDO scheme, Integer maxSize) {
        super(maxSize);
        this.service = serv;
        this.scheme = scheme;
    }

    @Override
    public List<SearcherProduct> nextList(int page, int pageSize) {
        return service.findProductByTopCategory(scheme.getCategoryId().longValue(), lastDate, page, pageSize);
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

}
