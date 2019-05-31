package com.d2c.order.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.order.model.Order.OrderType;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 订单明细
 */
@Table(name = "orderitem")
public class OrderItem extends PreUserDO {

    private static final long serialVersionUID = 1L;
    // 单据信息
    /**
     * 订单ID
     */
    private Long orderId;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 订单类型
     */
    private String type = OrderType.ordinary.name();
    /**
     * 明细状态
     */
    private String status = ItemStatus.INIT.name();
    /**
     * 零售类型
     */
    private String busType = BusType.DIRECT.name();
    /**
     * 买家Id
     */
    @AssertColumn("会员ID不能为空")
    private Long buyerMemberId;
    /**
     * 买家名称
     */
    private String buyerMemberName;
    /**
     * 支付方式
     */
    private Integer paymentType;
    /**
     * 支付时间
     */
    private Date paymentTime;
    /**
     * 钱包本金分解
     */
    private BigDecimal cashAmount = new BigDecimal(0);
    /**
     * 钱包赠金分解
     */
    private BigDecimal giftAmount = new BigDecimal(0);
    /**
     * 货到付款金额分解
     */
    private BigDecimal codAmount = new BigDecimal(0);
    /**
     * 税费分解金额
     */
    private BigDecimal taxAmount = new BigDecimal(0);
    // 商品信息
    /**
     * 产品ID
     */
    @AssertColumn("商品ID不能为空")
    private Long productId;
    /**
     * 编码分类
     */
    private String saleCategory;
    /**
     * 生产类型
     */
    private String productSaleType;
    /**
     * 销售类型
     */
    private String productSellType;
    /**
     * 贸易类型
     */
    private String productTradeType;
    /**
     * 商品来源
     */
    private String productSource;
    /**
     * 商品货号
     */
    private String productSn;
    /**
     * 设计师货号
     */
    private String externalSn;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品图片
     */
    private String productImg;
    /**
     * 销售价格
     */
    @AssertColumn("商品金额不能为空")
    private BigDecimal productPrice;
    /**
     * 商品数量
     */
    @AssertColumn(value = "商品总数量应该大于0", min = 0)
    private Integer productQuantity;
    /**
     * 实发数量
     */
    private Integer deliveryQuantity;
    /**
     * 吊牌价格
     */
    private BigDecimal originalPrice;
    /**
     * 第三方货币价(成本价/汇率)
     */
    private BigDecimal thirdCcyPrice;
    /**
     * 商品说明
     */
    private String productRemark;
    /**
     * 设计师ID
     */
    private Long designerId;
    /**
     * 设计师编号
     */
    private String designerCode;
    /**
     * 设计师名称
     */
    private String designerName;
    // 条码信息
    /**
     * SKU ID
     */
    @AssertColumn("商品条码ID不能为空")
    private Long productSkuId;
    /**
     * 条码，对应 barCode
     */
    @AssertColumn("商品条码不能为空")
    private String productSkuSn;
    /**
     * SKU 名称
     */
    private String productSkuName;
    /**
     * 销售属性1
     */
    private String sp1;
    /**
     * 销售属性2
     */
    private String sp2;
    /**
     * 销售属性3
     */
    private String sp3;
    /**
     * 是否支持货到付款
     */
    private int cod = 1;
    /**
     * 是否支持售后
     */
    private int after = 1;
    /**
     * 是否支持优惠券
     */
    private Integer coupon = 1;
    /**
     * 是否包税
     */
    private int taxation = 1;
    /**
     * 是否包邮
     */
    private int shipping = 0;
    // 返利信息
    /**
     * 顶级返利ID
     */
    private Long masterId;
    /**
     * 间接返利ID
     */
    private Long superId;
    /**
     * 间接返利ID
     */
    private Long parentId;
    /**
     * 直接返利ID
     */
    private Long partnerId;
    /**
     * 顶级返利比
     */
    private BigDecimal masterRatio;
    /**
     * 间接返利比
     */
    private BigDecimal superRatio;
    /**
     * 间接返利比
     */
    private BigDecimal parentRatio;
    /**
     * 直接返利比
     */
    private BigDecimal partnerRatio;
    /**
     * 返利的形式
     */
    private String partnerStyle;
    // 优惠信息
    /**
     * 商品总计优惠金额
     */
    private BigDecimal promotionAmount = new BigDecimal(0);
    /**
     * 满减优惠分解金额
     */
    private BigDecimal orderPromotionAmount = new BigDecimal(0);
    /**
     * 优惠券分解金额
     */
    private BigDecimal couponAmount = new BigDecimal(0);
    /**
     * 红包分解金额
     */
    private BigDecimal redAmount = new BigDecimal(0);
    /**
     * 买手分解金额
     */
    private BigDecimal partnerAmount = new BigDecimal(0);
    /**
     * 商品活动ID
     */
    private Long goodPromotionId;
    /**
     * 订单活动ID
     */
    private Long orderPromotionId;
    /**
     * 组合商品ID
     */
    private Long productCombId;
    /**
     * 限时购活动ID
     */
    private Long flashPromotionId;
    /**
     * 商品活动名称
     */
    private String promotionName;
    /**
     * 订单活动名称
     */
    private String orderPromotionName;
    // 物流信息
    /**
     * 发货方式，0：D2C直发，1：设计师直发
     */
    private int deliveryType = 0;
    /**
     * 扣减库存的类型 1：扣减POP库存 0：扣减真实库存
     */
    private Integer pop = 0;
    /**
     * 调拨状态标识 -1：调拨关闭 0：未调拨 1：调拨中 2：设计师已发货 8：调拨完成
     */
    private Integer requisition = 0;
    /**
     * 拼团状态 -1：拼团失败 0：未拼团 1：拼团中 8：拼团成功
     */
    private Integer collageStatus = 0;
    /**
     * 明细发货锁定
     */
    private int locked = 0;
    /**
     * 门店ID
     */
    private Long storeId;
    /**
     * 门店调拨时间
     */
    private Date bindDate;
    /**
     * 门店发货备注
     */
    private String storeMemo;
    /**
     * 保税仓ID
     */
    private Long warehouseId;
    /**
     * 保税仓名称
     */
    private String warehouseName;
    /**
     * D+店ID
     */
    private Long dplusId;
    /**
     * D+店编号
     */
    private String dplusCode;
    /**
     * D+店名称
     */
    private String dplusName;
    /**
     * 物流时间
     */
    @Column(insertable = false)
    private Date deliveryTime;
    /**
     * 物流编号
     */
    @Column(insertable = false)
    private String deliverySn;
    /**
     * 物流公司名称
     */
    @Column(insertable = false)
    private String deliveryCorpName;
    /**
     * 真实发货条码
     */
    private String deliveryBarCode;
    /**
     * 明细发货备注
     */
    private String itemMemo;
    /**
     * 预计发货时间
     */
    private Date estimateDate;
    /**
     * 用户签收时间
     */
    @Column(insertable = false)
    private Date signDate;
    /**
     * 超时发货赔偿金
     */
    private BigDecimal compensationAmount = new BigDecimal(0);
    // 售后信息
    /**
     * 退款单ID
     */
    @Column(insertable = false)
    private Long refundId;
    /**
     * 退货单ID
     */
    @Column(insertable = false)
    private Long reshipId;
    /**
     * 换货单ID
     */
    @Column(insertable = false)
    private Long exchangeId;
    /**
     * 评价ID
     */
    @Column(insertable = false)
    private Long commentId;
    /**
     * 退差价金额
     */
    @Column(insertable = false)
    private BigDecimal diffAmount = new BigDecimal(0);
    /**
     * 预计完成时间
     */
    @Column(insertable = false)
    private Date expectDate;
    /**
     * 交易完成时间
     */
    @Column(insertable = false)
    private Date finishDate;
    // 货到付款
    /**
     * 是否结算
     */
    private int balance = 0;
    /**
     * 结算人
     */
    @Column(insertable = false)
    private String balanceMan;
    /**
     * 结算日期
     */
    @Column(insertable = false)
    private Date balanceDate;
    /**
     * 结算原因
     */
    @Column(insertable = false)
    private String balanceReason;
    /**
     * 结算金额
     */
    @Column(insertable = false)
    private BigDecimal balanceMoney;
    /**
     * 账单编号
     */
    private String billNumber;
    // 其他信息
    /**
     * 关闭原因
     */
    @Column(insertable = false)
    private String itemCloseReason;
    /**
     * 关闭人
     */
    @Column(insertable = false)
    private String itemCloseMan;
    /**
     * 关闭时间
     */
    @Column(insertable = false)
    private Date itemCloseTime;

    public OrderItem() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public Integer getPop() {
        return pop;
    }

    public void setPop(Integer pop) {
        this.pop = pop;
    }

    public Integer getRequisition() {
        return requisition;
    }

    public void setRequisition(Integer requisition) {
        this.requisition = requisition;
    }

    public Integer getCollageStatus() {
        return collageStatus;
    }

    public void setCollageStatus(Integer collageStatus) {
        this.collageStatus = collageStatus;
    }

    public Long getBuyerMemberId() {
        return buyerMemberId;
    }

    public void setBuyerMemberId(Long buyerMemberId) {
        this.buyerMemberId = buyerMemberId;
    }

    public String getBuyerMemberName() {
        return buyerMemberName;
    }

    public void setBuyerMemberName(String buyerMemberName) {
        this.buyerMemberName = buyerMemberName;
    }

    public Date getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(Date estimateDate) {
        this.estimateDate = estimateDate;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public Date getExpectDate() {
        return expectDate;
    }

    public void setExpectDate(Date expectDate) {
        this.expectDate = expectDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Long getRefundId() {
        return refundId;
    }

    public void setRefundId(Long refundId) {
        this.refundId = refundId;
    }

    public Long getReshipId() {
        return reshipId;
    }

    public void setReshipId(Long reshipId) {
        this.reshipId = reshipId;
    }

    public Long getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(Long exchangeId) {
        this.exchangeId = exchangeId;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public BigDecimal getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(BigDecimal promotionAmount) {
        this.promotionAmount = promotionAmount;
    }

    public BigDecimal getOrderPromotionAmount() {
        return orderPromotionAmount;
    }

    public void setOrderPromotionAmount(BigDecimal orderPromotionAmount) {
        this.orderPromotionAmount = orderPromotionAmount;
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

    public BigDecimal getPartnerAmount() {
        return partnerAmount;
    }

    public void setPartnerAmount(BigDecimal partnerAmount) {
        this.partnerAmount = partnerAmount;
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

    public BigDecimal getCodAmount() {
        return codAmount;
    }

    public void setCodAmount(BigDecimal codAmount) {
        this.codAmount = codAmount;
    }

    public BigDecimal getCompensationAmount() {
        return compensationAmount;
    }

    public void setCompensationAmount(BigDecimal compensationAmount) {
        this.compensationAmount = compensationAmount;
    }

    public BigDecimal getDiffAmount() {
        return diffAmount;
    }

    public void setDiffAmount(BigDecimal diffAmount) {
        this.diffAmount = diffAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Long getGoodPromotionId() {
        return goodPromotionId;
    }

    public void setGoodPromotionId(Long goodPromotionId) {
        this.goodPromotionId = goodPromotionId;
    }

    public Long getOrderPromotionId() {
        return orderPromotionId;
    }

    public void setOrderPromotionId(Long orderPromotionId) {
        this.orderPromotionId = orderPromotionId;
    }

    public Long getProductCombId() {
        return productCombId;
    }

    public void setProductCombId(Long productCombId) {
        this.productCombId = productCombId;
    }

    public Long getFlashPromotionId() {
        return flashPromotionId;
    }

    public void setFlashPromotionId(Long flashPromotionId) {
        this.flashPromotionId = flashPromotionId;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public String getOrderPromotionName() {
        return orderPromotionName;
    }

    public void setOrderPromotionName(String orderPromotionName) {
        this.orderPromotionName = orderPromotionName;
    }

    public int getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(int deliveryType) {
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

    public String getDeliveryBarCode() {
        return deliveryBarCode;
    }

    public void setDeliveryBarCode(String deliveryBarCode) {
        this.deliveryBarCode = deliveryBarCode;
    }

    public String getItemMemo() {
        return itemMemo;
    }

    public void setItemMemo(String itemMemo) {
        this.itemMemo = itemMemo;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getSaleCategory() {
        return saleCategory;
    }

    public void setSaleCategory(String saleCategory) {
        this.saleCategory = saleCategory;
    }

    public String getProductSaleType() {
        return productSaleType;
    }

    public void setProductSaleType(String productSaleType) {
        this.productSaleType = productSaleType;
    }

    public String getProductSellType() {
        return productSellType;
    }

    public void setProductSellType(String productSellType) {
        this.productSellType = productSellType;
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

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
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

    public Integer getDeliveryQuantity() {
        return deliveryQuantity;
    }

    public void setDeliveryQuantity(Integer deliveryQuantity) {
        this.deliveryQuantity = deliveryQuantity;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getProductRemark() {
        return productRemark;
    }

    public void setProductRemark(String productRemark) {
        this.productRemark = productRemark;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public String getDesignerCode() {
        return designerCode;
    }

    public void setDesignerCode(String designerCode) {
        this.designerCode = designerCode;
    }

    public String getDesignerName() {
        return designerName;
    }

    public void setDesignerName(String designerName) {
        this.designerName = designerName;
    }

    public Long getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(Long productSkuId) {
        this.productSkuId = productSkuId;
    }

    public String getProductSkuSn() {
        return productSkuSn;
    }

    public void setProductSkuSn(String productSkuSn) {
        this.productSkuSn = productSkuSn;
    }

    public String getProductSkuName() {
        return productSkuName;
    }

    public void setProductSkuName(String productSkuName) {
        this.productSkuName = productSkuName;
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

    public String getSp3() {
        return sp3;
    }

    public void setSp3(String sp3) {
        this.sp3 = sp3;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getAfter() {
        return after;
    }

    public void setAfter(int after) {
        this.after = after;
    }

    public Integer getCoupon() {
        return coupon;
    }

    public void setCoupon(Integer coupon) {
        this.coupon = coupon;
    }

    public int getTaxation() {
        return taxation;
    }

    public void setTaxation(int taxation) {
        this.taxation = taxation;
    }

    public int getShipping() {
        return shipping;
    }

    public void setShipping(int shipping) {
        this.shipping = shipping;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Date getBindDate() {
        return bindDate;
    }

    public void setBindDate(Date bindDate) {
        this.bindDate = bindDate;
    }

    public String getStoreMemo() {
        return storeMemo;
    }

    public void setStoreMemo(String storeMemo) {
        this.storeMemo = storeMemo;
    }

    public String getItemCloseReason() {
        return itemCloseReason;
    }

    public void setItemCloseReason(String itemCloseReason) {
        this.itemCloseReason = itemCloseReason;
    }

    public String getItemCloseMan() {
        return itemCloseMan;
    }

    public void setItemCloseMan(String itemCloseMan) {
        this.itemCloseMan = itemCloseMan;
    }

    public Date getItemCloseTime() {
        return itemCloseTime;
    }

    public void setItemCloseTime(Date itemCloseTime) {
        this.itemCloseTime = itemCloseTime;
    }

    public String getBalanceMan() {
        return balanceMan;
    }

    public void setBalanceMan(String balanceMan) {
        this.balanceMan = balanceMan;
    }

    public Date getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(Date balanceDate) {
        this.balanceDate = balanceDate;
    }

    public String getBalanceReason() {
        return balanceReason;
    }

    public void setBalanceReason(String balanceReason) {
        this.balanceReason = balanceReason;
    }

    public BigDecimal getBalanceMoney() {
        return balanceMoney;
    }

    public void setBalanceMoney(BigDecimal balanceMoney) {
        this.balanceMoney = balanceMoney;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getMasterId() {
        return masterId;
    }

    public void setMasterId(Long masterId) {
        this.masterId = masterId;
    }

    public Long getSuperId() {
        return superId;
    }

    public void setSuperId(Long superId) {
        this.superId = superId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public BigDecimal getMasterRatio() {
        return masterRatio;
    }

    public void setMasterRatio(BigDecimal masterRatio) {
        this.masterRatio = masterRatio;
    }

    public BigDecimal getSuperRatio() {
        return superRatio;
    }

    public void setSuperRatio(BigDecimal superRatio) {
        this.superRatio = superRatio;
    }

    public BigDecimal getParentRatio() {
        return parentRatio;
    }

    public void setParentRatio(BigDecimal parentRatio) {
        this.parentRatio = parentRatio;
    }

    public BigDecimal getPartnerRatio() {
        return partnerRatio;
    }

    public void setPartnerRatio(BigDecimal partnerRatio) {
        this.partnerRatio = partnerRatio;
    }

    public String getPartnerStyle() {
        return partnerStyle;
    }

    public void setPartnerStyle(String partnerStyle) {
        this.partnerStyle = partnerStyle;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Long getDplusId() {
        return dplusId;
    }

    public void setDplusId(Long dplusId) {
        this.dplusId = dplusId;
    }

    public String getDplusCode() {
        return dplusCode;
    }

    public void setDplusCode(String dplusCode) {
        this.dplusCode = dplusCode;
    }

    public String getDplusName() {
        return dplusName;
    }

    public void setDplusName(String dplusName) {
        this.dplusName = dplusName;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public BigDecimal getThirdCcyPrice() {
        return thirdCcyPrice;
    }

    public void setThirdCcyPrice(BigDecimal thirdCcyPrice) {
        this.thirdCcyPrice = thirdCcyPrice;
    }

    public String getSp1Value() {
        if (this.sp1 != null) {
            return JSONObject.parseObject(sp1).get("value").toString();
        } else {
            return "";
        }
    }

    public String getSp1Img() {
        if (this.sp1 != null) {
            return JSONObject.parseObject(sp1).get("img").toString();
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

    public void setOrderIdAndSn(Long orderId, String orderSn) {
        this.setOrderId(orderId);
        this.setOrderSn(orderSn);
    }

    /**
     * 商品合计
     */
    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = this.getProductPrice().multiply(new BigDecimal(this.getProductQuantity()));
        return totalPrice;
    }

    /**
     * 优惠合计
     */
    public BigDecimal getTotalPromotion() {
        if (getPromotionAmount() != null && getOrderPromotionAmount() != null && getCouponAmount() != null
                && getRedAmount() != null && getPartnerAmount() != null) {
            return getPromotionAmount().add(getOrderPromotionAmount()).add(getCouponAmount()).add(getRedAmount())
                    .add(getPartnerAmount());
        }
        return new BigDecimal(0);
    }

    /**
     * 订单明细实际支付金额
     */
    public BigDecimal getActualAmount() {
        return this.getProductPrice().multiply(new BigDecimal(this.getProductQuantity()))
                .subtract(this.getTotalPromotion()).add(this.getTaxAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 实际订单明细发货金额
     */
    public BigDecimal getActualDeliveryAmount() {
        BigDecimal ratio = new BigDecimal(this.getDeliveryQuantity()).divide(new BigDecimal(this.getProductQuantity()),
                4);
        return this.getProductPrice().multiply(new BigDecimal(this.getDeliveryQuantity()))
                .subtract(this.getTotalPromotion()).multiply(ratio).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 是否允许评论
     */
    public int isCommented() {
        ItemStatus enumStatus = ItemStatus.getItemStatusByName(this.getStatus());
        if (enumStatus.getCode() > 6) {
            if (this.getCommentId() != null) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return -1;
        }
    }

    /**
     * 货到付款 退款退货 仅退款 仅换货的入口
     *
     * @return
     */
    public Map<String, Integer> getCodAfterApply() {
        Map<String, Integer> result = new HashMap<>();
        ItemStatus enumStatus = ItemStatus.getItemStatusByName(this.getStatus());
        if (enumStatus.getCode() <= 0 || enumStatus.getCode() == 8) {
            result.put("refund", 0);
            result.put("reship", 0);
            result.put("exchange", 0);
        } else {
            result.put("refund", 1);
            result.put("reship", 1);
            // 退换货直接屏蔽不支持
            result.put("exchange", 0);
            if (enumStatus.getCode() < 7) {
                result.put("refund", 0);
                result.put("reship", 0);
                result.put("exchange", 0);
            }
        }
        return result;
    }

    /**
     * 在线支付 退款退货 仅退款 仅换货的入口
     *
     * @return
     */
    public Map<String, Integer> getOnlineAfterApply() {
        Map<String, Integer> result = new HashMap<>();
        ItemStatus enumStatus = ItemStatus.getItemStatusByName(this.getStatus());
        if (enumStatus.getCode() <= 0 || enumStatus.getCode() == 8) {
            result.put("refund", 0);
            result.put("reship", 0);
            result.put("exchange", 0);
        } else {
            result.put("refund", 1);
            result.put("reship", 1);
            // 退换货直接屏蔽不支持
            result.put("exchange", 0);
            if (enumStatus.getCode() < 2) {
                result.put("reship", 0);
                result.put("exchange", 0);
            }
        }
        return result;
    }

    /**
     * 货到付款 后台代 退款退货 仅退款 仅换货的入口
     *
     * @return
     */
    public Map<String, Integer> getCodAfterInstead() {
        Map<String, Integer> result = new HashMap<>();
        ItemStatus enumStatus = ItemStatus.getItemStatusByName(this.getStatus());
        if (enumStatus.getCode() <= 0 || enumStatus.getCode() == 8) {
            result.put("refund", 0);
            result.put("reship", 0);
            result.put("exchange", 0);
        } else {
            result.put("refund", 1);
            result.put("reship", 1);
            result.put("exchange", 1);
            if (enumStatus.getCode() < 2) {
                result.put("refund", 0);
                result.put("reship", 0);
                result.put("exchange", 0);
            }
        }
        return result;
    }

    /**
     * 在线支付 后台代 退款退货 仅退款 仅换货的入口
     *
     * @return
     */
    public Map<String, Integer> getOnlineAfterInstead() {
        Map<String, Integer> result = new HashMap<>();
        ItemStatus enumStatus = ItemStatus.getItemStatusByName(this.getStatus());
        if (enumStatus.getCode() <= 0 || enumStatus.getCode() == 8) {
            result.put("refund", 0);
            result.put("reship", 0);
            result.put("exchange", 0);
        } else {
            result.put("refund", 1);
            result.put("reship", 1);
            result.put("exchange", 1);
            if (enumStatus.getCode() < 2) {
                result.put("reship", 0);
                result.put("exchange", 0);
            }
        }
        return result;
    }

    public enum ItemStatus {
        INIT(0, "待付款"), NORMAL(1, "待发货"), DELIVERED(2, "已发货"), SIGNED(7, "已签收"),
        CLOSE(-1, "用户取消"), MALLCLOSE(-3, "超时关闭"), AFTERCLOSE(-2, "售后完成"), SUCCESS(8, "交易完成");
        private static Map<Integer, ItemStatus> holder = new HashMap<>();
        private static Map<String, ItemStatus> holderString = new HashMap<>();

        static {
            for (ItemStatus status : values()) {
                holder.put(status.getCode(), status);
                holderString.put(status.name(), status);
            }
        }

        private int code;
        private String display;

        ItemStatus(int code) {
            this.code = code;
        }

        ItemStatus(int code, String display) {
            this.code = code;
            this.display = display;
        }

        public static ItemStatus getItemStatusByName(String status) {
            return holderString.get(status);
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

        public String getName() {
            switch (code) {
                case -3:
                    return "平台取消";
                case -2:
                    return "售后完成";
                case -1:
                    return "用户取消";
                case 0:
                    return "待付款";
                case 1:
                    return "待发货";
                case 2:
                    return "已发货";
                case 7:
                    return "已签收";
                case 8:
                    return "交易完成";
                default:
                    return "未知";
            }
        }
    }

    public enum BusType {
        DIRECT("D2C直销"), DPLUS("店主零售");
        private String display;

        BusType(String display) {
            this.display = display;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }
    }

    public enum PartnerStyle {
        COMM, SELF, PASS
    }

}