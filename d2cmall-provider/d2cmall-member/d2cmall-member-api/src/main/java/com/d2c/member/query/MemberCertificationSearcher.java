package com.d2c.member.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class MemberCertificationSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 账号
     */
    private String loginCode;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 是否活体认证
     */
    private Integer authenticate;
    /**
     * 开始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAuthenticate() {
        return authenticate;
    }

    public void setAuthenticate(Integer authenticate) {
        this.authenticate = authenticate;
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

}
