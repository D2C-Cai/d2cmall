package com.d2c.content.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 实体类 -文章分类
 */
@Table(name = "v_article_category")
public class ArticleCategory extends PreUserDO {

    /**
     * 树路径分隔符
     */
    public static final String PATH_SEPARATOR = ",";
    private static final long serialVersionUID = 1L;
    /**
     * 分类名称
     */
    @AssertColumn("文章分类名称不能为空")
    private String name;
    /**
     * 页面关键词
     */
    private String metaKeywords;
    /**
     * 页面描述
     */
    private String metaDescription;
    /**
     * 排序
     */
    private Integer orderList = 0;
    /**
     * 树路径
     */
    private String path;
    /**
     * 上级分类ID
     */
    private Long parentId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public Integer getOrderList() {
        return orderList;
    }

    public void setOrderList(Integer orderList) {
        this.orderList = orderList;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

}