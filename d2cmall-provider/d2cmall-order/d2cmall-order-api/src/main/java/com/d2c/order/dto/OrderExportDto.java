package com.d2c.order.dto;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.HideColumn;
import com.d2c.common.api.annotation.HideEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderExportDto implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 订单状态信息
     */
    private String orderSn;
    private Date orderCreateDate;
    private Integer orderStatus;
    private String type;
    private String device;
    private String terminal;
    private Integer orderDeleted;
    private Integer split;
    private Long crmGroupId;
    private Long storeGroupId;
    private String orderCloseMan;
    private Date orderCloseTime;
    private String closeReason;
    private String adminMemo;
    private Date finishDate;
    /**
     * 订单支付信息
     */
    private Date orderPayTime;
    private Integer paymentType;
    private String paymentSn;
    private String alipaySn;
    private String mchId;
    private BigDecimal totalAmount;
    private BigDecimal promotionAmount;
    private BigDecimal couponAmount;
    private BigDecimal redAmount;
    private BigDecimal taxAmount;
    private BigDecimal shippingRates;
    private BigDecimal partnerAmount;
    /**
     * 收货地址信息
     */
    private Long orderMemberId;
    @HideColumn(type = HideEnum.MOBILE)
    private String orderLoginCode;
    private String memberMobile;
    private String memberEmail;
    private String reciver;
    @HideColumn(type = HideEnum.MOBILE)
    private String contact;
    private String province;
    private String city;
    private String district;
    private String address;
    private String memo;
    private Integer invoiced;
    /**
     * 明细状态信息
     */
    private String operation;
    private Long orderItemId;
    private String orderItemStatus;
    private BigDecimal orderitemPromotionAmount;
    private BigDecimal orderitemCoupontAmount;
    private BigDecimal orderitemRedAmount;
    private BigDecimal orderitemPartnerAmount;
    private BigDecimal orderitemTaxAmount;
    private BigDecimal orderPromotionAmount;
    private BigDecimal cashAmount;
    private BigDecimal giftAmount;
    private Long storeId;
    private Integer after;
    private Short balance;
    private Date balanceDate;
    private String balanceMan;
    private BigDecimal balanceMoney;
    private String balanceReason;
    private BigDecimal partnerRatio;
    private BigDecimal parentRatio;
    private BigDecimal superRatio;
    private BigDecimal masterRatio;
    private Long partnerId;
    private Long parentId;
    private Long superId;
    private Long masterId;
    private String dplusCode;
    private BigDecimal thirdCcyPrice;
    /**
     * 明细发货信息
     */
    private Date estimateDate;
    private Integer deliveryType;
    private Date deliveryTime;
    private String deliverySn;
    private String deliveryCorpName;
    /**
     * 明细商品信息
     */
    private String productSn;
    private String externalSn;
    private String productName;
    private BigDecimal originalCost;
    private BigDecimal productPrice;
    private Integer productQuantity;
    private String productCategory;
    private Long designerId;
    private String designerName;
    private String remark;
    /**
     * 明细条码信息
     */
    private String productSkuSn;
    private String deliveryBarCode;
    private Integer productSkuStatus;
    private String sp1;
    private String sp2;
    private Integer sfStock;
    private Integer stStock;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Date getOrderCreateDate() {
        return orderCreateDate;
    }

    public void setOrderCreateDate(Date orderCreateDate) {
        this.orderCreateDate = orderCreateDate;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public Integer getOrderDeleted() {
        return orderDeleted;
    }

    public void setOrderDeleted(Integer orderDeleted) {
        this.orderDeleted = orderDeleted;
    }

    public Integer getSplit() {
        return split;
    }

    public void setSplit(Integer split) {
        this.split = split;
    }

    public Long getCrmGroupId() {
        return crmGroupId;
    }

    public void setCrmGroupId(Long crmGroupId) {
        this.crmGroupId = crmGroupId;
    }

    public Long getStoreGroupId() {
        return storeGroupId;
    }

    public void setStoreGroupId(Long storeGroupId) {
        this.storeGroupId = storeGroupId;
    }

    public String getOrderCloseMan() {
        return orderCloseMan;
    }

    public void setOrderCloseMan(String orderCloseMan) {
        this.orderCloseMan = orderCloseMan;
    }

    public Date getOrderCloseTime() {
        return orderCloseTime;
    }

    public void setOrderCloseTime(Date orderCloseTime) {
        this.orderCloseTime = orderCloseTime;
    }

    public String getCloseReason() {
        return closeReason;
    }

    public void setCloseReason(String closeReason) {
        this.closeReason = closeReason;
    }

    public String getAdminMemo() {
        return adminMemo;
    }

    public void setAdminMemo(String adminMemo) {
        this.adminMemo = adminMemo;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Date getOrderPayTime() {
        return orderPayTime;
    }

    public void setOrderPayTime(Date orderPayTime) {
        this.orderPayTime = orderPayTime;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentSn() {
        return paymentSn;
    }

    public void setPaymentSn(String paymentSn) {
        this.paymentSn = paymentSn;
    }

    public String getAlipaySn() {
        return alipaySn;
    }

    public void setAlipaySn(String alipaySn) {
        this.alipaySn = alipaySn;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(BigDecimal promotionAmount) {
        this.promotionAmount = promotionAmount;
    }

    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }

    public BigDecimal getRedAmount() {
        return redAmount;
    }

    public void setRedAmount(BigDecimal redAmount) {
        this.redAmount = redAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getShippingRates() {
        return shippingRates;
    }

    public void setShippingRates(BigDecimal shippingRates) {
        this.shippingRates = shippingRates;
    }

    public BigDecimal getPartnerAmount() {
        return partnerAmount;
    }

    public void setPartnerAmount(BigDecimal partnerAmount) {
        this.partnerAmount = partnerAmount;
    }

    public Long getOrderMemberId() {
        return orderMemberId;
    }

    public void setOrderMemberId(Long orderMemberId) {
        this.orderMemberId = orderMemberId;
    }

    public String getOrderLoginCode() {
        return orderLoginCode;
    }

    public void setOrderLoginCode(String orderLoginCode) {
        this.orderLoginCode = orderLoginCode;
    }

    public String getMemberMobile() {
        return memberMobile;
    }

    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getReciver() {
        return reciver;
    }

    public void setReciver(String reciver) {
        this.reciver = reciver;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getInvoiced() {
        return invoiced;
    }

    public void setInvoiced(Integer invoiced) {
        this.invoiced = invoiced;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getOrderItemStatus() {
        return orderItemStatus;
    }

    public void setOrderItemStatus(String orderItemStatus) {
        this.orderItemStatus = orderItemStatus;
    }

    public BigDecimal getOrderitemPromotionAmount() {
        return orderitemPromotionAmount;
    }

    public void setOrderitemPromotionAmount(BigDecimal orderitemPromotionAmount) {
        this.orderitemPromotionAmount = orderitemPromotionAmount;
    }

    public BigDecimal getOrderitemCoupontAmount() {
        return orderitemCoupontAmount;
    }

    public void setOrderitemCoupontAmount(BigDecimal orderitemCoupontAmount) {
        this.orderitemCoupontAmount = orderitemCoupontAmount;
    }

    public BigDecimal getOrderitemRedAmount() {
        return orderitemRedAmount;
    }

    public void setOrderitemRedAmount(BigDecimal orderitemRedAmount) {
        this.orderitemRedAmount = orderitemRedAmount;
    }

    public BigDecimal getOrderitemPartnerAmount() {
        return orderitemPartnerAmount;
    }

    public void setOrderitemPartnerAmount(BigDecimal orderitemPartnerAmount) {
        this.orderitemPartnerAmount = orderitemPartnerAmount;
    }

    public BigDecimal getOrderitemTaxAmount() {
        return orderitemTaxAmount;
    }

    public void setOrderitemTaxAmount(BigDecimal orderitemTaxAmount) {
        this.orderitemTaxAmount = orderitemTaxAmount;
    }

    public BigDecimal getOrderPromotionAmount() {
        return orderPromotionAmount;
    }

    public void setOrderPromotionAmount(BigDecimal orderPromotionAmount) {
        this.orderPromotionAmount = orderPromotionAmount;
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

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Integer getAfter() {
        return after;
    }

    public void setAfter(Integer after) {
        this.after = after;
    }

    public Short getBalance() {
        return balance;
    }

    public void setBalance(Short balance) {
        this.balance = balance;
    }

    public Date getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(Date balanceDate) {
        this.balanceDate = balanceDate;
    }

    public String getBalanceMan() {
        return balanceMan;
    }

    public void setBalanceMan(String balanceMan) {
        this.balanceMan = balanceMan;
    }

    public BigDecimal getBalanceMoney() {
        return balanceMoney;
    }

    public void setBalanceMoney(BigDecimal balanceMoney) {
        this.balanceMoney = balanceMoney;
    }

    public String getBalanceReason() {
        return balanceReason;
    }

    public void setBalanceReason(String balanceReason) {
        this.balanceReason = balanceReason;
    }

    public Date getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(Date estimateDate) {
        this.estimateDate = estimateDate;
    }

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDeliverySn() {
        return deliverySn;
    }

    public void setDeliverySn(String deliverySn) {
        this.deliverySn = deliverySn;
    }

    public String getDeliveryCorpName() {
        return deliveryCorpName;
    }

    public void setDeliveryCorpName(String deliveryCorpName) {
        this.deliveryCorpName = deliveryCorpName;
    }

    public String getProductSn() {
        return productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    public String getExternalSn() {
        return externalSn;
    }

    public void setExternalSn(String externalSn) {
        this.externalSn = externalSn;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getOriginalCost() {
        return originalCost;
    }

    public void setOriginalCost(BigDecimal originalCost) {
        this.originalCost = originalCost;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public String getDesignerName() {
        return designerName;
    }

    public void setDesignerName(String designerName) {
        this.designerName = designerName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProductSkuSn() {
        return productSkuSn;
    }

    public void setProductSkuSn(String productSkuSn) {
        this.productSkuSn = productSkuSn;
    }

    public String getDeliveryBarCode() {
        return deliveryBarCode;
    }

    public void setDeliveryBarCode(String deliveryBarCode) {
        this.deliveryBarCode = deliveryBarCode;
    }

    public Integer getProductSkuStatus() {
        return productSkuStatus;
    }

    public void setProductSkuStatus(Integer productSkuStatus) {
        this.productSkuStatus = productSkuStatus;
    }

    public String getSp1() {
        return sp1;
    }

    public void setSp1(String sp1) {
        this.sp1 = sp1;
    }

    public String getSp2() {
        return sp2;
    }

    public void setSp2(String sp2) {
        this.sp2 = sp2;
    }

    public Integer getSfStock() {
        return sfStock;
    }

    public void setSfStock(Integer sfStock) {
        this.sfStock = sfStock;
    }

    public Integer getStStock() {
        return stStock;
    }

    public void setStStock(Integer stStock) {
        this.stStock = stStock;
    }

    public BigDecimal getPartnerRatio() {
        return partnerRatio;
    }

    public void setPartnerRatio(BigDecimal partnerRatio) {
        this.partnerRatio = partnerRatio;
    }

    public BigDecimal getParentRatio() {
        return parentRatio;
    }

    public void setParentRatio(BigDecimal parentRatio) {
        this.parentRatio = parentRatio;
    }

    public BigDecimal getSuperRatio() {
        return superRatio;
    }

    public void setSuperRatio(BigDecimal superRatio) {
        this.superRatio = superRatio;
    }

    public BigDecimal getMasterRatio() {
        return masterRatio;
    }

    public void setMasterRatio(BigDecimal masterRatio) {
        this.masterRatio = masterRatio;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getSuperId() {
        return superId;
    }

    public void setSuperId(Long superId) {
        this.superId = superId;
    }

    public Long getMasterId() {
        return masterId;
    }

    public void setMasterId(Long masterId) {
        this.masterId = masterId;
    }

    public String getDplusCode() {
        return dplusCode;
    }

    public void setDplusCode(String dplusCode) {
        this.dplusCode = dplusCode;
    }

    public BigDecimal getThirdCcyPrice() {
        return thirdCcyPrice;
    }

    public void setThirdCcyPrice(BigDecimal thirdCcyPrice) {
        this.thirdCcyPrice = thirdCcyPrice;
    }

    public String getProvince() {
        String province1 = province;
        if (province1 != null) {
            if (province1.equals("北京市")) {
                province1 = "北京";
            } else if (province1.equals("天津市")) {
                province1 = "天津";
            } else if (province1.equals("上海市")) {
                province1 = "上海";
            } else if (province1.equals("重庆市")) {
                province1 = "重庆";
            }
        }
        return province1;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        if (province != null && city != null) {
            if (province.equals("北京市") || province.equals("北京")) {
                city = "北京市";
            } else if (province.equals("天津市") || province.equals("天津")) {
                city = "天津市";
            } else if (province.equals("上海市") || province.equals("上海")) {
                city = "上海市";
            } else if (province.equals("重庆市") || province.equals("重庆")) {
                city = "重庆市";
            }
        }
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSp1Value() {
        if (this.sp1 != null) {
            return JSONObject.parseObject(sp1).get("value").toString();
        } else {
            return "";
        }
    }

    public String getSp2Value() {
        if (this.sp2 != null) {
            return JSONObject.parseObject(sp2).get("value").toString();
        } else {
            return "";
        }
    }

    /**
     * 明细实付金额
     */
    public BigDecimal getActualAmount() {
        return this.getProductPrice().multiply(new BigDecimal(this.getProductQuantity()))
                .subtract(this.getOrderitemPromotionAmount()).subtract(this.getOrderPromotionAmount())
                .subtract(this.getOrderitemCoupontAmount()).subtract(this.getOrderitemRedAmount())
                .subtract(this.getOrderitemPartnerAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 总的订单应付金额
     */
    public BigDecimal getOrderTotalPay() {
        BigDecimal totalPay = this.getTotalAmount().add(this.getShippingRates()).subtract(this.getCouponAmount())
                .subtract(this.getRedAmount()).subtract(this.getPartnerAmount());
        return totalPay;
    }

}
