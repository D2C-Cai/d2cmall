package com.d2c.order.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 充值卡
 */
@Table(name = "o_cashcard")
public class CashCard extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 领用会员钱包账户ID
     */
    public Long accountId;
    /**
     * 0 创建，1 已发放，2 已领用并充值
     */
    public Integer status = 0;
    /**
     * 定义ID
     */
    @AssertColumn("定义Id不能为空")
    private Long defineId;
    /**
     * 卡号
     */
    @AssertColumn("编号不能为空")
    private String code;
    /**
     * 充值兑换密码
     */
    @JsonIgnore
    @AssertColumn("密码不能为空")
    private String password;
    /**
     * 名称
     */
    @AssertColumn("名称不能为空")
    private String name;
    /**
     * 使用开始使用时间
     */
    private Date enableDate;
    /**
     * 使用结束使用时间
     */
    private Date expireDate;
    /**
     * 面额
     */
    private BigDecimal amount = new BigDecimal("0");
    /**
     * 锁定金额
     */
    private BigDecimal lockAmount = new BigDecimal("0");
    /**
     * 当时账户余额
     */
    private BigDecimal usedAmount = new BigDecimal("0");
    /**
     * 发行人
     */
    private String sendor = null;
    /**
     * 发行说明
     */
    private String sendRemark = null;
    /**
     * 发行时间
     */
    private Date sendDate = null;
    /**
     * 领用会员ID
     */
    private Long memberId;
    /**
     * 会员账号
     */
    private String loginCode;
    /**
     * 兑换激活时间
     */
    private Date claimDate;
    /**
     * 优惠券使用说明
     */
    private String remark;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDefineId() {
        return defineId;
    }

    public void setDefineId(Long defineId) {
        this.defineId = defineId;
    }

    public Date getEnableDate() {
        return enableDate;
    }

    public void setEnableDate(Date enableDate) {
        this.enableDate = enableDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getSendor() {
        return sendor;
    }

    public void setSendor(String sendor) {
        this.sendor = sendor;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(Date claimDate) {
        this.claimDate = claimDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSendRemark() {
        return sendRemark;
    }

    public void setSendRemark(String sendRemark) {
        this.sendRemark = sendRemark;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getLockAmount() {
        return lockAmount;
    }

    public void setLockAmount(BigDecimal lockAmount) {
        this.lockAmount = lockAmount;
    }

    public BigDecimal getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(BigDecimal usedAmount) {
        this.usedAmount = usedAmount;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getAvailable() {
        return this.getAmount().subtract(this.getLockAmount()).subtract(this.getUsedAmount());
    }

    public void setAvailable() {
    }

    public BigDecimal getBalance() {
        return this.getAmount().subtract(this.getUsedAmount());
    }

    public void setBalance() {
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

    public String getStatusName() {
        String name = "未知";
        if (this.getStatus() == 0) {
            name = "待发放";
        } else if (this.getStatus() == 1) {
            name = "已发放";
        } else if (this.getStatus() == 2) {
            name = "已领用";
        } else if (this.getStatus() == -1) {
            name = "已作废";
        } else if (this.getStatus() == 8) {
            name = "已核销";
        }
        return name;
    }

    public void setStatusName() {
    }

}
