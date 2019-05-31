package com.d2c.product.search.query;

import org.elasticsearch.search.sort.SortOrder;

import java.io.Serializable;

public class DesignerSearchBean implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 关键字
     */
    private String keyword;
    /**
     * 区域
     */
    private String country;
    /**
     * 风格
     */
    private String style;
    /**
     * 分类
     */
    private String designArea;
    /**
     * A-Z
     */
    private String pageGroup;
    /**
     * 标签ID
     */
    private Long tagId;
    /**
     * 设计师ID
     */
    private Long designerId;
    /**
     * 是否上架
     */
    private Integer mark;
    /**
     * 排序字段
     */
    private String[] sortFields;
    /**
     * 顺序
     */
    private SortOrder[] orders;
    /**
     * 品牌ids
     */
    private String[] ids;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getDesignArea() {
        return designArea;
    }

    public void setDesignArea(String designArea) {
        this.designArea = designArea;
    }

    public String getPageGroup() {
        return pageGroup;
    }

    public void setPageGroup(String pageGroup) {
        this.pageGroup = pageGroup;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public String[] getSortFields() {
        return sortFields;
    }

    public void setSortFields(String[] sortFields) {
        this.sortFields = sortFields;
    }

    public SortOrder[] getOrders() {
        return orders;
    }

    public void setOrders(SortOrder[] orders) {
        this.orders = orders;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

}
