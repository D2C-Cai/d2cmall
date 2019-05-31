package com.d2c.order.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.member.enums.BusinessTypeEnum;
import com.d2c.member.enums.PayTypeEnum;
import com.d2c.member.model.Account;
import com.d2c.member.support.CashCardInfo;
import com.d2c.member.support.CompensationInfo;
import com.d2c.member.support.OrderInfo;
import com.d2c.member.support.RecommendInfo;
import com.d2c.util.date.DateUtil;
import com.d2c.util.serial.SerialNumUtil;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 钱包交易记录
 */
@Table(name = "o_account_item")
public class AccountItem extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    @AssertColumn("会员ID不能为空")
    private Long memberId;
    /**
     * 上次交易ID
     */
    private Long sourceId;
    /**
     * 交易流水号
     */
    private String sn;
    /**
     * 业务ID
     */
    private Long transactionId;
    /**
     * 业务类型
     */
    private String businessType;
    /**
     * 财务类型
     */
    private String payType;
    /**
     * 业务发生时间
     */
    private Date transactionTime;
    /**
     * 业务流水号
     */
    private String transactionSn;
    /**
     * 业务信息说明
     */
    private String transactionInfo;
    /**
     * 自己账户ID
     */
    @AssertColumn("账号不能为空")
    private Long selfAccountId;
    /**
     * 自己的账户号
     */
    private String selfAccountSn;
    /**
     * 交易账户ID
     */
    private Long optAccountId;
    /**
     * 交易对方账户号
     */
    private String optAccountSn;
    /**
     * 门店ID
     */
    private Long storeId;
    /**
     * 交易备注
     */
    private String memo;
    /**
     * 变动金额
     */
    private BigDecimal amount = new BigDecimal(0);
    /**
     * 赠送变动金额
     */
    private BigDecimal giftAmount = new BigDecimal(0);
    /**
     * 1：收入，-1：支出
     */
    private Integer direction = 1;
    /**
     * 状态：1 支付成功，0 待客户支付 ，-1 取消关闭
     */
    private Integer status = 0;
    /**
     * 仅限在期限内使用 1：是，0：否
     */
    private Integer limited = 0;
    /**
     * 不退的赠送金额
     */
    private BigDecimal limitGiftAmount = new BigDecimal(0);
    /**
     * 赠送金额的期限
     */
    private Date limitDate;
    /**
     * 确认时间
     */
    private Date confirmDate;
    /**
     * 确认人
     */
    private String confirmer;
    /**
     * 关闭时间
     */
    private Date closeDate;
    /**
     * 关闭人
     */
    private String closer;
    /**
     * 关闭说明
     */
    private String closeInfo;

    public AccountItem() {
    }

    private AccountItem(Long accountId) {
        this.selfAccountId = accountId;
        this.sn = SerialNumUtil.buildPayItemSn(String.valueOf(accountId));
    }

    /**
     * 订单支付，订单退款
     *
     * @param orderInfo
     */
    public AccountItem(OrderInfo orderInfo) {
        this(orderInfo.getAccountId());
        this.setSelfAccountId(orderInfo.getAccountId());
        this.setMemberId(orderInfo.getMemberId());
        this.setBusinessType(orderInfo.getBusinessType());
        this.setPayType(orderInfo.getPayType());
        this.setTransactionId(orderInfo.getBillSourceId());
        this.setTransactionSn(orderInfo.getBillSourceSn());
        this.setTransactionTime(orderInfo.getBillSourceTime());
        this.setTransactionInfo("订单编号：" + orderInfo.getBillSourceSn());
        this.setStatus(1);
        this.setDirection(-1);
        this.setAmount(orderInfo.getCashAmount());
        this.setGiftAmount(orderInfo.getGiftAmount());
        this.setLimited(orderInfo.getLimited());
        this.setLimitGiftAmount(orderInfo.getLimitGiftAmount());
        this.setMemo(orderInfo.getMemo());
        this.setConfirmDate(new Date());
        this.setConfirmer("sys");
    }

    /**
     * 下单赔偿，售后赔偿
     *
     * @param compensationInfo
     */
    public AccountItem(CompensationInfo compensationInfo) {
        this(compensationInfo.getAccountId());
        this.setSelfAccountId(compensationInfo.getAccountId());
        this.setMemberId(compensationInfo.getMemberId());
        if (compensationInfo.getType() == 0) {
            this.setBusinessType(BusinessTypeEnum.ORDER.name());
            this.setPayType(PayTypeEnum.COMPENSATE.name());
            this.setMemo(BusinessTypeEnum.ORDER.getDisplay() + PayTypeEnum.COMPENSATE.getDisplay());
        } else if (compensationInfo.getType() == 1) {
            this.setBusinessType(BusinessTypeEnum.AFTERSALE.name());
            this.setPayType(PayTypeEnum.COMPENSATE.name());
            this.setMemo(BusinessTypeEnum.AFTERSALE.getDisplay() + PayTypeEnum.COMPENSATE.getDisplay());
        }
        this.setTransactionId(compensationInfo.getBillSourceId());
        this.setTransactionSn(compensationInfo.getBillSourceSn());
        this.setTransactionTime(new Date());
        this.setTransactionInfo("超时赔偿：" + compensationInfo.getBillSourceSn());
        this.setStatus(1);
        this.setDirection(1);
        this.setAmount(new BigDecimal(0));
        this.setGiftAmount(compensationInfo.getCompensation());
        this.setConfirmDate(new Date());
        this.setConfirmer("sys");
    }

    /**
     * 现金充值
     *
     * @param recharge
     */
    public AccountItem(Recharge recharge) {
        this(recharge.getAccountId());
        this.setCreator(
                StringUtils.isNotBlank(recharge.getSubmitor()) ? recharge.getSubmitor() : recharge.getCreator());
        this.setSelfAccountId(recharge.getAccountId());
        this.setMemberId(recharge.getMemberId());
        this.setBusinessType(BusinessTypeEnum.PAY.toString());
        this.setPayType(PayTypeEnum.RECHARGE.name());
        this.setTransactionId(recharge.getId());
        this.setTransactionSn(recharge.getSn());
        this.setTransactionTime(recharge.getPayDate());
        this.setTransactionInfo("支付流水：" + recharge.getPaySn());
        this.setStatus(0);
        this.setDirection(1);
        this.setAmount(recharge.getRechargeAmount());
        this.setGiftAmount(recharge.getGiftAmount());
        if (recharge.getLimited() > 0) {
            this.setLimited(recharge.getLimited());
            this.setLimitGiftAmount(recharge.getGiftAmount());
            this.setLimitDate(recharge.getLimitDate());
        }
        String memo = "现金充值";
        if (recharge.getRuleId() != null) {
            memo = "充值活动：" + recharge.getRuleName();
        }
        this.setMemo(memo);
    }

    /**
     * 余额提现
     *
     * @param withdrawCash
     */
    public AccountItem(WithdrawCash withdrawCash) {
        this(withdrawCash.getAccountId());
        this.setCreator(withdrawCash.getPayer());
        this.setSelfAccountId(withdrawCash.getAccountId());
        this.setMemberId(withdrawCash.getMemberId());
        this.setBusinessType(BusinessTypeEnum.PAY.toString());
        this.setPayType(PayTypeEnum.WITHDRAWCASH.name());
        this.setTransactionId(withdrawCash.getId());
        this.setTransactionSn(withdrawCash.getSn());
        this.setTransactionTime(withdrawCash.getPayDate());
        this.setTransactionInfo("支付流水：" + withdrawCash.getPaySn());
        this.setStatus(0);
        this.setDirection(-1);
        String memo = "余额提现";
        this.setMemo(memo);
    }

    /**
     * 推荐有礼
     *
     * @param recommendInfo
     */
    public AccountItem(RecommendInfo recommendInfo) {
        this(recommendInfo.getAccountId());
        this.setCreator(String.valueOf(recommendInfo.getMemberId()));
        this.setSelfAccountId(recommendInfo.getAccountId());
        this.setMemberId(recommendInfo.getRecomMemberInfoId());
        this.setBusinessType(BusinessTypeEnum.PROMOTION.toString());
        this.setPayType(PayTypeEnum.GIVE.toString());
        this.setTransactionId(recommendInfo.getMemberId());
        this.setTransactionTime(new Date());
        this.setTransactionInfo(
                recommendInfo.getMemberId() + "绑定了" + recommendInfo.getRecomMemberInfoId() + "，推荐会员送红包");
        this.setStatus(1);
        this.setDirection(1);
        this.setAmount(new BigDecimal(0));
        this.setGiftAmount(recommendInfo.getRecommendRebates());
        this.setMemo("推荐红包");
        this.setConfirmDate(new Date());
        this.setConfirmer("sys");
    }

    /**
     * D2C卡充值
     *
     * @param cashCardInfo
     */
    public AccountItem(CashCardInfo cashCardInfo) {
        this(cashCardInfo.getAccountId());
        this.setSelfAccountId(cashCardInfo.getAccountId());
        this.setMemberId(cashCardInfo.getMemberId());
        this.setBusinessType(BusinessTypeEnum.CASHCARD.toString());
        this.setPayType(PayTypeEnum.RECHARGE.name());
        this.setTransactionId(cashCardInfo.getBillSourceId());
        this.setTransactionSn(cashCardInfo.getBillSourceSn());
        this.setTransactionTime(new Date());
        this.setTransactionInfo("充值卡号：" + cashCardInfo.getBillSourceSn());
        this.setStatus(1);
        this.setDirection(1);
        this.setAmount(cashCardInfo.getCardAmount());
        this.setGiftAmount(new BigDecimal(0));
        this.setMemo("d2c卡充值");
        this.setConfirmDate(new Date());
        this.setConfirmer("sys");
    }

    /**
     * 余额清零
     *
     * @param account
     */
    public AccountItem(Account account) {
        this(account.getId());
        this.setCreator("系统");
        this.setSelfAccountId(account.getId());
        this.setMemberId(account.getMemberId());
        this.setBusinessType(BusinessTypeEnum.PAY.toString());
        this.setPayType(PayTypeEnum.CLEAN.name());
        this.setTransactionTime(new Date());
        this.setStatus(1);
        this.setDirection(-1);
        this.setAmount(new BigDecimal(0));
        this.setLimitGiftAmount(account.getLimitGiftAmount());
        if (account.getLimitGiftAmount().compareTo(new BigDecimal(0)) < 0) {
            this.setGiftAmount(new BigDecimal(0));
        }
        String memo = "充值活动到期，赠金清零";
        this.setMemo(memo);
    }

    /**
     * 分销提现
     *
     * @param partnerCash
     * @param accountId
     */
    public AccountItem(PartnerCash partnerCash, Long accountId) {
        this(accountId);
        this.setCreator(partnerCash.getCreator());
        this.setSelfAccountId(accountId);
        this.setMemberId(partnerCash.getMemberId());
        this.setBusinessType(BusinessTypeEnum.PARTNER.toString());
        this.setPayType(PayTypeEnum.RECHARGE.name());
        this.setTransactionId(partnerCash.getId());
        this.setTransactionSn(partnerCash.getSn());
        this.setTransactionTime(partnerCash.getCreateDate());
        this.setTransactionInfo("提现单号：" + partnerCash.getSn());
        this.setStatus(1);
        this.setDirection(1);
        this.setAmount(new BigDecimal(0));
        this.setGiftAmount(partnerCash.getApplyTaxAmount());
        this.setMemo("分销提现");
        this.setConfirmDate(new Date());
        this.setConfirmer("sys");
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionSn() {
        return transactionSn;
    }

    public void setTransactionSn(String transactionSn) {
        this.transactionSn = transactionSn;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getTransactionInfo() {
        return transactionInfo;
    }

    public void setTransactionInfo(String transactionInfo) {
        this.transactionInfo = transactionInfo;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Long getOptAccountId() {
        return optAccountId;
    }

    public void setOptAccountId(Long optAccountId) {
        this.optAccountId = optAccountId;
    }

    public String getOptAccountSn() {
        return optAccountSn;
    }

    public void setOptAccountSn(String optAccountSn) {
        this.optAccountSn = optAccountSn;
    }

    public Long getSelfAccountId() {
        return selfAccountId;
    }

    public void setSelfAccountId(Long selfAccountId) {
        this.selfAccountId = selfAccountId;
    }

    public String getSelfAccountSn() {
        return selfAccountSn;
    }

    public void setSelfAccountSn(String selfAccountSn) {
        this.selfAccountSn = selfAccountSn;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getGiftAmount() {
        return giftAmount;
    }

    public void setGiftAmount(BigDecimal giftAmount) {
        this.giftAmount = giftAmount;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLimited() {
        return limited;
    }

    public void setLimited(Integer limited) {
        this.limited = limited;
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

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getConfirmer() {
        return confirmer;
    }

    public void setConfirmer(String confirmer) {
        this.confirmer = confirmer;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public String getCloser() {
        return closer;
    }

    public void setCloser(String closer) {
        this.closer = closer;
    }

    public String getCloseInfo() {
        return closeInfo;
    }

    public void setCloseInfo(String closeInfo) {
        this.closeInfo = closeInfo;
    }

    public BigDecimal getSubTotal() {
        return this.getAmount().add(this.getGiftAmount());
    }

    public BigDecimal getFactTotalAmount() {
        return this.getAmount().add(this.getGiftAmount()).multiply(new BigDecimal(this.getDirection()));
    }

    public BigDecimal getFactAmount() {
        if (this.getAmount() == null) {
            return new BigDecimal(0);
        }
        return this.getAmount().multiply(new BigDecimal(this.getDirection()));
    }

    public BigDecimal getFactGiftAmount() {
        if (this.getGiftAmount() == null) {
            return new BigDecimal(0);
        }
        return this.getGiftAmount().multiply(new BigDecimal(this.getDirection()));
    }

    public BigDecimal getFactLimitGiftAmount() {
        if (this.getLimitGiftAmount() == null) {
            return new BigDecimal(0);
        }
        return this.getLimitGiftAmount().multiply(new BigDecimal(this.getDirection()));
    }

    public String getBusinessTypeName() {
        return BusinessTypeEnum.valueOf(businessType).getDisplay();
    }

    public String getPayTypeName() {
        return PayTypeEnum.valueOf(payType).getDisplay();
    }

    public String getStatusName() {
        switch (status) {
            case 0:
                return "等待支付";
            case 1:
                return "交易成功";
            case 8:
                return "已确认";
            case -1:
                return "交易关闭";
        }
        return "";
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("sn", this.getSn());
        obj.put("transactionTime", DateUtil.second2str2(this.getTransactionTime()));
        obj.put("transactionInfo", this.getTransactionInfo());
        BigDecimal zero = new BigDecimal(0);
        if (this.getDirection() == 1) {
            // 收入
            obj.put("inAmount", this.getAmount());
            obj.put("inGiftAmount", this.getGiftAmount());
            obj.put("outAmount", zero);
            obj.put("outGiftAmount", zero);
        } else {
            // 支出
            obj.put("inAmount", zero);
            obj.put("inGiftAmount", zero);
            obj.put("outAmount", this.getAmount().negate());
            obj.put("outGiftAmount", this.getGiftAmount().negate());
        }
        obj.put("limitGiftAmount", this.getLimitGiftAmount());
        obj.put("payTypeName", this.getPayTypeName());
        obj.put("businessType", this.getBusinessTypeName());
        obj.put("memo", this.getMemo());
        return obj;
    }

}
