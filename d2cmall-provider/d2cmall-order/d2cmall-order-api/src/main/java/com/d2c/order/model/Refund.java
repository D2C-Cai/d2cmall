package com.d2c.order.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.util.date.DateUtil;
import com.d2c.util.serial.SerialNumUtil;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 退款单
 */
@Table(name = "o_order_refund")
public class Refund extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 退款编号
     */
    private String refundSn;
    /**
     * 退款帐号
     */
    private String account;
    /**
     * 会员ID
     */
    @AssertColumn("会员ID不能为空")
    private Long memberId;

    ;
    /**
     * 会员名
     */
    private String memberName;
    /**
     * 会员账号
     */
    private String loginCode;
    /**
     * 订单ID
     */
    @AssertColumn("订单ID不能为空")
    private Long orderId;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 订单支付流水
     */
    private String orderPaySn;
    /**
     * 订单支付方式
     */
    private Integer orderPayType;
    /**
     * 订单的支付Id
     */
    private Long orderPaymentId;
    /**
     * 订单创建时间
     */
    private Date orderCreateDate;
    /**
     * 订单明细ID
     */
    @AssertColumn("订单明细Id不能为空")
    private Long orderItemId;
    /**
     * 商品SKU的ID
     */
    private Long productSkuId;
    /**
     * 商品名称
     */
    private String productSkuName;
    /**
     * 条码
     */
    private String productSkuSn;
    /**
     * 销售属性1
     */
    private String sp1;
    /**
     * 销售属性2
     */
    private String sp2;
    /**
     * 销售属性2
     */
    private String sp3;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 商品图片
     */
    private String productImg;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品货号
     */
    private String productSn;
    /**
     * 编码分类
     */
    private String saleCategory;
    /**
     * 贸易类型
     */
    private String productTradeType;
    /**
     * 商品来源
     */
    private String productSource;
    /**
     * 设计师是ID
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
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 售价
     */
    private BigDecimal price;
    /**
     * 实际交易金额
     */
    private BigDecimal tradeAmount;
    /**
     * 退款渠道
     */
    private Integer backAccountType = PaymentTypeEnum.ALIPAY.getCode();
    /**
     * 退款账号
     */
    private String backAccountSn;
    /**
     * 退款名称
     */
    private String backAccountName;
    /**
     * 是否全额退款 1:全额退款，-1:退差价，-2:退运费
     */
    private Integer allRefund = 1;
    /**
     * 申请金额
     */
    private BigDecimal applyAmount = new BigDecimal("0");
    /**
     * 审核金额
     */
    private BigDecimal totalAmount = new BigDecimal("0");
    /**
     * 退款理由
     */
    private String refundReason;
    /**
     * 退款状态
     */
    private Integer refundStatus;
    /**
     * 备注
     */
    private String memo;
    /**
     * 订单备注
     */
    private String orderMemo;
    /**
     * 客服备注
     */
    private String customerMemo;
    /**
     * 凭证
     */
    private String evidences;
    /**
     * 退款单号
     */
    private String paySn;
    /**
     * 退款方式
     */
    private Integer payType;
    /**
     * 退款金额
     */
    private BigDecimal payMoney;
    /**
     * 退款操作人
     */
    private String payer;
    /**
     * 退款时间
     */
    private Date payDate;
    /**
     * 关闭操作人
     */
    private String closer;
    /**
     * 关闭操作时间
     */
    private Date closeDate;
    /**
     * 审核操作人
     */
    private String auditor;
    /**
     * 审核操作时间
     */
    private Date auditDate;
    /**
     * 财务拒绝退款时间
     */
    private Date refuseDate;
    /**
     * 退款确认系统时间
     */
    private Date createPayDate;
    /**
     * 退货单Id
     */
    private Long reshipId;
    /**
     * 换货单Id
     */
    private Long exchangeId;
    /**
     * 是否代理（1 是，0 不是）
     */
    private Integer proxy = 0;
    /**
     * 是否锁定
     */
    private Integer locked = 0;
    /**
     * 设备
     */
    private String device;
    /**
     * app版本
     */
    private String appVersion;
    /**
     * 错误代码号
     */
    private String errorCode;
    /**
     * 是否无密退款 0不是，1是
     */
    private Integer autoRefund = 0;
    /**
     * 赔偿金额
     */
    private BigDecimal compensationAmount = new BigDecimal(0);
    /**
     * 钱包退款本金
     */
    private BigDecimal cashAmount;
    /**
     * 钱包退款赠金
     */
    private BigDecimal giftAmount;
    public Refund() {
    }

    public String getRefundStatusName() {
        if (this.getRefundStatus() == null) {
            return "";
        }
        RefundStatus rs = RefundStatus.getStatus(this.getRefundStatus());
        return rs.getName();
    }

    public String getRefundReasonName() {
        if (this.getRefundReason() == null) {
            return "";
        }
        switch (RefundReason.valueOf(this.getRefundReason()).code) {
            case 0:
                return "拍错了";
            case 1:
                return "不想要了";
            case 2:
                return "商品缺货";
            case 3:
                return "协商一致退款";
            case 4:
                return "商品需要维修";
            case 5:
                return "收到商品破损";
            case 6:
                return "商品错发/漏发";
            case 7:
                return "收到商品描述不符";
            case 8:
                return "商品质量问题";
            case 9:
                return "订单信息错误";
            case 10:
                return "商品价格问题";
            case 11:
                return "退货退款";
            default:
                return "";
        }
    }

    private void createRefund() {
        refundSn = SerialNumUtil.buildRefundSn();
        refundStatus = RefundStatus.APPLY.getCode();
    }

    public void createRefund(Reship reship, Integer type) {
        createRefund();
        this.setMemberId(reship.getMemberId());
        this.setMemberName(reship.getMemberName());
        this.setLoginCode(reship.getLoginCode());
        this.setOrderId(reship.getOrderId());
        this.setOrderSn(reship.getOrderSn());
        this.setOrderItemId(reship.getOrderItemId());
        this.setProductSkuId(reship.getProductSkuId());
        this.setProductSkuSn(reship.getProductSkuSn());
        this.setProductSkuName(reship.getProductName());
        this.setSp1(reship.getSp1());
        this.setSp2(reship.getSp2());
        this.setSp3(reship.getSp3());
        this.setProductId(reship.getProductId());
        this.setProductImg(reship.getProductImg());
        this.setProductName(reship.getProductName());
        this.setProductSn(reship.getProductSn());
        this.setSaleCategory(reship.getSaleCategory());
        this.setProductTradeType(reship.getProductTradeType());
        this.setProductSource(reship.getProductSource());
        this.setDesignerId(reship.getDesignerId());
        this.setDesignerCode(reship.getDesignerCode());
        this.setDesignerName(reship.getDesignerName());
        this.setPrice(reship.getPrice());
        this.setQuantity(reship.getActualQuantity());
        this.setTradeAmount(reship.getTradeAmount());
        this.setRefundReason(RefundReason.reship.name());
        this.setMemo(reship.getMemo());
        this.setOrderPayType(type);
        this.setPayType(reship.getOrderPayType());
        this.setBackAccountType(reship.getOrderPayType());
    }

    public void createRefund(Exchange exchange, Integer type) {
        createRefund();
        this.setMemberId(exchange.getMemberId());
        this.setMemberName(exchange.getMemberName());
        this.setLoginCode(exchange.getLoginCode());
        this.setOrderId(exchange.getOrderId());
        this.setOrderSn(exchange.getOrderSn());
        this.setOrderPayType(type);
        this.setOrderItemId(exchange.getOrderItemId());
        this.setProductSkuId(exchange.getOldSkuId());
        this.setProductSkuSn(exchange.getOldSkuSn());
        this.setProductSkuName(exchange.getOldProductName());
        this.setSp1(exchange.getOldSp1());
        this.setSp2(exchange.getOldSp2());
        this.setSp3(exchange.getOldSp3());
        this.setProductId(exchange.getOldProductId());
        this.setProductImg(exchange.getProductImg());
        this.setProductName(exchange.getOldProductName());
        this.setProductSn(exchange.getOldProductSn());
        this.setSaleCategory(exchange.getSaleCategory());
        this.setProductTradeType(exchange.getProductTradeType());
        this.setProductSource(exchange.getProductSource());
        this.setDesignerId(exchange.getDesignerId());
        this.setDesignerCode(exchange.getDesignerCode());
        this.setDesignerName(exchange.getDesignerName());
        this.setPrice(exchange.getPrice());
        this.setQuantity(exchange.getQuantity());
        this.setTradeAmount(exchange.getPrice());
        this.setRefundReason(RefundReason.exchange.name());
        this.setMemo(exchange.getMemo());
        this.setPayType(exchange.getOrderPayType());
        this.setBackAccountType(exchange.getOrderPayType());
    }

    public void createRefund(Order order, OrderItem orderItem) {
        createRefund();
        this.setMemberId(orderItem.getBuyerMemberId());
        this.setMemberName(orderItem.getBuyerMemberName());
        this.setCreator(order.getLoginCode());
        this.setLoginCode(order.getLoginCode());
        this.setOrderId(order.getId());
        this.setOrderSn(order.getOrderSn());
        this.setOrderPaySn(order.getPaymentSn());
        this.setOrderPayType(order.getPaymentType());
        this.setOrderPaymentId(order.getPaymentId());
        this.setOrderCreateDate(order.getCreateDate());
        this.setOrderItemId(orderItem.getId());
        this.setProductSkuId(orderItem.getProductSkuId());
        this.setProductSkuSn(orderItem.getProductSkuSn());
        this.setProductSkuName(orderItem.getProductName());
        this.setSp1(orderItem.getSp1());
        this.setSp2(orderItem.getSp2());
        this.setSp3(orderItem.getSp3());
        this.setProductId(orderItem.getProductId());
        this.setProductImg(orderItem.getProductImg());
        this.setProductName(orderItem.getProductName());
        this.setProductSn(orderItem.getProductSn());
        this.setSaleCategory(orderItem.getSaleCategory());
        this.setProductTradeType(orderItem.getProductTradeType());
        this.setProductSource(orderItem.getProductSource());
        this.setDesignerId(orderItem.getDesignerId());
        this.setDesignerCode(orderItem.getDesignerCode());
        this.setDesignerName(orderItem.getDesignerName());
        this.setPrice(orderItem.getProductPrice());
        this.setQuantity(orderItem.getProductQuantity());
        this.setTradeAmount(orderItem.getActualAmount());
        this.setOrderMemo(order.getMemo());
        this.setPayType(order.getPaymentType());
        this.setBackAccountType(order.getPaymentType());
        this.setBackAccountName(order.getPaymentSn());
    }

    public String getRefundSn() {
        return refundSn;
    }

    public void setRefundSn(String refundSn) {
        this.refundSn = refundSn;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
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

    public String getOrderPaySn() {
        return orderPaySn;
    }

    public void setOrderPaySn(String orderPaySn) {
        this.orderPaySn = orderPaySn;
    }

    public Integer getOrderPayType() {
        return orderPayType;
    }

    public void setOrderPayType(Integer orderPayType) {
        this.orderPayType = orderPayType;
    }

    public Long getOrderPaymentId() {
        return orderPaymentId;
    }

    public void setOrderPaymentId(Long orderPaymentId) {
        this.orderPaymentId = orderPaymentId;
    }

    public Date getOrderCreateDate() {
        return orderCreateDate;
    }

    public void setOrderCreateDate(Date orderCreateDate) {
        this.orderCreateDate = orderCreateDate;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Long getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(Long productSkuId) {
        this.productSkuId = productSkuId;
    }

    public String getProductSkuName() {
        return productSkuName;
    }

    public void setProductSkuName(String productSkuName) {
        this.productSkuName = productSkuName;
    }

    public String getProductSkuSn() {
        return productSkuSn;
    }

    public void setProductSkuSn(String productSkuSn) {
        this.productSkuSn = productSkuSn;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

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

    public String getSaleCategory() {
        return saleCategory;
    }

    public void setSaleCategory(String saleCategory) {
        this.saleCategory = saleCategory;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public Integer getBackAccountType() {
        return backAccountType;
    }

    public void setBackAccountType(Integer backAccountType) {
        this.backAccountType = backAccountType;
    }

    public String getBackAccountSn() {
        return backAccountSn;
    }

    public void setBackAccountSn(String backAccountSn) {
        this.backAccountSn = backAccountSn;
    }

    public String getBackAccountName() {
        return backAccountName;
    }

    public void setBackAccountName(String backAccountName) {
        this.backAccountName = backAccountName;
    }

    public Integer getAllRefund() {
        return allRefund;
    }

    public void setAllRefund(Integer allRefund) {
        this.allRefund = allRefund;
    }

    public BigDecimal getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(BigDecimal applyAmount) {
        this.applyAmount = applyAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public Integer getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getOrderMemo() {
        return orderMemo;
    }

    public void setOrderMemo(String orderMemo) {
        this.orderMemo = orderMemo;
    }

    public String getCustomerMemo() {
        return customerMemo;
    }

    public void setCustomerMemo(String customerMemo) {
        this.customerMemo = customerMemo;
    }

    public String getEvidences() {
        return evidences;
    }

    public void setEvidences(String evidences) {
        this.evidences = evidences;
    }

    public String getPaySn() {
        return paySn;
    }

    public void setPaySn(String paySn) {
        this.paySn = paySn;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public String getCloser() {
        return closer;
    }

    public void setCloser(String closer) {
        this.closer = closer;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public Date getRefuseDate() {
        return refuseDate;
    }

    public void setRefuseDate(Date refuseDate) {
        this.refuseDate = refuseDate;
    }

    public Date getCreatePayDate() {
        return createPayDate;
    }

    public void setCreatePayDate(Date createPayDate) {
        this.createPayDate = createPayDate;
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

    public Integer getProxy() {
        return proxy;
    }

    public void setProxy(Integer proxy) {
        this.proxy = proxy;
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Integer getAutoRefund() {
        return autoRefund;
    }

    public void setAutoRefund(Integer autoRefund) {
        this.autoRefund = autoRefund;
    }

    public BigDecimal getCompensationAmount() {
        return compensationAmount;
    }

    public void setCompensationAmount(BigDecimal compensationAmount) {
        this.compensationAmount = compensationAmount;
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

    public String getPayTypeName() {
        return PaymentTypeEnum.getByCode(this.getPayType()).getDisplay();
    }

    public String getOrderPayTypeName() {
        return PaymentTypeEnum.getByCode(this.getOrderPayType()).getDisplay();
    }

    public String getProductColor() {
        JSONObject obejct = JSONObject.parseObject(sp1);
        return obejct.getString("value");
    }

    public String getProductSize() {
        if (this.sp2 != null) {
            return JSONObject.parseObject(sp2).get("value").toString();
        } else {
            return "";
        }
    }

    public String[] getEvidenceList() {
        if (StringUtils.isNotBlank(evidences)) {
            return evidences.split(",");
        }
        return null;
    }

    public BigDecimal getRealAmount() {
        if (refundStatus != null && refundStatus == 8) {
            return this.getPayMoney();
        } else {
            if (totalAmount.compareTo(new BigDecimal(0)) > 0) {
                return this.getTotalAmount();
            } else {
                return this.getApplyAmount();
            }
        }
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("createDate", DateUtil.second2str2(this.getCreateDate()));
        obj.put("refundSn", this.getRefundSn());
        obj.put("orderSn", this.getOrderSn());
        obj.put("statusName", this.getRefundStatusName());
        obj.put("statusCode", this.getRefundStatus());
        obj.put("productName", this.getProductName());
        obj.put("productImg", this.getProductImg());
        obj.put("productColor", this.getProductColor());
        obj.put("productSize", this.getProductSize());
        obj.put("applyAmount", this.getApplyAmount());
        obj.put("totalAmount", this.getRealAmount());
        obj.put("backAccountName", this.getBackAccountName());
        obj.put("backAccountSn", this.getBackAccountSn());
        obj.put("refundReason", this.getRefundReasonName());
        obj.put("memo", this.getMemo());
        obj.put("quantity", this.getQuantity());
        obj.put("productPrice", this.getPrice());
        obj.put("productSource", this.getProductSource());
        obj.put("productTradeType", this.getProductTradeType());
        obj.put("evidences", this.getEvidenceList());
        obj.put("customerMemo", this.getCustomerMemo());
        obj.put("applyAmount", this.getApplyAmount());
        obj.put("refuseDate", DateUtil.second2str2(this.getRefuseDate()));
        obj.put("payDate", DateUtil.second2str2(this.getPayDate()));
        obj.put("paySn", this.getPaySn());
        obj.put("payMoney", this.getPayMoney());
        obj.put("closeDate", DateUtil.second2str2(this.getCloseDate()));
        obj.put("creator", this.getCreator());
        if (this.getPayType() != null) {
            obj.put("payType", this.getPayTypeName());
            obj.put("paymentType", PaymentTypeEnum.getByCode(this.getPayType()));
        }
        if (this.getBackAccountType() != null) {
            obj.put("backAccountType", PaymentTypeEnum.getByCode(this.getBackAccountType()));
        }
        return obj;
    }

    public enum RefundStatus {
        CREATE(0, "正在申请退款"), APPLY(1, "客服正在确认"), WAITFORPAYMENT(4, "财务正在退款"), WAITFORNOTIFY(5, "财务正在退款"),
        MEMBERCLOSE(-2, "用户取消退款"), CLOSE(-1, "商家拒绝退款"), SUCCESS(8, "退款成功");
        private static Map<Integer, RefundStatus> holder = new HashMap<>();

        static {
            for (RefundStatus status : values()) {
                holder.put(status.getCode(), status);
            }
        }

        private int code;
        private String display;

        RefundStatus(int code) {
            this.code = code;
        }

        RefundStatus(int code, String display) {
            this.code = code;
            this.display = display;
        }

        public static RefundStatus getStatus(int i) {
            return holder.get(i);
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
            switch (this.code) {
                case -1:
                    return "商家拒绝退款";
                case -2:
                    return "用户取消退款";
                case 0:
                    return "正在申请退款";
                case 1:
                    return "客服正在确认";
                case 4:
                case 5:
                    return "财务正在退款";
                case 8:
                    return "退款成功";
                default:
                    return "";
            }
        }
    }

    /**
     * 退款支付类型（预存款支付、在线支付、线下支付）
     */
    public enum RefundPaymentType {
        deposit, online, offline
    }

    /**
     * 退款理由
     */
    public static enum RefundReason {
        wrong(0), no(1), stock(2), consensus(3), repair(4), worn(5), incorrect(6),
        not(7), quality(8), error(9), price(10), reship(11), exchange(12);
        private static Map<Integer, RefundReason> holder = new HashMap<>();

        static {
            holder.put(0, RefundReason.wrong);
            holder.put(1, RefundReason.no);
            holder.put(2, RefundReason.stock);
            holder.put(3, RefundReason.consensus);
            holder.put(4, RefundReason.repair);
            holder.put(5, RefundReason.worn);
            holder.put(6, RefundReason.incorrect);
            holder.put(7, RefundReason.not);
            holder.put(8, RefundReason.quality);
            holder.put(9, RefundReason.error);
            holder.put(10, RefundReason.price);
            holder.put(11, RefundReason.reship);
            holder.put(12, RefundReason.exchange);
        }

        private int code;

        RefundReason(int code) {
            this.code = code;
        }

        public static RefundReason getStatus(int i) {
            return holder.get(i);
        }

        public int getCode() {
            return code;
        }
    }

}
