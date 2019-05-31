package com.d2c.member.query;

import com.d2c.common.api.query.model.BaseQuery;
import com.d2c.util.string.LoginUtil;

public class DistributorSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 组Id
     */
    private Long groupId;
    /**
     * 会员编号
     */
    private Long memberId;
    /**
     * 0停用，1启用
     */
    private Integer status;
    /**
     * 用户名
     */
    private String memberName;
    /**
     * 邮箱
     */
    private String memberEmail;
    /**
     * 手机
     */
    private String memberMobile;
    /**
     * 登录账号，第一个
     */
    private String loginCode;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        if (LoginUtil.checkMobile(memberName)) {
            this.setMemberMobile(memberName);
            this.setMemberName(null);
        } else {
            this.memberName = memberName;
        }
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getMemberMobile() {
        return memberMobile;
    }

    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

}
