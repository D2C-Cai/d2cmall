package com.d2c.member.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class MemberShareCommentSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 源ID
     */
    private Long sourceId;
    /**
     * 是否审核
     */
    private Integer verified;
    /**
     * 评论开始时间
     */
    private Date startDate;
    /**
     * 评论结束时间
     */
    private Date endDate;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 创建时间
     */
    private String sortCreateDate = "ASC";
    /**
     * 设备终端
     */
    private String device;
    /**
     * 是否为热门评论
     */
    private Integer hot;
    /**
     * 是否显示该用户未审核评论
     */
    private boolean allList = false;

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getVerified() {
        return verified;
    }

    public void setVerified(Integer verified) {
        this.verified = verified;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getSortCreateDate() {
        return sortCreateDate;
    }

    public void setSortCreateDate(String sortCreateDate) {
        this.sortCreateDate = sortCreateDate;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Integer getHot() {
        return hot;
    }

    public void setHot(Integer hot) {
        this.hot = hot;
    }

    public boolean getAllList() {
        return allList;
    }

    public void setAllList(boolean allList) {
        this.allList = allList;
    }

}
