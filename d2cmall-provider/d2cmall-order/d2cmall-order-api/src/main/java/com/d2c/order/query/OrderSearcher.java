package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.model.Order.OrderStatus;
import com.d2c.order.model.Order.OrderType;
import com.d2c.order.model.OrderItem.ItemStatus;
import com.d2c.util.string.LoginUtil;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

public class OrderSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 是否删除
     */
    private Integer deleted;
    /**
     * 是否拆分
     */
    private Boolean split;
    /**
     * 订单设备
     */
    private String device;
    /**
     * 订单渠道
     */
    private String terminal;
    /**
     * 订单渠道ID
     */
    private String terminalId;
    /**
     * CRM接待小组
     */
    private Long[] crmGroupId;
    /**
     * 订单类型
     */
    private OrderType[] orderType;
    /**
     * 订单状态
     */
    private OrderStatus[] orderStatus;
    /**
     * 订单状态名称
     */
    private String orderStatusName;
    /**
     * 订单总数量
     */
    private Integer orderTotalQuantity;
    /**
     * 订单总金额
     */
    private BigDecimal orderTotalAmount;
    /**
     * 订单创建开始时间
     */
    private Date startDate;
    /**
     * 订单创建结束时间
     */
    private Date endDate;
    /**
     * 最后处理时间
     */
    private Date startModifyDate;
    /**
     * 最后处理时间
     */
    private Date endModifyDate;
    /**
     * 完结开始时间
     */
    private Date finishStartDate;
    /**
     * 完结结束时间
     */
    private Date finishEndDate;
    /**
     * 支付开始时间
     */
    private Date startPayDate;
    /**
     * 支付结束时间
     */
    private Date endPayDate;
    /**
     * 支付方式
     */
    private Integer[] paymentType;
    /**
     * 支付方式
     */
    private Integer paymentCode;
    /**
     * 支付流水号
     */
    private String paymentSn;
    /**
     * 是否已开票
     */
    private Integer invoiced;
    /**
     * 货到付款拒收
     */
    private Integer refuse;
    /**
     * 订单活动名称
     */
    private String orderPromotionName;
    /**
     * 用户ID
     */
    private Long memberId;
    /**
     * 用户账号
     */
    private String loginCode;
    /**
     * 真实姓名
     */
    private String memberName;
    /**
     * 用户昵称
     */
    private String memberNickname;
    /**
     * 用户邮箱
     */
    private String memberEmail;
    /**
     * 用户手机
     */
    private String memberMobile;
    /**
     * 用户备注
     */
    private String memo;
    /**
     * 收货人
     */
    private String reciver;
    /**
     * 联系方式
     */
    private String contact;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 订单明细状态
     */
    private String[] itemStatus;
    /**
     * 运营小组
     */
    private String operation;
    /**
     * 门店ID
     */
    private Long storeId;
    /**
     * 门店编号
     */
    private String storeCode;
    /**
     * 物流方式
     */
    private Integer deliveryType;
    /**
     * 物流编号
     */
    private String deliverySn;
    /**
     * 快递账单号
     */
    private String billNumber;
    /**
     * 预计发货开始时间
     */
    private Date estimateStartDate;
    /**
     * 预计发货结束时间
     */
    private Date estimateEndDate;
    /**
     * 发货预警时间
     */
    private Long warningTime;
    /**
     * 发货开始时间
     */
    private Date deliveryTimeStart;
    /**
     * 发货结束时间
     */
    private Date deliveryTimeEnd;
    /**
     * 签收开始时间
     */
    private Date signStartDate;
    /**
     * 签收结束时间
     */
    private Date signEndDate;
    /**
     * 是否调拨
     */
    private Integer allot;
    /**
     * 调拨状态
     */
    private Integer requisition;
    /**
     * 是否评论
     */
    private Integer commented;
    /**
     * 是否结算
     */
    private Integer balance;
    /**
     * 结算标识
     */
    private Integer exportBalance;
    /**
     * 结算开始时间
     */
    private Date balanceStartDate;
    /**
     * 结算结束时间
     */
    private Date balanceEndDate;
    /**
     * 设计师 ID
     */
    private Long designerId;
    /**
     * 设计师名
     */
    private String designerName;
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
     * 商品条码
     */
    private String skuSn;
    /**
     * 明细库存大于
     */
    private Integer moreStock;
    /**
     * 顺丰有货
     */
    private Integer sf;
    /**
     * 门店有货
     */
    private Integer st;
    /**
     * 订单排序字段
     */
    private String orderByStr = "create_date DESC";
    /**
     * 明细排序字段
     */
    private String itemOrderBy = "oi.create_date DESC";
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
     * 订单中是否存在pop占单or自营占单（pop,1;自营,0）
     */
    private Integer pop;
    /**
     * 是否售后中
     */
    private Integer after;
    /**
     * 售后类型
     */
    private String afterType;
    /**
     * 生产类型 BUYOUT(买断) CONSIGN(代销) SELF(自产) COOPERATIVE(合作款)
     */
    private String productSaleType;
    /**
     * 贸易类型 COMMON(一般) CROSS(跨境)
     */
    private String productTradeType;
    /**
     * 商品来源 D2CMALL(D2C) KAOLA(考拉)
     */
    private String productSource;
    /**
     * 是否跨境 1：跨境 0：国内
     */
    private Integer crossBorder;
    /**
     * 是否礼包订单 1：是 0：否
     */
    private Integer gift;
    /**
     * 评团中
     */
    private Boolean inCollage;

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Boolean getSplit() {
        return split;
    }

    public void setSplit(Boolean split) {
        this.split = split;
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

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public Long[] getCrmGroupId() {
        return crmGroupId;
    }

    public void setCrmGroupId(Long[] crmGroupId) {
        this.crmGroupId = crmGroupId;
    }

    public OrderType[] getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType[] orderType) {
        this.orderType = orderType;
    }

    public OrderStatus[] getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus[] orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getOrderTotalQuantity() {
        return orderTotalQuantity;
    }

    public void setOrderTotalQuantity(Integer orderTotalQuantity) {
        this.orderTotalQuantity = orderTotalQuantity;
    }

    public BigDecimal getOrderTotalAmount() {
        return orderTotalAmount;
    }

    public void setOrderTotalAmount(BigDecimal orderTotalAmount) {
        this.orderTotalAmount = orderTotalAmount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartModifyDate() {
        return startModifyDate;
    }

    public void setStartModifyDate(Date startModifyDate) {
        this.startModifyDate = startModifyDate;
    }

    public Date getEndModifyDate() {
        return endModifyDate;
    }

    public void setEndModifyDate(Date endModifyDate) {
        this.endModifyDate = endModifyDate;
    }

    public Date getStartPayDate() {
        return startPayDate;
    }

    public void setStartPayDate(Date startPayDate) {
        this.startPayDate = startPayDate;
    }

    public Date getEndPayDate() {
        return endPayDate;
    }

    public void setEndPayDate(Date endPayDate) {
        this.endPayDate = endPayDate;
    }

    public Integer[] getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer[] paymentType) {
        this.paymentType = paymentType;
    }

    public Integer getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(Integer paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getPaymentSn() {
        return paymentSn;
    }

    public void setPaymentSn(String paymentSn) {
        this.paymentSn = paymentSn;
    }

    public Integer getInvoiced() {
        return invoiced;
    }

    public void setInvoiced(Integer invoiced) {
        this.invoiced = invoiced;
    }

    public Integer getRefuse() {
        return refuse;
    }

    public void setRefuse(Integer refuse) {
        this.refuse = refuse;
    }

    public String getOrderPromotionName() {
        return orderPromotionName;
    }

    public void setOrderPromotionName(String orderPromotionName) {
        this.orderPromotionName = orderPromotionName;
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

    public String getMemberNickname() {
        return memberNickname;
    }

    public void setMemberNickname(String memberNickname) {
        this.memberNickname = memberNickname;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getMemberMobile() {
        return memberMobile;
    }

    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String[] getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String[] itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getDeliverySn() {
        return deliverySn;
    }

    public void setDeliverySn(String deliverySn) {
        this.deliverySn = deliverySn;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public Date getEstimateStartDate() {
        return estimateStartDate;
    }

    public void setEstimateStartDate(Date estimateStartDate) {
        this.estimateStartDate = estimateStartDate;
    }

    public Date getEstimateEndDate() {
        return estimateEndDate;
    }

    public void setEstimateEndDate(Date estimateEndDate) {
        this.estimateEndDate = estimateEndDate;
    }

    public Long getWarningTime() {
        return warningTime;
    }

    public void setWarningTime(Long warningTime) {
        this.warningTime = warningTime;
    }

    public Date getDeliveryTimeStart() {
        return deliveryTimeStart;
    }

    public void setDeliveryTimeStart(Date deliveryTimeStart) {
        this.deliveryTimeStart = deliveryTimeStart;
    }

    public Date getDeliveryTimeEnd() {
        return deliveryTimeEnd;
    }

    public void setDeliveryTimeEnd(Date deliveryTimeEnd) {
        this.deliveryTimeEnd = deliveryTimeEnd;
    }

    public Date getSignStartDate() {
        return signStartDate;
    }

    public void setSignStartDate(Date signStartDate) {
        this.signStartDate = signStartDate;
    }

    public Date getSignEndDate() {
        return signEndDate;
    }

    public void setSignEndDate(Date signEndDate) {
        this.signEndDate = signEndDate;
    }

    public Integer getAllot() {
        return allot;
    }

    public void setAllot(Integer allot) {
        this.allot = allot;
    }

    public Integer getRequisition() {
        return requisition;
    }

    public void setRequisition(Integer requisition) {
        this.requisition = requisition;
    }

    public Integer getCommented() {
        return commented;
    }

    public void setCommented(Integer commented) {
        this.commented = commented;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getExportBalance() {
        return exportBalance;
    }

    public void setExportBalance(Integer exportBalance) {
        this.exportBalance = exportBalance;
    }

    public Date getBalanceStartDate() {
        return balanceStartDate;
    }

    public void setBalanceStartDate(Date balanceStartDate) {
        this.balanceStartDate = balanceStartDate;
    }

    public Date getBalanceEndDate() {
        return balanceEndDate;
    }

    public void setBalanceEndDate(Date balanceEndDate) {
        this.balanceEndDate = balanceEndDate;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
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

    public String getSkuSn() {
        return skuSn;
    }

    public void setSkuSn(String skuSn) {
        this.skuSn = skuSn;
    }

    public Integer getMoreStock() {
        return moreStock;
    }

    public void setMoreStock(Integer moreStock) {
        this.moreStock = moreStock;
    }

    public Integer getSf() {
        return sf;
    }

    public void setSf(Integer sf) {
        this.sf = sf;
    }

    public Integer getSt() {
        return st;
    }

    public void setSt(Integer st) {
        this.st = st;
    }

    public String getOrderByStr() {
        return orderByStr;
    }

    public void setOrderByStr(String orderByStr) {
        this.orderByStr = orderByStr;
    }

    public String getItemOrderBy() {
        return itemOrderBy;
    }

    public void setItemOrderBy(String itemOrderBy) {
        this.itemOrderBy = itemOrderBy;
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

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        if (StringUtils.isNotBlank(orderSn)) {
            orderSn = orderSn.replaceAll("\u00a0", " ");
            orderSn = orderSn.trim();
        }
        this.orderSn = orderSn;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    /**
     * 用于后台订单查询
     *
     * @param orderStatusName 表单值
     */
    public void setOrderStatusName(String orderStatusName) {
        if (StringUtils.isBlank(orderStatusName)) {
            return;
        }
        // 已付款，一般用于查询业绩
        if ("Payment".equals(orderStatusName)) {
            orderStatus = new OrderStatus[]{OrderStatus.WaitingForDelivery, OrderStatus.Delivered,
                    OrderStatus.Success};
        } else {
            orderStatus = new OrderStatus[]{OrderStatus.valueOf(orderStatusName)};
        }
        this.orderStatusName = orderStatusName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        if (LoginUtil.checkMobile(memberName)) {
            this.setMemberMobile(memberName);
            this.setMemberName(null);
        } else {
            this.memberName = memberName;
        }
    }

    public String getDesignerName() {
        return designerName;
    }

    public void setDesignerName(String designerName) {
        if (StringUtils.isNotBlank(designerName)) {
            designerName = designerName.replaceAll("\u00a0", " ");
            designerName = designerName.trim();
        }
        this.designerName = designerName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        if (StringUtils.isNotBlank(productName)) {
            productName = productName.replaceAll("\u00a0", " ");
            productName = productName.trim();
        }
        this.productName = productName;
    }

    public Integer getPop() {
        return pop;
    }

    public void setPop(Integer pop) {
        this.pop = pop;
    }

    public Integer getAfter() {
        return after;
    }

    public void setAfter(Integer after) {
        this.after = after;
    }

    public String getAfterType() {
        return afterType;
    }

    public void setAfterType(String afterType) {
        this.afterType = afterType;
    }

    public String getProductSaleType() {
        return productSaleType;
    }

    public void setProductSaleType(String productSaleType) {
        this.productSaleType = productSaleType;
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

    public Date getFinishStartDate() {
        return finishStartDate;
    }

    public void setFinishStartDate(Date finishStartDate) {
        this.finishStartDate = finishStartDate;
    }

    public Date getFinishEndDate() {
        return finishEndDate;
    }

    public void setFinishEndDate(Date finishEndDate) {
        this.finishEndDate = finishEndDate;
    }

    public Integer getCrossBorder() {
        return crossBorder;
    }

    public void setCrossBorder(Integer crossBorder) {
        this.crossBorder = crossBorder;
    }

    public Integer getGift() {
        return gift;
    }

    public void setGift(Integer gift) {
        this.gift = gift;
    }

    public Boolean getInCollage() {
        return inCollage;
    }

    public void setInCollage(Boolean inCollage) {
        this.inCollage = inCollage;
    }

    /**
     * 根据index分析订单状态 主用于APP
     *
     * @param index
     */
    public void handleIndex(Integer index) {
        if (index != null) {
            switch (index) {
                case 1:
                    // 待付款
                    orderStatus = new OrderStatus[]{OrderStatus.WaitingForPay};
                    break;
                case 2:
                    // 待发货
                    orderStatus = new OrderStatus[]{OrderStatus.WaitingForDelivery};
                    break;
                case 3:
                    // 待收货
                    orderStatus = new OrderStatus[]{OrderStatus.Delivered};
                    break;
            }
        }
    }

    /**
     * 根据index分析明细状态 主用于APP
     *
     * @param index
     */
    public void handleIndexForItem(Integer index) {
        if (index != null) {
            switch (index) {
                case 1:
                    // 待付款
                    this.itemStatus = new String[]{ItemStatus.INIT.name()};
                    break;
                case 2:
                    // 待发货
                    this.itemStatus = new String[]{ItemStatus.NORMAL.name()};
                    break;
                case 3:
                    // 待收货
                    this.itemStatus = new String[]{ItemStatus.DELIVERED.name()};
                    break;
                case 4:
                    this.itemStatus = new String[]{ItemStatus.SIGNED.name(), ItemStatus.SUCCESS.name()};
                    break;
            }
        }
    }

    /**
     * 根据index分析明细状态 主用于分销
     *
     * @param index
     */
    public void handleIndexForPartner(Integer index) {
        if (index != null) {
            switch (index) {
                case -1:
                    // 已关闭
                    this.itemStatus = new String[]{ItemStatus.CLOSE.name(), ItemStatus.MALLCLOSE.name(),
                            ItemStatus.AFTERCLOSE.name()};
                    break;
                case 1:
                    // 交易中
                    this.itemStatus = new String[]{ItemStatus.NORMAL.name(), ItemStatus.DELIVERED.name(),
                            ItemStatus.SIGNED.name()};
                    break;
                case 8:
                    // 交易完成
                    this.itemStatus = new String[]{ItemStatus.SUCCESS.name()};
                    break;
            }
        }
    }

    /**
     * 分析支付方式
     */
    public void analysisPaymentType() {
        if (this.paymentCode != null) {
            if (this.paymentCode == 689) {
                this.paymentType = new Integer[]{PaymentTypeEnum.WX_SCANPAY.getCode(),
                        PaymentTypeEnum.WXAPPPAY.getCode(), PaymentTypeEnum.WXPAY.getCode()};
            } else {
                this.paymentType = new Integer[]{PaymentTypeEnum.getByCode(paymentCode).getCode()};
            }
        }
    }

    /**
     * 根据订单状态 分析订单的排序方式
     */
    public void analyseOrderStatus() {
        if (orderStatus != null && orderStatus.length > 0) {
            switch (orderStatus[0].getCode()) {
                case 3:
                    orderByStr = "o.payment_time DESC";
                    break;
                case 4:
                    orderByStr = "o.delivery_time DESC";
                    break;
                default:
                    orderByStr = "o.create_date DESC";
            }
        }
    }

}
