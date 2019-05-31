package com.d2c.content.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 实体类 -敏感词
 */
@Table(name = "v_sensitive_words")
public class SensitiveWords extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 敏感词
     */
    @AssertColumn("敏感词不能为空")
    private String keyword;
    /**
     * 1:启用 0：停用 -1：逻辑删除
     */
    private Integer status = 1;

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
