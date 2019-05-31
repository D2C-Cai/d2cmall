package com.d2c.content.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class SubscribeSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 开始时间
     */
    private Date beginCreateDate;
    /**
     * 结束时间
     */
    private Date endCreateDate;
    /**
     * 枚举类型
     */
    private String subType;
    /**
     * 用户ID
     */
    private Long memberId;
    /**
     * 用户登录号
     */
    private String memberCode;
    /**
     * 用户名
     */
    private String memberName;
    /**
     * 邮件地址
     */
    private String subscribe;

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

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }

}
