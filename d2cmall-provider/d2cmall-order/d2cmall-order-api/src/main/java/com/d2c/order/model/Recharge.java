package com.d2c.order.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.util.date.DateUtil;
import com.d2c.util.serial.SerialNumUtil;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 充值记录
 */
@Table(name = "o_recharge")
public class Recharge extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 编号
     */
    private String sn;
    /**
     * 0未审核，1 已审核，-1关闭，-2 待支付
     */
    private Integer status = 0;
    /**
     * 充值类型
     */
    private String payType;
    /**
     * 账单ID
     */
    private Long billId;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 钱包ID
     */
    @AssertColumn("会员钱包ID不能为空")
    private Long accountId;
    /**
     * 门店ID
     */
    private Long storeId;
    /**
     * 充值金额
     */
    @AssertColumn("充值金额不能为空")
    private BigDecimal rechargeAmount = new BigDecimal(0);
    /**
     * 赠送金额
     */
    private BigDecimal giftAmount = new BigDecimal(0);
    /**
     * 充值规则ID
     */
    private Long ruleId;
    /**
     * 充值活动名称
     */
    private String ruleName;
    /**
     * 仅限在期限内使用 1：是，0：否
     */
    private Integer limited = 0;
    /**
     * 赠送金额的期限
     */
    private Date limitDate;
    /**
     * 支付渠道（付款方式）
     */
    @AssertColumn("付款方式不能为空")
    private String payChannel;
    /**
     * 支付账号
     */
    @AssertColumn("支付账号不能为空")
    private String payAccount;
    /**
     * 支付流水号
     */
    @AssertColumn("支付流水号不能为空")
    private String paySn;
    /**
     * 充值时间（转账收到的时间）
     */
    @AssertColumn("充值时间不能为空")
    private Date payDate;
    /**
     * 审核人
     */
    private String submitor;
    /**
     * 审核时间
     */
    private Date submitDate;
    /**
     * 关闭人
     */
    private String closer;
    /**
     * 关闭时间
     */
    private Date closeDate;
    /**
     * 关闭原因
     */
    private String closeReason;
    /**
     * 备注
     */
    private String memo;

    public Recharge() {
    }

    public Recharge(Long accountId, Long memberId, String creator) {
        this.sn = SerialNumUtil.buildPayItemSn(String.valueOf(accountId));
        this.accountId = accountId;
        this.memberId = memberId;
        this.creator = creator;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public BigDecimal getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(BigDecimal rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public BigDecimal getGiftAmount() {
        return giftAmount;
    }

    public void setGiftAmount(BigDecimal giftAmount) {
        this.giftAmount = giftAmount;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public Integer getLimited() {
        return limited;
    }

    public void setLimited(Integer limited) {
        this.limited = limited;
    }

    public Date getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(Date limitDate) {
        this.limitDate = limitDate;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public String getSubmitor() {
        return submitor;
    }

    public void setSubmitor(String submitor) {
        this.submitor = submitor;
    }

    public String getCloser() {
        return closer;
    }

    public void setCloser(String closer) {
        this.closer = closer;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public String getCloseReason() {
        return closeReason;
    }

    public void setCloseReason(String closeReason) {
        this.closeReason = closeReason;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayAccount() {
        if (this.getPayChannel().equals("CASH")) {
            this.payAccount = "现金";
        }
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public String getPaySn() {
        if (this.getPayChannel().equals("CASH") && StringUtils.isEmpty(paySn)) {
            this.paySn = String.valueOf(new Date().getTime());
        }
        return paySn;
    }

    public void setPaySn(String paySn) {
        this.paySn = paySn;
    }

    public BigDecimal getTotalAmount() {
        return rechargeAmount.add(giftAmount);
    }

    public void setTotalAmount(BigDecimal totalAmount) {
    }

    public String getPayChannelName() {
        if (StringUtils.isEmpty(payChannel)) {
            return "未知";
        }
        return PaymentTypeEnum.valueOf(payChannel).getDisplay();
    }

    public void setPayChannelName() {
    }

    public String getStatusName() {
        switch (status) {
            case 0:
                return "未审核";
            case 1:
                return "已审核";
            case -1:
                return "已关闭";
            case -2:
                return "待付款";
            default:
                return "未知";
        }
    }

    public void setStatusName() {
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("memberId", this.getMemberId());
        obj.put("accountId", this.getAccountId());
        obj.put("payParams", PaymentTypeEnum.ALIPAY + "," + PaymentTypeEnum.WXAPPPAY);
        obj.put("payDate", DateUtil.second2str2(this.getPayDate()));
        obj.put("payChannel", this.getPayChannel());
        obj.put("payAccount", this.getPayAccount());
        obj.put("paySn", this.getPaySn());
        obj.put("rechargeAmount", this.getRechargeAmount());
        obj.put("status", this.getStatus());
        obj.put("statusName", this.getStatusName());
        return obj;
    }

}
