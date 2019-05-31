package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;
import com.d2c.order.model.Exchange.ExchangeStatus;

import java.util.Date;

public class ExchangeSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 换货ID
     */
    private String id;
    /**
     * 换货编号
     */
    private String exchangeSn;
    /**
     * 换货原因
     */
    private String exchangeReason;
    /**
     * 状态
     */
    private Integer[] exchangeStatus;
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
     * 换货后发货物流
     */
    private String[] exchangeDeliverySn;
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
     * 成功状态
     */
    private Integer success;
    /**
     * 会员ID
     */
    private Long memberId;
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

    public String getExchangeSn() {
        return exchangeSn;
    }

    public void setExchangeSn(String exchangeSn) {
        this.exchangeSn = exchangeSn;
    }

    public String getExchangeReason() {
        return exchangeReason;
    }

    public void setExchangeReason(String exchangeReason) {
        this.exchangeReason = exchangeReason;
    }

    public Integer[] getExchangeStatus() {
        return exchangeStatus;
    }

    public void setExchangeStatus(Integer[] exchangeStatus) {
        this.exchangeStatus = exchangeStatus;
    }

    public String[] getDeliverySn() {
        return deliverySn;
    }

    public void setDeliverySn(String[] deliverySn) {
        this.deliverySn = deliverySn;
    }

    public String[] getExchangeDeliverySn() {
        return exchangeDeliverySn;
    }

    public void setExchangeDeliverySn(String[] exchangeDeliverySn) {
        this.exchangeDeliverySn = exchangeDeliverySn;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
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

    public void analysisStatus() {
        if (this.success != null && 1 == this.success) {
            this.exchangeStatus = new Integer[]{ExchangeStatus.DELIVERED.getCode(),
                    ExchangeStatus.SUCCESS.getCode()};
        }
    }

}
