package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.math.BigDecimal;
import java.util.Date;

public class RedPacketsItemSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 会员账号
     */
    private String loginCode;
    /**
     * 交易账户ID
     */
    private Long accountId;
    /**
     * 状态：-1 交易取消，1等待支付，8 交易成功
     */
    private Integer status;
    /**
     * 交易流水号
     */
    private String sn;
    /**
     * 业务类型
     */
    private String businessType;
    /**
     * 支出/收入
     */
    private Integer direction;
    /**
     * 业务编号
     */
    private String transactionSn;
    /**
     * 创建时间开始
     */
    private Date beginDate;
    /**
     * 创建时间结束
     */
    private Date endDate;
    /**
     * 金额大于等于
     */
    private BigDecimal beginAmount;
    /**
     * 金额小于等于
     */
    private BigDecimal endAmount;

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

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public String getTransactionSn() {
        return transactionSn;
    }

    public void setTransactionSn(String transactionSn) {
        this.transactionSn = transactionSn;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getBeginAmount() {
        return beginAmount;
    }

    public void setBeginAmount(BigDecimal beginAmount) {
        this.beginAmount = beginAmount;
    }

    public BigDecimal getEndAmount() {
        return endAmount;
    }

    public void setEndAmount(BigDecimal endAmount) {
        this.endAmount = endAmount;
    }

}
