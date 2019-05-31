package com.d2c.member.support;

import com.d2c.member.enums.BusinessTypeEnum;
import com.d2c.member.enums.PayTypeEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BillInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 业务类型
     */
    private String businessType;
    /**
     * 财务类型
     */
    private String payType;
    /**
     * 支付单ID
     */
    private Long paymentId;
    /**
     * 用户ID
     */
    private Long memberId;
    /**
     * 钱包ID
     */
    private Long accountId;
    /**
     * 接收人
     */
    private Long toMemberId;
    /**
     * 备注
     */
    private String memo;
    /**
     * 单据ID
     */
    private Long billSourceId;
    /**
     * 单据编号
     */
    private String billSourceSn;
    /**
     * 单据时间
     */
    private Date billSourceTime;
    /**
     * 单据类型
     */
    private String billSourceType;
    /**
     * 单据标题
     */
    private String billSubject;
    /**
     * 单据内容
     */
    private String billBody;
    /**
     * 单据总金额
     */
    private BigDecimal billTotalFee;
    /**
     * 运费
     */
    private BigDecimal billShipFee;

    public BillInfo() {
        super();
    }

    public BillInfo(String businessType, String payType) {
        this.businessType = businessType;
        this.payType = payType;
        this.memo = BusinessTypeEnum.valueOf(businessType).getDisplay() + PayTypeEnum.valueOf(payType).getDisplay();
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
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

    public Long getToMemberId() {
        return toMemberId;
    }

    public void setToMemberId(Long toMemberId) {
        this.toMemberId = toMemberId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getBillSourceId() {
        return billSourceId;
    }

    public void setBillSourceId(Long billSourceId) {
        this.billSourceId = billSourceId;
    }

    public String getBillSourceSn() {
        return billSourceSn;
    }

    public void setBillSourceSn(String billSourceSn) {
        this.billSourceSn = billSourceSn;
    }

    public Date getBillSourceTime() {
        return billSourceTime;
    }

    public void setBillSourceTime(Date billSourceTime) {
        this.billSourceTime = billSourceTime;
    }

    public String getBillSourceType() {
        return billSourceType;
    }

    public void setBillSourceType(String billSourceType) {
        this.billSourceType = billSourceType;
    }

    public String getBillSubject() {
        return billSubject;
    }

    public void setBillSubject(String billSubject) {
        this.billSubject = billSubject;
    }

    public String getBillBody() {
        return billBody;
    }

    public void setBillBody(String billBody) {
        this.billBody = billBody;
    }

    public BigDecimal getBillTotalFee() {
        return billTotalFee;
    }

    public void setBillTotalFee(BigDecimal billTotalFee) {
        this.billTotalFee = billTotalFee;
    }

    public BigDecimal getBillShipFee() {
        return billShipFee;
    }

    public void setBillShipFee(BigDecimal billShipFee) {
        this.billShipFee = billShipFee;
    }

}
