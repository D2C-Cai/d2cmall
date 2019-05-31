package com.d2c.member.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.util.date.DateUtil;
import org.springframework.util.StringUtils;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 钱包
 */
@Table(name = "m_account")
public class Account extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    @AssertColumn("会员ID不能为空")
    private Long memberId;
    /**
     * 钱包账号
     */
    @AssertColumn("账号不能为空")
    private String account;
    /**
     * 支付密码
     */
    private String password;
    /**
     * 绑定手机号
     */
    private String mobile;
    /**
     * 区号
     */
    private String nationCode;
    /**
     * 会员昵称
     */
    private String name;
    /**
     * 备注
     */
    private String memo;
    /**
     * 身份证号码
     */
    private String cardId;
    /**
     * 收款渠道 银行（如果是支付宝，银行则就显示阿里）
     */
    private String channel;
    /**
     * 开户银行
     */
    private String bank;
    /**
     * 提现账户
     */
    private String cardAccount;
    /**
     * 持卡人
     */
    private String cardHolder;
    /**
     * 总金额（可兑换金额 + 赠金总金额）
     */
    private BigDecimal totalAmount = new BigDecimal(0);
    /**
     * 可兑换金额
     */
    private BigDecimal cashAmount = new BigDecimal(0);
    /**
     * 赠金总金额
     */
    private BigDecimal giftAmount = new BigDecimal(0);
    /**
     * 冻结余额
     */
    private BigDecimal freezeAmount = new BigDecimal(0);
    /**
     * 冻结赠金余额
     */
    private BigDecimal freezeGiftAmount = new BigDecimal(0);
    /**
     * 在期限内的赠送金额
     */
    private BigDecimal limitGiftAmount = new BigDecimal(0);
    /**
     * 赠送金额的期限
     */
    private Date limitDate;
    /**
     * 红包可用金额
     */
    private BigDecimal redAmount = new BigDecimal(0);
    /**
     * 红包使用期限
     */
    private Date redDate;
    /**
     * 1 成功，0 冻结 ，-1 不成功
     */
    private int status = 0;
    /**
     * 支付是否被锁定
     */
    private Boolean locked = false;
    /**
     * 最后一次尝试支付时间
     */
    private Date lastPayDate;

    public Account(Long memberInfoId, String loginCode, String nationCode) {
        this.memberId = memberInfoId;
        this.account = loginCode;
        this.name = loginCode;
        this.mobile = loginCode;
        this.nationCode = nationCode;
        this.status = 1;
    }

    public Account() {
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
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

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    public BigDecimal getGiftAmount() {
        return giftAmount;
    }

    public void setGiftAmount(BigDecimal giftAmount) {
        this.giftAmount = giftAmount;
    }

    public BigDecimal getFreezeAmount() {
        return freezeAmount;
    }

    public void setFreezeAmount(BigDecimal freezeAmount) {
        this.freezeAmount = freezeAmount;
    }

    public BigDecimal getFreezeGiftAmount() {
        return freezeGiftAmount;
    }

    public void setFreezeGiftAmount(BigDecimal freezeGiftAmount) {
        this.freezeGiftAmount = freezeGiftAmount;
    }

    public BigDecimal getLimitGiftAmount() {
        return limitGiftAmount;
    }

    public void setLimitGiftAmount(BigDecimal limitGiftAmount) {
        this.limitGiftAmount = limitGiftAmount;
    }

    public Date getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(Date limitDate) {
        this.limitDate = limitDate;
    }

    public BigDecimal getRedAmount() {
        return redAmount;
    }

    public void setRedAmount(BigDecimal redAmount) {
        this.redAmount = redAmount;
    }

    public Date getRedDate() {
        return redDate;
    }

    public void setRedDate(Date redDate) {
        this.redDate = redDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Date getLastPayDate() {
        return lastPayDate;
    }

    public void setLastPayDate(Date lastPayDate) {
        this.lastPayDate = lastPayDate;
    }

    public String getNationCode() {
        return nationCode;
    }

    public void setNationCode(String nationCode) {
        if (StringUtils.isEmpty(nationCode)) {
            nationCode = "86";
        }
        this.nationCode = nationCode;
    }

    public BigDecimal getAvailableTotalAmount() {
        return this.getTotalAmount().subtract(this.getSubFreezeAmount());
    }

    public void setAvailableTotalAmount() {
    }

    public BigDecimal getAvailableGiftAmount() {
        return this.getGiftAmount().subtract(this.getFreezeGiftAmount());
    }

    public void setAvailableGiftAmount() {
    }

    public BigDecimal getAvailableCashAmount() {
        return this.getCashAmount().subtract(this.getFreezeAmount());
    }

    public void setAvailableCashAmount() {
    }

    public BigDecimal getSubFreezeAmount() {
        return this.getFreezeAmount().add(getFreezeGiftAmount());
    }

    public void setSubFreezeAmount() {
    }

    public String getStatusName() {
        switch (this.getStatus()) {
            case 1:
                return "成功";
            case 0:
                return "冻结";
            case -1:
                return "尚未开通";
        }
        return "尚未开通";
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("mobile", this.getMobile() == null ? "" : this.getMobile());
        obj.put("totalAmount", this.getTotalAmount());
        obj.put("availableAmount", this.getAvailableTotalAmount());
        obj.put("cashAmount", this.getCashAmount());
        obj.put("giftAmount", this.getGiftAmount());
        obj.put("freezeAmount", this.getFreezeAmount());
        obj.put("redAmount", this.getRedAmount());
        obj.put("redDate", this.getRedDate() == null ? "" : DateUtil.second2str2(this.getRedDate()));
        obj.put("isRed", this.getRedDate() == null || this.getRedDate().after(new Date()));
        obj.put("setPassword", StringUtils.isEmpty(this.getPassword()) ? false : true);
        return obj;
    }

}
