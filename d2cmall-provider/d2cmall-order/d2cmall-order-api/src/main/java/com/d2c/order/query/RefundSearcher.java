package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;
import com.d2c.order.enums.PaymentTypeEnum;

import java.math.BigDecimal;
import java.util.Date;

public class RefundSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 退款编号
     */
    private String refundSn;
    /**
     * 退款原因
     */
    private String refundReason;
    /**
     * 状态
     */
    private Integer[] refundStatus;
    /**
     * 客户账号
     */
    private String loginCode;
    /**
     * 申请人
     */
    private String creator;
    /**
     * 审核操作人
     */
    private String auditor;
    /**
     * 付款操作人
     */
    private String payer;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品货号
     */
    private String productSn;
    /**
     * sku条码
     */
    private String skuSn;
    /**
     * 是否同意
     */
    private Boolean agreeFlag;
    /**
     * 订单明细ID
     */
    private Long orderItemId;
    /**
     * 创建开始时间
     */
    private Date beginCreateDate;
    /**
     * 创建结束时间
     */
    private Date endCreateDate;
    /**
     * 退款退货
     */
    private Integer reship;
    /**
     * 锁定
     */
    private Integer lock;
    /**
     * 客服审核开始
     */
    private Date beginAuditDate;
    /**
     * 客服审核结束
     */
    private Date endAuditDate;
    /**
     * 退款支付方式
     */
    private Integer payType;
    /**
     * 订单支付方式
     */
    private Integer orderPayType;
    /**
     * 退款确认系统时间
     */
    private Date beginCreatePayDate;
    /**
     * 退款确认系统时间
     */
    private Date endCreatePayDate;
    /**
     * 退款时间
     */
    private Date beginPayDate;
    private Date endPayDate;
    /**
     * 订单支付方式
     */
    private PaymentTypeEnum[] orderPaymentType;
    /**
     * 支付方式
     */
    private PaymentTypeEnum[] paymentType;
    /**
     * 金额最小
     */
    private BigDecimal minTotalAmount;
    /**
     * 金额最大
     */
    private BigDecimal maxTotalAmount;
    /**
     * 开始时间
     */
    private Date orderBeginDate;
    /**
     * 结束时间
     */
    private Date orderEndDate;
    /**
     * 退款流水号
     */
    private String paySn;
    /**
     * 用户ID
     */
    private Long memberId;
    /**
     * 物流类型
     */
    private Integer deliveryType;
    /**
     * 设备终端
     */
    private String device;
    /**
     * 设计师ID
     */
    private Long designerId;
    /**
     * 订单创建时间
     */
    private Date orderCreateDate;
    /**
     * 订单备注
     */
    private String orderMemo;
    /**
     * 运营小组
     */
    private String operation;
    /**
     * CRM小组
     */
    private Long[] crmGroupId;
    /**
     * 贸易类型
     */
    private String productTradeType;
    /**
     * 商品来源
     */
    private String productSource;
    /**
     * 最后操作人
     */
    private String lastModifyMan;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSn() {
        return productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    public String getSkuSn() {
        return skuSn;
    }

    public void setSkuSn(String skuSn) {
        this.skuSn = skuSn;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getRefundSn() {
        return refundSn;
    }

    public void setRefundSn(String refundSn) {
        this.refundSn = refundSn;
    }

    public Integer[] getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Integer[] refundStatus) {
        this.refundStatus = refundStatus;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Boolean getAgreeFlag() {
        return agreeFlag;
    }

    public void setAgreeFlag(Boolean agreeFlag) {
        this.agreeFlag = agreeFlag;
    }

    public Date getBeginCreateDate() {
        return beginCreateDate;
    }

    public void setBeginCreateDate(Date beginCreateDate) {
        this.beginCreateDate = beginCreateDate;
    }

    public Date getEndCreateDate() {
        return endCreateDate;
    }

    public void setEndCreateDate(Date endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

    public Integer getReship() {
        return reship;
    }

    public void setReship(Integer reship) {
        this.reship = reship;
    }

    public Integer getLock() {
        return lock;
    }

    public void setLock(Integer lock) {
        this.lock = lock;
    }

    public Date getBeginAuditDate() {
        return beginAuditDate;
    }

    public void setBeginAuditDate(Date beginAuditDate) {
        this.beginAuditDate = beginAuditDate;
    }

    public Date getEndAuditDate() {
        return endAuditDate;
    }

    public void setEndAuditDate(Date endAuditDate) {
        this.endAuditDate = endAuditDate;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getOrderPayType() {
        return orderPayType;
    }

    public void setOrderPayType(Integer orderPayType) {
        this.orderPayType = orderPayType;
    }

    public PaymentTypeEnum[] getOrderPaymentType() {
        return orderPaymentType;
    }

    public void setOrderPaymentType(PaymentTypeEnum[] orderPaymentType) {
        this.orderPaymentType = orderPaymentType;
    }

    public PaymentTypeEnum[] getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentTypeEnum[] paymentType) {
        this.paymentType = paymentType;
    }

    public Date getBeginCreatePayDate() {
        return beginCreatePayDate;
    }

    public void setBeginCreatePayDate(Date beginCreatePayDate) {
        this.beginCreatePayDate = beginCreatePayDate;
    }

    public Date getEndCreatePayDate() {
        return endCreatePayDate;
    }

    public void setEndCreatePayDate(Date endCreatePayDate) {
        this.endCreatePayDate = endCreatePayDate;
    }

    public BigDecimal getMinTotalAmount() {
        return minTotalAmount;
    }

    public void setMinTotalAmount(BigDecimal minTotalAmount) {
        this.minTotalAmount = minTotalAmount;
    }

    public BigDecimal getMaxTotalAmount() {
        return maxTotalAmount;
    }

    public void setMaxTotalAmount(BigDecimal maxTotalAmount) {
        this.maxTotalAmount = maxTotalAmount;
    }

    public Date getOrderBeginDate() {
        return orderBeginDate;
    }

    public void setOrderBeginDate(Date orderBeginDate) {
        this.orderBeginDate = orderBeginDate;
    }

    public Date getOrderEndDate() {
        return orderEndDate;
    }

    public void setOrderEndDate(Date orderEndDate) {
        this.orderEndDate = orderEndDate;
    }

    public String getPaySn() {
        return paySn;
    }

    public void setPaySn(String paySn) {
        this.paySn = paySn;
    }

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Date getBeginPayDate() {
        return beginPayDate;
    }

    public void setBeginPayDate(Date beginPayDate) {
        this.beginPayDate = beginPayDate;
    }

    public Date getEndPayDate() {
        return endPayDate;
    }

    public void setEndPayDate(Date endPayDate) {
        this.endPayDate = endPayDate;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public Date getOrderCreateDate() {
        return orderCreateDate;
    }

    public void setOrderCreateDate(Date orderCreateDate) {
        this.orderCreateDate = orderCreateDate;
    }

    public String getOrderMemo() {
        return orderMemo;
    }

    public void setOrderMemo(String orderMemo) {
        this.orderMemo = orderMemo;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Long[] getCrmGroupId() {
        return crmGroupId;
    }

    public void setCrmGroupId(Long[] crmGroupId) {
        this.crmGroupId = crmGroupId;
    }

    public String getProductTradeType() {
        return productTradeType;
    }

    public void setProductTradeType(String productTradeType) {
        this.productTradeType = productTradeType;
    }

    public String getProductSource() {
        return productSource;
    }

    public void setProductSource(String productSource) {
        this.productSource = productSource;
    }

    public String getLastModifyMan() {
        return lastModifyMan;
    }

    public void setLastModifyMan(String lastModifyMan) {
        this.lastModifyMan = lastModifyMan;
    }

    public void analysisOrderPayType() {
        if (this.orderPayType != null) {
            if (this.orderPayType == 689) {
                this.orderPaymentType = new PaymentTypeEnum[]{PaymentTypeEnum.WX_SCANPAY, PaymentTypeEnum.WXAPPPAY,
                        PaymentTypeEnum.WXPAY};
            } else {
                this.orderPaymentType = new PaymentTypeEnum[]{PaymentTypeEnum.getByCode(this.orderPayType)};
            }
        }
    }

    public void analysisPayType() {
        if (this.payType != null) {
            if (this.payType == 689) {
                this.paymentType = new PaymentTypeEnum[]{PaymentTypeEnum.WX_SCANPAY, PaymentTypeEnum.WXAPPPAY,
                        PaymentTypeEnum.WXPAY};
            } else {
                this.paymentType = new PaymentTypeEnum[]{PaymentTypeEnum.getByCode(this.payType)};
            }
        }
    }

}
