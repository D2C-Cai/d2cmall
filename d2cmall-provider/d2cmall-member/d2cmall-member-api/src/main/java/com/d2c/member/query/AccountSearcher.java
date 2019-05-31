package com.d2c.member.query;

import com.d2c.common.api.query.model.BaseQuery;
import com.d2c.util.string.LoginUtil;

import java.math.BigDecimal;
import java.util.Date;

public class AccountSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 会员编号
     */
    private Long memberId;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 会员昵称
     */
    private String memberName;
    /**
     * 会员邮箱
     */
    private String memberEmail;
    /**
     * 手机号码
     */
    private String memberMobile;
    /**
     * 钱包账号
     */
    private String account;
    /**
     * 持卡人
     */
    private String cardHolder;
    /**
     * 最低可用金额
     */
    private BigDecimal minPrice;
    /**
     * 最高可用金额
     */
    private BigDecimal maxPrice;
    /**
     * 用户类别 1普通 2经销商
     */
    private Integer memberType;
    /**
     * 赠送金额的期限
     */
    private Date limitDate;

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
        if (cardHolder != null) {
            cardHolder = cardHolder.toUpperCase();
        }
        this.cardHolder = cardHolder;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public Date getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(Date limitDate) {
        this.limitDate = limitDate;
    }

}
