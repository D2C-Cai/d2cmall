package com.d2c.member.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class WardrobeSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 会员账号
     */
    private String loginCode;
    /**
     * 一级分类
     */
    private String topName;
    /**
     * 二级分类
     */
    private String categoryName;
    /**
     * 创建时间
     */
    private Date beginDate;
    private Date endDate;
    /**
     * 标签
     */
    private String tags;
    /**
     * 季节
     */
    private String season;
    /**
     * 价位
     */
    private String price;
    /**
     * 场景
     */
    private String scene;
    /**
     * 色彩
     */
    private String color;
    /**
     * 分类名称
     */
    private Long categoryId;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getTopName() {
        return topName;
    }

    public void setTopName(String topName) {
        this.topName = topName;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

}
