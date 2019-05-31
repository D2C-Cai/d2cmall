package com.d2c.member.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class PartnerInviteSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 邀请人ID
     */
    private Long fromMemberId;
    /**
     * 邀请人分销ID
     */
    private Long fromPartnerId;
    /**
     * 受邀人ID
     */
    private Long toMemberId;
    /**
     * 受邀人分销ID
     */
    private Long toPartnerId;
    /**
     * 创建时间开始
     */
    private Date createDateStart;
    /**
     * 创建时间结束
     */
    private Date createDateEnd;
    /**
     * 是否奖励 0：否，1：是
     */
    private Integer award;
    /**
     * 邀请人等级
     */
    private Integer fromLevel;
    /**
     * 受邀人等级
     */
    private Integer toLevel;
    /**
     * 受邀人账号
     */
    private String fromLoginCode;
    /**
     * 被邀人账号
     */
    private String toLoginCode;

    public Long getFromMemberId() {
        return fromMemberId;
    }

    public void setFromMemberId(Long fromMemberId) {
        this.fromMemberId = fromMemberId;
    }

    public Long getFromPartnerId() {
        return fromPartnerId;
    }

    public void setFromPartnerId(Long fromPartnerId) {
        this.fromPartnerId = fromPartnerId;
    }

    public Long getToMemberId() {
        return toMemberId;
    }

    public void setToMemberId(Long toMemberId) {
        this.toMemberId = toMemberId;
    }

    public Long getToPartnerId() {
        return toPartnerId;
    }

    public void setToPartnerId(Long toPartnerId) {
        this.toPartnerId = toPartnerId;
    }

    public Date getCreateDateStart() {
        return createDateStart;
    }

    public void setCreateDateStart(Date createDateStart) {
        this.createDateStart = createDateStart;
    }

    public Date getCreateDateEnd() {
        return createDateEnd;
    }

    public void setCreateDateEnd(Date createDateEnd) {
        this.createDateEnd = createDateEnd;
    }

    public Integer getAward() {
        return award;
    }

    public void setAward(Integer award) {
        this.award = award;
    }

    public Integer getFromLevel() {
        return fromLevel;
    }

    public void setFromLevel(Integer fromLevel) {
        this.fromLevel = fromLevel;
    }

    public Integer getToLevel() {
        return toLevel;
    }

    public void setToLevel(Integer toLevel) {
        this.toLevel = toLevel;
    }

    public String getFromLoginCode() {
        return fromLoginCode;
    }

    public void setFromLoginCode(String fromLoginCode) {
        this.fromLoginCode = fromLoginCode;
    }

    public String getToLoginCode() {
        return toLoginCode;
    }

    public void setToLoginCode(String toLoginCode) {
        this.toLoginCode = toLoginCode;
    }

}
