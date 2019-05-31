package com.d2c.content.query;

import com.d2c.common.api.query.model.BaseQuery;

public class ArticleSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 标题
     */
    private String title;
    /**
     * 英文名称
     */
    private String name;
    /**
     * 是否发布
     */
    private Boolean published;
    /**
     * 文章类别ID
     */
    private Long articleCategoryId;
    /**
     * 品牌ID
     */
    private Long brandId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getArticleCategoryId() {
        return articleCategoryId;
    }

    public void setArticleCategoryId(Long articleCategoryId) {
        this.articleCategoryId = articleCategoryId;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

}
