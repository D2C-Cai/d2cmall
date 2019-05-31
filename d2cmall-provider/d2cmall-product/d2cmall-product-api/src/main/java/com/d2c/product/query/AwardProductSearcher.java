package com.d2c.product.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class AwardProductSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
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
     * 奖品名称
     */
    private String awardName;
    /**
     * 类型
     */
    private String type;
    /**
     * 场次ID
     */
    private Long sessionId;
    /**
     * 场次名称
     */
    private String sessionName;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
