package com.d2c.member.search.query;

import org.elasticsearch.search.sort.SortOrder;

import java.io.Serializable;

public class MemberShareSearchBean implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 关键字
     */
    private String keyword;
    /**
     * 用户ID
     */
    private Long memberId;
    /**
     * 城市
     */
    private String city;
    /**
     * 设计师ID
     */
    private Long designerId;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 标签ID
     */
    private Long tagId;
    /**
     * 买家秀ID
     */
    private Long shareId;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 标签，逗号分隔
     */
    private String tags;
    /**
     * 排序字段
     */
    private String[] sortFields;
    /**
     * 顺序
     */
    private SortOrder[] orders;
    /**
     * 图片标签
     */
    private String picTag;
    /**
     * 资源类型
     */
    private String resourceType;
    /**
     * 买家秀类型 1.买家秀 2.评论生成
     */
    private Integer type;
    /**
     * 话题ID
     */
    private Long topicId;
    /**
     * 显示我的所有和其他人审核的
     */
    private Boolean allList = false;
    /**
     * 0普通用户1设计师 2D2C官方5达人
     */
    private Integer role;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getShareId() {
        return shareId;
    }

    public void setShareId(Long shareId) {
        this.shareId = shareId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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

    public String getPicTag() {
        return picTag;
    }

    public void setPicTag(String picTag) {
        this.picTag = picTag;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Boolean getAllList() {
        return allList;
    }

    public void setAllList(Boolean allList) {
        this.allList = allList;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

}
