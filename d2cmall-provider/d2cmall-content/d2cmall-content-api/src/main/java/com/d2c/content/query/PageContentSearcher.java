package com.d2c.content.query;

import com.d2c.common.api.query.model.BaseQuery;

public class PageContentSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 一级模块(MAIN,BRAND,SQUARE,NAV,MY)
     */
    private String parent;
    /**
     * 方向（（TOP）上，（BOTTOM）下，（LEFT）左、（RIGHT）右）
     */
    private String direction;
    /**
     * 标题
     */
    private String title;
    /**
     * ajax请求地址
     */
    private String url;
    /**
     * web浏览器打开地址
     */
    private String webUrl;
    /**
     * 同一级，只能有一个是默认状态，如果没有，则第一个是
     */
    private Integer isDefault;
    /**
     * 属于分类
     */
    private Integer isCategory;
    /**
     * 分类
     */
    private Long categoryId;

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getIsCategory() {
        return isCategory;
    }

    public void setIsCategory(Integer isCategory) {
        this.isCategory = isCategory;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

}
