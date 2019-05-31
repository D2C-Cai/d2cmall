package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;

public class LogisticsTemplateSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 物流模板
     */
    private String templateName;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

}
