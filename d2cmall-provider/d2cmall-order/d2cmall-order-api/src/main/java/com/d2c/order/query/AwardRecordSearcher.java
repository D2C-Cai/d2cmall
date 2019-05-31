package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class AwardRecordSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 会员id
     */
    private Long memberId;
    /**
     * 会员名称
     */
    private String memberName;
    /**
     * D2C账号(loginNo)
     */
    private String loginNo;
    /**
     * 奖品等级
     */
    private Integer awardLevel;
    /**
     * 奖品名称
     */
    private String awardName;
    /**
     * 开始查询时间
     */
    private Date startDate;
    /**
     * 结束查询时间
     */
    private Date endDate;
    /**
     * 等級排名
     */
    private String levelName;
    /**
     * 奖品类型
     */
    private String awardType;
    /**
     * 場次ID
     */
    private Long sessionId;
    /**
     * 场次名称
     */
    private String sessionName;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getLoginNo() {
        return loginNo;
    }

    public void setLoginNo(String loginNo) {
        this.loginNo = loginNo;
    }

    public Integer getAwardLevel() {
        return awardLevel;
    }

    public void setAwardLevel(Integer awardLevel) {
        this.awardLevel = awardLevel;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
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

    public String getAwardType() {
        return awardType;
    }

    public void setAwardType(String awardType) {
        this.awardType = awardType;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

}
