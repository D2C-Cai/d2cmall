package com.d2c.product.query;

import com.d2c.common.api.query.model.BaseQuery;

public class CrawDesignerSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    private String crawUrl;
    private String crawDesignerName;

    public String getCrawUrl() {
        return crawUrl;
    }

    public void setCrawUrl(String crawUrl) {
        this.crawUrl = crawUrl;
    }

    public String getCrawDesignerName() {
        return crawDesignerName;
    }

    public void setCrawDesignerName(String crawDesignerName) {
        this.crawDesignerName = crawDesignerName;
    }

}
