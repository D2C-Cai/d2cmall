package com.d2c.logger.query;

import com.d2c.common.api.query.model.BaseQuery;

public class TemplateSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 模板类型
     */
    private String templateType;
    /**
     * 主题
     */
    private String subject;

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

}
