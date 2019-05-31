package com.d2c.content.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class ThemeSearcher extends BaseQuery {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 标题
     */
    private String name;
    /**
     * 1上架 0下架
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
     * 是否本周精选
     */
    private Integer recommend;
    /**
     * 上架开始时间
     */
    private Date beginUpMarketDate;
    /**
     * 上架结束时间
     */
    private Date endUpMarketDate;
    /**
     * 标签ID
     */
    private Long tagId;
    /**
     * 类型
     */
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
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

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
