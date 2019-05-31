package com.d2c.content.query;

import com.d2c.common.api.query.model.BaseQuery;

import javax.swing.*;

/**
 * @author xh
 * @see 搜索关键词
 */
public class SearcherBean extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 排序字段
     */
    String[] sortFields;
    /**
     * 排序顺序
     */
    SortOrder[] orders;
    /**
     * 关键字
     */
    private String keyword;
    /**
     * 设计师ID
     */
    private Long[] designerId;
    /**
     * 二级分类ID
     */
    private Long[] categoryId;
    /**
     * 一级分类ID
     */
    private Long topId;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long[] getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long[] designerId) {
        this.designerId = designerId;
    }

    public Long[] getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long[] categoryId) {
        this.categoryId = categoryId;
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

    public Long getTopId() {
        return topId;
    }

    public void setTopId(Long topId) {
        this.topId = topId;
    }

}
