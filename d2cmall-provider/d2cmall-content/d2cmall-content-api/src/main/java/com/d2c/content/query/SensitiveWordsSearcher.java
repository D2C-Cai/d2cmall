package com.d2c.content.query;

import com.d2c.common.api.query.model.BaseQuery;

/**
 * 敏感词
 */
public class SensitiveWordsSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    private String keyword;
    private Integer status;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
