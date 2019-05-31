package com.d2c.order.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.common.base.utils.StringUt;
import com.d2c.member.model.Partner;
import com.d2c.util.serial.SerialNumUtil;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 分销提现单
 */
@Table(name = "o_partner_cash")
public class PartnerCash extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 提现单号
     */
    private String sn;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 申请ID
     */
    private Long partnerId;
    /**
     * 申请账号
     */
    private String partnerCode;
    /**
     * 申请等级
     */
    private Integer partnerLevel;
    /**
     * 申请状态 0:待审核 1:未支付 8:已支付 -1:已拒绝
     */
    private Integer status;
    /**
     * 申请金额
     */
    private BigDecimal applyAmount = new BigDecimal(0);
    /**
     * 申请金额（扣税）
     */
    private BigDecimal applyTaxAmount = new BigDecimal(0);
    /**
     * 申请真实姓名
     */
    private String applyName;
    /**
     * 申请提现账号
     */
    private String applyAccount;
    /**
     * 身份证号
     */
    private String identityCard;
    /**
     * 省市地区
     */
    private String region;
    /**
     * 支付宝账号
     */
    private String alipay;
    /**
     * 银行类型
     */
    private String bankType;
    /**
     * 银行支行
     */
    private String bank;
    /**
     * 银行卡号
     */
    private String bankSn;
    /**
     * 支付财务
     */
    private String payMan;
    /**
     * 支付时间
     */
    private Date payDate;
    /**
     * 支付方式
     */
    private String payType;
    /**
     * 支付账号
     */
    private String payAccount;
    /**
     * 支付金额
     */
    private BigDecimal payAmount = new BigDecimal(0);
    /**
     * 支付流水
     */
    private String paySn;
    /**
     * 拒绝原因
     */
    private String refuseReason;
    /**
     * 运营审核人
     */
    private String confirmMan;
    /**
     * 运营操作人
     */
    private String confirmOperateMan;
    /**
     * 累计应交税
     */
    private BigDecimal totalTaxAmount = new BigDecimal(0);
    /**
     * 计税方式
     */
    private Integer taxType;

    public PartnerCash() {
    }

    public PartnerCash(Partner partner, String payType) {
        this.status = 0;
        this.sn = SerialNumUtil.buildPatrnerCashSn();
        this.memberId = partner.getMemberId();
        this.partnerId = partner.getId();
        this.partnerCode = partner.getLoginCode();
        this.partnerLevel = partner.getLevel();
        this.identityCard = partner.getIdentityCard();
        this.payType = payType;
        this.applyName = partner.getRealName();
        if (payType != null) {
            if (payType.equals(PayType.bank.name()) || payType.equals(PayType.wallet.name())) {
                this.bank = partner.getBank();
                this.bankType = partner.getBankType();
                this.bankSn = partner.getBankSn();
                this.region = partner.getRegion();
            } else if (PayType.alipay.name().equals(payType)) {
                this.alipay = partner.getAlipay();
            }
        }
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

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public Integer getPartnerLevel() {
        return partnerLevel;
    }

    public void setPartnerLevel(Integer partnerLevel) {
        this.partnerLevel = partnerLevel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(BigDecimal applyAmount) {
        this.applyAmount = applyAmount;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public String getPaySn() {
        return paySn;
    }

    public void setPaySn(String paySn) {
        this.paySn = paySn;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public String getPayMan() {
        return payMan;
    }

    public void setPayMan(String payMan) {
        this.payMan = payMan;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public String getApplyAccount() {
        if (applyAccount != null) {
            return applyAccount;
        } else {
            return PayType.alipay.name().equals(this.getPayType()) ? this.getAlipay() : this.getBankSn();
        }
    }

    public void setApplyAccount(String applyAccount) {
        this.applyAccount = applyAccount;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public String getConfirmOperateMan() {
        return confirmOperateMan;
    }

    public void setConfirmOperateMan(String confirmOperateMan) {
        this.confirmOperateMan = confirmOperateMan;
    }

    public String getConfirmMan() {
        return confirmMan;
    }

    public void setConfirmMan(String confirmMan) {
        this.confirmMan = confirmMan;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getBankSn() {
        return bankSn;
    }

    public void setBankSn(String bankSn) {
        this.bankSn = bankSn;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public BigDecimal getApplyTaxAmount() {
        return applyTaxAmount;
    }

    public void setApplyTaxAmount(BigDecimal applyTaxAmount) {
        this.applyTaxAmount = applyTaxAmount;
    }

    public BigDecimal getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(BigDecimal totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }

    public Integer getTaxType() {
        return taxType;
    }

    public void setTaxType(Integer taxType) {
        this.taxType = taxType;
    }

    public String getStatusName() {
        switch (this.getStatus()) {
            case -1:
                return "已拒绝";
            case 0:
            case 1:
                return "未支付";
            case 8:
                return "已支付";
        }
        return "状态异常";
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("sn", this.getSn());
        obj.put("createDate", this.getCreateDate());
        obj.put("applyName", this.getApplyName() == null ? "" : StringUt.hideRealName(this.getApplyName()));
        obj.put("alipay", this.getAlipay() == null ? "" : StringUt.hideMobile(this.getAlipay()));
        obj.put("bankType", this.getBankType() == null ? "" : this.getBankType());
        obj.put("bank", this.getBank() == null ? "" : this.getBank());
        obj.put("bankSn", this.getBankSn() == null ? "" : StringUt.hideBankSn(this.getBankSn()));
        obj.put("applyAccount", this.getApplyAccount());
        obj.put("applyAmount", this.getApplyAmount());
        obj.put("applyTaxAmount", this.getApplyTaxAmount());
        obj.put("status", this.getStatus());
        obj.put("statusName", this.getStatusName());
        obj.put("payType", this.getPayType());
        obj.put("paySn", this.getPaySn());
        obj.put("payDate", this.getPayDate());
        obj.put("refuseReason", this.getRefuseReason());
        return obj;
    }

    public enum PayType {
        alipay, bank, wallet;
        private static Set<String> payTypes = null;

        static {
            payTypes = new HashSet<String>();
            for (PayType item : PayType.values()) {
                payTypes.add(item.name());
            }
        }

        public static boolean contain(String payType) {
            return payTypes.contains(payType);
        }
    }

}
