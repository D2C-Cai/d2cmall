package com.d2c.member.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class TopicSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 标题
     */
    private String title;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 创建开始时间
     */
    private Date beginCreateDate;
    /**
     * 创建结束时间
     */
    private Date endCreateDate;
    /**
     * 发布开始时间
     */
    private Date beginUpMarketDate;
    /**
     * 发布结束时间
     */
    private Date endUpMarketDate;
    /**
     * 按参与人数排序
     */
    private String sortByCount;
    /**
     * 排序字段
     */
    private String sortByField;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getBeginCreateDate() {
        return beginCreateDate;
    }

    public void setBeginCreateDate(Date beginCreateDate) {
        this.beginCreateDate = beginCreateDate;
    }

    public Date getEndCreateDate() {
        return endCreateDate;
    }

    public void setEndCreateDate(Date endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

    public Date getBeginUpMarketDate() {
        return beginUpMarketDate;
    }

    public void setBeginUpMarketDate(Date beginUpMarketDate) {
        this.beginUpMarketDate = beginUpMarketDate;
    }

    public Date getEndUpMarketDate() {
        return endUpMarketDate;
    }

    public void setEndUpMarketDate(Date endUpMarketDate) {
        this.endUpMarketDate = endUpMarketDate;
    }

    public String getSortByCount() {
        return sortByCount;
    }

    public void setSortByCount(String sortByCount) {
        this.sortByCount = sortByCount;
    }

    public String getSortByField() {
        return sortByField;
    }

    public void setSortByField(String sortByField) {
        this.sortByField = sortByField;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
