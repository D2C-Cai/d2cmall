package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;
import com.d2c.order.enums.PaymentTypeEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class ReshipSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 退货编号
     */
    private String reshipSn;
    /**
     * 退货原因
     */
    private String reshipReason;
    /**
     * 状态
     */
    private Integer[] reshipStatus;
    /**
     * 登陆账号
     */
    private String loginCode;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 物流编号
     */
    private String[] deliverySn;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品货号
     */
    private String productSn;
    /**
     * 设计师货号
     */
    private String externalSn;
    /**
     * sku编号
     */
    private String skuSn;
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
     * 审核操作时间
     */
    private Date beginAuditDate;
    /**
     * 审核操作时间
     */
    private Date endAuditDate;
    /**
     * 收货操作时间
     */
    private Date beginReceiveDate;
    /**
     * 审核操作时间
     */
    private Date endReceiveDate;
    /**
     * 用户ID
     */
    private Long memberId;
    /**
     * 创建者
     */
    private String creator;
    /**
     * 物流类型
     */
    private Integer deliveryType;
    /**
     * 寄件人
     */
    private String sender;
    /**
     * 寄件人联系方式
     */
    private String mobile;
    /**
     * 设备终端
     */
    private String device;
    /**
     * 设计师ID
     */
    private Long designerId;
    /**
     * 订单支付方式
     */
    private Integer orderPayType;
    /**
     * 订单支付方式
     */
    private PaymentTypeEnum[] orderPaymentType;
    /**
     * 发货开始时间
     */
    private Date beginDeliveryTime;
    /**
     * 发货结束时间
     */
    private Date endDeliveryTime;
    /**
     * 贸易类型
     */
    private String productTradeType;
    /**
     * 商品来源
     */
    private String productSource;
    /**
     * CRM小组
     */
    private Long[] crmGroupId;
    /**
     * 运营小组
     */
    private String operation;

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

    public String getReshipSn() {
        return reshipSn;
    }

    public void setReshipSn(String reshipSn) {
        this.reshipSn = reshipSn;
    }

    public String getReshipReason() {
        return reshipReason;
    }

    public void setReshipReason(String reshipReason) {
        this.reshipReason = reshipReason;
    }

    public Integer[] getReshipStatus() {
        return reshipStatus;
    }

    public void setReshipStatus(Integer[] reshipStatus) {
        this.reshipStatus = reshipStatus;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String[] getDeliverySn() {
        return deliverySn;
    }

    public void setDeliverySn(String deliverySn) {
        if (StringUtils.isNotBlank(deliverySn)) {
            deliverySn = deliverySn.trim();
            this.deliverySn = deliverySn.split(",");
        }
    }

    public void setDeliverySn(String[] deliverySn) {
        this.deliverySn = deliverySn;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
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

    public Date getBeginReceiveDate() {
        return beginReceiveDate;
    }

    public void setBeginReceiveDate(Date beginReceiveDate) {
        this.beginReceiveDate = beginReceiveDate;
    }

    public Date getEndReceiveDate() {
        return endReceiveDate;
    }

    public void setEndReceiveDate(Date endReceiveDate) {
        this.endReceiveDate = endReceiveDate;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getExternalSn() {
        return externalSn;
    }

    public void setExternalSn(String externalSn) {
        this.externalSn = externalSn;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
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

    public Date getBeginDeliveryTime() {
        return beginDeliveryTime;
    }

    public void setBeginDeliveryTime(Date beginDeliveryTime) {
        this.beginDeliveryTime = beginDeliveryTime;
    }

    public Date getEndDeliveryTime() {
        return endDeliveryTime;
    }

    public void setEndDeliveryTime(Date endDeliveryTime) {
        this.endDeliveryTime = endDeliveryTime;
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

    public Long[] getCrmGroupId() {
        return crmGroupId;
    }

    public void setCrmGroupId(Long[] crmGroupId) {
        this.crmGroupId = crmGroupId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
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

}
