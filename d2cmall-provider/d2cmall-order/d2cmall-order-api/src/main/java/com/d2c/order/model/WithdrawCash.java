package com.d2c.order.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.util.serial.SerialNumUtil;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 提现记录
 */
@Table(name = "o_withdraw_cash")
public class WithdrawCash extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 编号
     */
    private String sn;
    /**
     * 0 申请中，1已审核 ，8 提现成功，-1 关闭
     */
    private Integer status = 0;
    /**
     * 账单ID
     */
    private Long billId;
    /**
     * 用户ID
     */
    private Long memberId;
    /**
     * 账户ID
     */
    @AssertColumn("会员钱包ID不能为空")
    private Long accountId;
    /**
     * 提现金额
     */
    @AssertColumn(value = "提现金额不能为空", min = 0)
    private BigDecimal cashAmount = new BigDecimal(0);
    /**
     * 身份证号码
     */
    private String cardId;
    /**
     * 提现账户
     */
    @AssertColumn("提现账户不能为空")
    private String cardAccount;
    /**
     * 持卡人
     */
    private String cardHolder;
    /**
     * 支付渠道
     */
    private String payChannel;
    /**
     * 支付流水号
     */
    private String paySn;
    /**
     * 实际支付，现在默认就是他的申请金额
     */
    private BigDecimal actualPay = new BigDecimal(0);
    /**
     * 提现扣减优惠金额
     */
    private BigDecimal giftPay = new BigDecimal(0);
    /**
     * 支付人
     */
    private String payer;
    /**
     * 支付时间
     */
    private Date payDate;
    /**
     * 支付登记时间
     */
    private Date payCreateDate;
    // @Override
    // public ValidationErrors validateFields() {
    // ValidationErrors errors = new ValidationErrors();
    // if (StringUtils.isEmpty(accountId)) {
    // errors.add("01", "会员钱包ID不能为空");
    // }
    // if (StringUtils.isEmpty(cardAccount)) {
    // errors.add("02", "提现账户不能为空");
    // }
    // if (StringUtils.isEmpty(cashAmount)) {
    // errors.add("03", "提现金额不能为空");
    // }
    // return errors;
    // }

    public WithdrawCash() {
    }

    public WithdrawCash(Long accountId) {
        this.sn = SerialNumUtil.buildPayItemSn(String.valueOf(accountId));
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

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardAccount() {
        return cardAccount;
    }

    public void setCardAccount(String cardAccount) {
        this.cardAccount = cardAccount;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getPaySn() {
        return paySn;
    }

    public void setPaySn(String paySn) {
        this.paySn = paySn;
    }

    public BigDecimal getActualPay() {
        return actualPay;
    }

    public void setActualPay(BigDecimal actualPay) {
        this.actualPay = actualPay;
    }

    public BigDecimal getGiftPay() {
        return giftPay;
    }

    public void setGiftPay(BigDecimal giftPay) {
        this.giftPay = giftPay;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public Date getPayCreateDate() {
        return payCreateDate;
    }

    public void setPayCreateDate(Date payCreateDate) {
        this.payCreateDate = payCreateDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public String getStatusName() {
        switch (status) {
            case 0:
                return "申请中";
            case 1:
                return "已审核";
            case 8:
                return "提现成功";
            case -1:
                return "关闭";
            default:
                return "未知";
        }
    }

    public void setStatusName() {
    }

}
