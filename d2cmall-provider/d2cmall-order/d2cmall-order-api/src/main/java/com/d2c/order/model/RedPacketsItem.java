package com.d2c.order.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.member.model.Account;
import com.d2c.util.date.DateUtil;
import com.d2c.util.serial.SerialNumUtil;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 红包交易记录
 */
@Table(name = "o_account_red_item")
public class RedPacketsItem extends PreUserDO {

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
     * 变动金额
     */
    private BigDecimal amount = new BigDecimal(0);
    /**
     * 状态：-1 交易取消，1等待支付，8 交易成功
     */
    private Integer status = 1;
    /**
     * 交易流水号
     */
    private String sn;
    /**
     * 业务类型
     */
    private String businessType;
    /**
     * 业务ID
     */
    private Long transactionId;
    /**
     * 业务编号
     */
    private String transactionSn;

    public RedPacketsItem() {
    }

    public RedPacketsItem(Account account) {
        this.accountId = account.getId();
        this.memberId = account.getMemberId();
        this.loginCode = account.getAccount();
        this.status = 1;
        this.sn = SerialNumUtil.buildRedpacketsSn();
        this.businessType = BusinessTypeEnum.PAY.name();
    }

    public RedPacketsItem(Account account, String type) {
        this.accountId = account.getId();
        this.memberId = account.getMemberId();
        this.loginCode = account.getAccount();
        this.status = 8;
        this.sn = SerialNumUtil.buildRedpacketsSn();
        this.businessType = type;
    }

    public RedPacketsItem(Account account, String type, Long transactionId, Integer status, String transactionSn) {
        this.accountId = account.getId();
        this.memberId = account.getMemberId();
        this.loginCode = account.getAccount();
        this.status = status;
        this.sn = SerialNumUtil.buildRedpacketsSn();
        this.businessType = type;
        this.transactionId = transactionId;
        this.transactionSn = transactionSn;
    }

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public String getStatusName() {
        if (this.status == 1) {
            return "等待支付";
        } else if (this.status == 8) {
            return "交易成功";
        } else if (this.status == -1) {
            return "交易取消";
        }
        return "";
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("createDate", DateUtil.second2str2(this.getCreateDate()));
        obj.put("amount", this.getAmount());
        obj.put("businessName", BusinessTypeEnum.valueOf(this.getBusinessType()).getDisplay());
        obj.put("statusName", this.getStatusName());
        return obj;
    }

    public enum BusinessTypeEnum {
        BARGAIN("砍价活动"), AWARD("抽奖活动"), COLLECTCARD("集卡活动"), SYSTEM("系统赠送"),
        PAY("下单支付"), REBATE("订单返利"), POINT("积分兑换"), SHARE("分享奖励"), OTHER("其他");
        private String display;

        BusinessTypeEnum(String display) {
            this.display = display;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }
    }

}
