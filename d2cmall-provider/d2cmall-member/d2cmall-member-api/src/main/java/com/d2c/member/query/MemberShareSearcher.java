package com.d2c.member.query;

import com.d2c.common.api.query.model.BaseQuery;
import com.d2c.member.search.query.MemberShareSearchBean;
import org.elasticsearch.search.sort.SortOrder;

import java.util.Date;
import java.util.List;

public class MemberShareSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 状态，0未审核，1已审核
     */
    private Integer status;
    /**
     * 会员名
     */
    private String name;
    /**
     * 会员昵称
     */
    private String nickname;
    /**
     * 会员手机
     */
    private String mobile;
    /**
     * 会员邮箱
     */
    private String email;
    /**
     * 创建时间开始
     */
    private Date beginCreateDate;
    /**
     * 创建时间结束
     */
    private Date endCreateDate;
    /**
     * 标签ID
     */
    private Long tagId;
    /**
     * 街道
     */
    private String street;
    /**
     * 设计师ID
     */
    private Long designerId;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 排序
     */
    private String sortLike;
    /**
     * 源ID
     */
    private String sourceId;
    /**
     * 源IDs
     */
    private List<Long> sourceIds;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 资源类型
     */
    private String resourceType;
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

    public Boolean getAllList() {
        return allList;
    }

    public void setAllList(Boolean allList) {
        this.allList = allList;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Date getBcd() {
        return beginCreateDate;
    }

    public void setBcd(Date bcd) {
        this.beginCreateDate = bcd;
    }

    public Date getEcd() {
        return endCreateDate;
    }

    public void setEcd(Date ecd) {
        this.endCreateDate = ecd;
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

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public String getSortLike() {
        return sortLike;
    }

    public void setSortLike(String sortLike) {
        this.sortLike = sortLike;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public List<Long> getSourceIds() {
        return sourceIds;
    }

    public void setSourceIds(List<Long> sourceIds) {
        this.sourceIds = sourceIds;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public MemberShareSearchBean initSearchQuery() {
        MemberShareSearchBean searcherBean = new MemberShareSearchBean();
        searcherBean.setTagId(this.getTagId());
        searcherBean.setDesignerId(this.getDesignerId());
        searcherBean.setProductId(this.getProductId());
        searcherBean.setMemberId(this.getMemberId());
        searcherBean.setStatus(this.getStatus());
        String[] sortFields = new String[]{"top", "createDate"};
        SortOrder[] sortOrders = new SortOrder[]{SortOrder.DESC, SortOrder.DESC};
        if (this.getMemberId() != null) {
            sortFields = new String[]{"createDate"};
            sortOrders = new SortOrder[]{SortOrder.DESC};
        }
        searcherBean.setOrders(sortOrders);
        searcherBean.setSortFields(sortFields);
        searcherBean.setResourceType(this.getResourceType());
        searcherBean.setAllList(this.getAllList());
        return searcherBean;
    }

}