package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;
import com.d2c.util.string.LoginUtil;

import java.math.BigDecimal;
import java.util.Date;

public class RechargeSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 充值编号
     */
    private String sn;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 支付渠道
     */
    private String payChannel;
    /**
     * 支付账号
     */
    private String payAccount;
    /**
     * 支付流水号
     */
    private String paySn;
    /**
     * 会员ID
     */
    private Long memberId;
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
     * 账户ID
     */
    private Long accountId;
    /**
     * 持卡人
     */
    private String cardHolder;
    /**
     * 门店ID
     */
    private Long storeId;
    /**
     * 充值开始时间
     */
    private Date begin;
    /**
     * 充值结束时间
     */
    private Date end;
    /**
     * 审核开始时间
     */
    private Date beginSubmitTime;
    /**
     * 审核结束时间
     */
    private Date endSubmitTime;
    /**
     * 最低充值金额
     */
    private BigDecimal minRechargeAmount;
    /**
     * 最高充值金额
     */
    private BigDecimal maxRechargeAmount;
    /**
     * 活动名称
     */
    private String ruleName;
    /**
     * 是否是门店充值 true，表示门店充值，false，非门店充值（财务充值和在线充值）
     */
    private Boolean store;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
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

    public String getPaySn() {
        return paySn;
    }

    public void setPaySn(String paySn) {
        this.paySn = paySn;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getBeginSubmitTime() {
        return beginSubmitTime;
    }

    public void setBeginSubmitTime(Date beginSubmitTime) {
        this.beginSubmitTime = beginSubmitTime;
    }

    public Date getEndSubmitTime() {
        return endSubmitTime;
    }

    public void setEndSubmitTime(Date endSubmitTime) {
        this.endSubmitTime = endSubmitTime;
    }

    public BigDecimal getMinRechargeAmount() {
        return minRechargeAmount;
    }

    public void setMinRechargeAmount(BigDecimal minRechargeAmount) {
        this.minRechargeAmount = minRechargeAmount;
    }

    public BigDecimal getMaxRechargeAmount() {
        return maxRechargeAmount;
    }

    public void setMaxRechargeAmount(BigDecimal maxRechargeAmount) {
        this.maxRechargeAmount = maxRechargeAmount;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public Boolean getStore() {
        return store;
    }

    public void setStore(Boolean store) {
        this.store = store;
    }

}
