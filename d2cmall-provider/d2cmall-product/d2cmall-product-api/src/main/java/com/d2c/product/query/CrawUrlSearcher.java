package com.d2c.product.query;

import com.d2c.common.api.query.model.BaseQuery;

public class CrawUrlSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    private String crawUrl;

    public String getCrawUrl() {
        return crawUrl;
    }

    public void setCrawUrl(String crawUrl) {
        this.crawUrl = crawUrl;
    }

}
