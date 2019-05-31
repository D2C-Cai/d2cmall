package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;
import com.d2c.util.string.LoginUtil;

public class WithdrawCashSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 会员Id
     */
    private Long memberId;
    /**
     * 账户Id
     */
    private Long accountId;
    /**
     * 用户名
     */
    private String memberName;
    /**
     * 邮箱
     */
    private String memberEmail;
    /**
     * 手机号码
     */
    private String memberMobile;
    /**
     * 账号
     */
    private String account;
    /**
     * 持卡人
     */
    private String cardHolder;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
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

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

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

}
