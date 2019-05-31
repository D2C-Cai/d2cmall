package com.d2c.content.search.query;

import java.io.Serializable;

public class ThemeSearcherBean implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 标签ID
     */
    private Long tagId;
    /**
     * 标题
     */
    private String title;
    /**
     * 关键字
     */
    private String keyword;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 类型
     */
    private String type;
    /**
     * 下周
     */
    private Integer fix;

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getFix() {
        return fix;
    }

    public void setFix(Integer fix) {
        this.fix = fix;
    }

}
