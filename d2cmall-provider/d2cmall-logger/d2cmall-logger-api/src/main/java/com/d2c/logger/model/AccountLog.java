package com.d2c.logger.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 钱包日志
 */
@Table(name = "log_account")
public class AccountLog extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 账户ID
     */
    private Long accountId;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 会员账号
     */
    private String account;
    /**
     * 名称，昵称
     */
    private String name;
    /**
     * 支付密码
     */
    private String password;
    /**
     * 支付绑定手机号
     */
    private String mobile;
    /**
     * 区号
     */
    private String nationCode;
    /**
     * 备注
     */
    private String memo;
    /**
     * 身份证号码
     */
    private String cardId;
    /**
     * 收款渠道 银行 ，如果是支付宝，银行则就显示阿里
     */
    private String channel;
    /**
     * 开户行（浙江省分行）
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
     * 总金额(可兑换金额+红包总金额)
     */
    private BigDecimal totalAmount = new BigDecimal(0);
    /**
     * 可兑换金额
     */
    private BigDecimal cashAmount = new BigDecimal(0);
    /**
     * 红包总金额
     */
    private BigDecimal giftAmount = new BigDecimal(0);
    /**
     * 冻结余额
     */
    private BigDecimal freezeAmount = new BigDecimal(0);
    /**
     * 冻结红包余额
     */
    private BigDecimal freezeGiftAmount = new BigDecimal(0);
    /**
     * 总积分
     */
    private int totalPoint = 0;
    /**
     * 不可用积分，锁定积分
     */
    private int freezePoint = 0;
    /**
     * 成长值
     */
    private int growthValue = 0;
    /**
     * 级别ID
     */
    private Long levelId;
    /**
     * 1 成功，0 冻结 ，-1 不成功
     */
    private int status = 0;

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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getNationCode() {
        return nationCode;
    }

    public void setNationCode(String nationCode) {
        this.nationCode = nationCode;
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

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    public int getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(int totalPoint) {
        this.totalPoint = totalPoint;
    }

    public int getFreezePoint() {
        return freezePoint;
    }

    public void setFreezePoint(int freezePoint) {
        this.freezePoint = freezePoint;
    }

    public int getGrowthValue() {
        return growthValue;
    }

    public void setGrowthValue(int growthValue) {
        this.growthValue = growthValue;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

}
