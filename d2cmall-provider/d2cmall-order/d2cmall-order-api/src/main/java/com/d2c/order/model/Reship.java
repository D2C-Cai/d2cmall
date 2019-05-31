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
 * 退货单
 */
@Table(name = "o_order_reship")
public class Reship extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 退货编号
     */
    private String reshipSn;
    /**
     * 对应的退款单, 当用户直接发货给仓库, 仓库做入库的时候, 退款为空
     */
    private Long refundId;
    /**
     * 退货状态
     */
    private Integer reshipStatus;
    /**
     * 退货理由
     */
    private String reshipReason;
    /**
     * 物流编号
     */
    private String deliverySn;
    /**
     * 物流费用
     */
    private BigDecimal deliveryFee;
    /**
     * 物流公司名称
     */
    private String deliveryCorpName;
    /**
     * 用户说明
     */
    private String memo;
    /**
     * 客服备注
     */
    private String customerMemo;
    /**
     * 凭证
     */
    private String evidences;
    /**
     * SKU的Id
     */
    @AssertColumn("商品条码ID不能为空")
    private Long productSkuId;
    /**
     * 条码
     */
    private String productSkuSn;
    /**
     * 商品ID
     */
    @AssertColumn("商品ID不能为空")
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
     * 设计师货号
     */
    private String externalSn;
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
    /**
     * 会员ID
     */
    @AssertColumn("会员ID不能为空")
    private Long memberId;
    /**
     * 会员名
     */
    private String memberName;
    /**
     * 会员登录号
     */
    private String loginCode;
    /**
     * 下单数量
     */
    private Integer quantity;
    /**
     * 实发数量
     */
    private Integer deliveryQuantity;
    /**
     * 实退数量
     */
    private Integer actualQuantity;
    /**
     * 售价
     */
    private BigDecimal price;
    /**
     * 实际交易金额
     */
    private BigDecimal tradeAmount;
    /**
     * 是否已收货
     */
    private boolean received = true;
    /**
     * 对于订单 Id
     */
    @AssertColumn("订单ID不能为空")
    private Long orderId;
    /**
     * 对于订单 编号
     */
    private String orderSn;
    /**
     * 支付方式
     */
    private Integer orderPayType;
    /**
     * 明细ID
     */
    @AssertColumn("订单明细ID不能为空")
    private Long orderItemId;
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
     * 收货操作人
     */
    private String receiver;
    /**
     * 收货操作时间
     */
    private Date receiveDate;
    /**
     * 拒绝收货人
     */
    private String refuseReceiver;
    /**
     * 拒绝收货时间
     */
    private Date refuseReceiveDate;
    /**
     * 用户发货截止时间
     */
    private Date deliveryExpiredDate;
    /**
     * 是否代理（1 是，0 不是）
     */
    private Integer proxy = 0;
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
     * 退货地址
     */
    private String backAddress;
    private String backMobile;
    private String backConsignee;
    /**
     * 设备
     */
    private String device;
    /**
     * app版本
     */
    private String appVersion;
    /**
     * 寄件人姓名
     */
    private String sender;
    /**
     * 寄件人联系电话
     */
    private String mobile;
    public Reship() {
    }

    public String getReshipReasonName() {
        if (this.getReshipReason() == null) {
            return "";
        }
        ReshipReason reason = ReshipReason.getStatusByName(this.getReshipReason());
        if (reason == null) {
            return "";
        }
        switch (reason.getCode()) {
            case 0:
                return "商品需要维修";
            case 1:
                return "收到商品破损";
            case 2:
                return "商品错发或漏发";
            case 3:
                return "收到商品与描述不符";
            case 4:
                return "商品质量问题";
            case 7:
                return "七天无理由退货";
            default:
                return "";
        }
    }

    public void createReship() {
        reshipSn = SerialNumUtil.buildReshipSn();
        reshipStatus = ReshipStatus.APPLY.getCode();
    }

    public void createReship(Order order, OrderItem orderItem) {
        this.createReship();
        this.setProductId(orderItem.getProductId());
        this.setProductImg(orderItem.getProductImg());
        this.setProductName(orderItem.getProductName());
        this.setProductSn(orderItem.getProductSn());
        this.setExternalSn(orderItem.getExternalSn());
        this.setSaleCategory(orderItem.getSaleCategory());
        this.setProductTradeType(orderItem.getProductTradeType());
        this.setProductSource(orderItem.getProductSource());
        this.setProductSkuId(orderItem.getProductSkuId());
        this.setProductSkuSn(orderItem.getDeliveryBarCode());
        this.setSp1(orderItem.getSp1());
        this.setSp2(orderItem.getSp2());
        this.setSp3(orderItem.getSp3());
        this.setDesignerId(orderItem.getDesignerId());
        this.setDesignerCode(orderItem.getDesignerCode());
        this.setDesignerName(orderItem.getDesignerName());
        this.setMemberId(orderItem.getBuyerMemberId());
        this.setMemberName(orderItem.getBuyerMemberName());
        this.setCreator(order.getLoginCode());
        this.setLoginCode(order.getLoginCode());
        this.setOrderId(order.getId());
        this.setOrderSn(order.getOrderSn());
        this.setOrderPayType(order.getPaymentType());
        this.setOrderItemId(orderItem.getId());
        this.setPrice(orderItem.getProductPrice());
        this.setQuantity(orderItem.getProductQuantity());
        this.setDeliveryQuantity(orderItem.getDeliveryQuantity());
        this.setSender(order.getReciver());
        this.setMobile(order.getContact());
        if (this.getActualQuantity() != null) {
            this.setTradeAmount(orderItem.getActualAmount().multiply(new BigDecimal(this.getActualQuantity()))
                    .divide(new BigDecimal(orderItem.getProductQuantity()), 2, BigDecimal.ROUND_HALF_UP));
        } else {
            this.setActualQuantity(orderItem.getProductQuantity());
            this.setTradeAmount(orderItem.getActualAmount());
        }
    }

    public String getReshipSn() {
        return reshipSn;
    }

    public void setReshipSn(String reshipSn) {
        this.reshipSn = reshipSn;
    }

    public Long getRefundId() {
        return refundId;
    }

    public void setRefundId(Long refundId) {
        this.refundId = refundId;
    }

    public Integer getReshipStatus() {
        return reshipStatus;
    }

    public void setReshipStatus(Integer reshipStatus) {
        this.reshipStatus = reshipStatus;
    }

    public String getReshipReason() {
        return reshipReason;
    }

    public void setReshipReason(String reshipReason) {
        this.reshipReason = reshipReason;
    }

    public String getDeliverySn() {
        return deliverySn;
    }

    public void setDeliverySn(String deliverySn) {
        this.deliverySn = deliverySn;
    }

    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(BigDecimal deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getDeliveryCorpName() {
        return deliveryCorpName;
    }

    public void setDeliveryCorpName(String deliveryCorpName) {
        this.deliveryCorpName = deliveryCorpName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

    public String getExternalSn() {
        return externalSn;
    }

    public void setExternalSn(String externalSn) {
        this.externalSn = externalSn;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getDeliveryQuantity() {
        return deliveryQuantity;
    }

    public void setDeliveryQuantity(Integer deliveryQuantity) {
        this.deliveryQuantity = deliveryQuantity;
    }

    public Integer getActualQuantity() {
        return actualQuantity;
    }

    public void setActualQuantity(Integer actualQuantity) {
        this.actualQuantity = actualQuantity;
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

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
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

    public Integer getOrderPayType() {
        return orderPayType;
    }

    public void setOrderPayType(Integer orderPayType) {
        this.orderPayType = orderPayType;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
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

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getRefuseReceiver() {
        return refuseReceiver;
    }

    public void setRefuseReceiver(String refuseReceiver) {
        this.refuseReceiver = refuseReceiver;
    }

    public Date getRefuseReceiveDate() {
        return refuseReceiveDate;
    }

    public void setRefuseReceiveDate(Date refuseReceiveDate) {
        this.refuseReceiveDate = refuseReceiveDate;
    }

    public Date getDeliveryExpiredDate() {
        return deliveryExpiredDate;
    }

    public void setDeliveryExpiredDate(Date deliveryExpiredDate) {
        this.deliveryExpiredDate = deliveryExpiredDate;
    }

    public Integer getProxy() {
        return proxy;
    }

    public void setProxy(Integer proxy) {
        this.proxy = proxy;
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

    public String getBackAddress() {
        return backAddress;
    }

    public void setBackAddress(String backAddress) {
        this.backAddress = backAddress;
    }

    public String getBackMobile() {
        return backMobile;
    }

    public void setBackMobile(String backMobile) {
        this.backMobile = backMobile;
    }

    public String getBackConsignee() {
        return backConsignee;
    }

    public void setBackConsignee(String backConsignee) {
        this.backConsignee = backConsignee;
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

    public String getReshipStatusName() {
        if (this.getReshipStatus() == null) {
            return "";
        }
        ReshipStatus rs = ReshipStatus.getStatus(getReshipStatus());
        return rs.getName();
    }

    public String getOrderPayTypeName() {
        return PaymentTypeEnum.getByCode(orderPayType).getDisplay();
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

    protected Map<String, String> getCheckBackAddress() {
        Map<String, String> map = new HashMap<>();
        if (!org.springframework.util.StringUtils.isEmpty(backAddress)) {
            map.put("address", this.getBackAddress());
            map.put("consignee", this.getBackConsignee());
            map.put("mobile", this.getBackMobile());
        }
        return map;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("createDate", DateUtil.second2str2(this.getCreateDate()));
        obj.put("reshipSn", this.getReshipSn());
        obj.put("orderSn", this.getOrderSn());
        obj.put("statusName", this.getReshipStatusName());
        obj.put("statusCode", this.getReshipStatus());
        obj.put("productName", this.getProductName());
        obj.put("productImg", this.getProductImg());
        obj.put("productColor", this.getProductColor());
        obj.put("productSize", this.getProductSize());
        obj.put("tradeAmount", this.getTradeAmount());
        obj.put("refundId", this.getRefundId());
        obj.put("backAddress", this.getCheckBackAddress());
        obj.put("refundReason", this.getReshipReasonName());
        obj.put("memo", this.getMemo());
        obj.put("quantity", this.getQuantity());
        obj.put("productPrice", this.getPrice());
        obj.put("productSource", this.getProductSource());
        obj.put("productTradeType", this.getProductTradeType());
        obj.put("evidences", this.getEvidenceList());
        obj.put("customerMemo", this.getCustomerMemo());
        obj.put("deliveryCorpCode", this.getDeliveryCorpName());
        obj.put("deliveryCorpName", this.getDeliveryCorpName());
        obj.put("deliverySn", this.getDeliverySn());
        obj.put("closeDate", DateUtil.second2str2(this.getCloseDate()));
        obj.put("actualQuantity", this.getActualQuantity());
        obj.put("received", this.isReceived() ? 1 : 0);
        obj.put("creator", this.getCreator());
        if (this.getOrderPayType() != null) {
            obj.put("payType", this.getOrderPayTypeName());
            obj.put("paymentType", PaymentTypeEnum.getByCode(this.getOrderPayType()));
            obj.put("orderPayType", this.getOrderPayType());
        }
        if (this.getDeliveryExpiredDate() != null) {
            obj.put("deliveryExpiredDate", DateUtil.second2str2(this.getDeliveryExpiredDate()));
            long deliveryExpiredDay = 0;
            if (this.getDeliveryExpiredDate().compareTo(new Date()) > 0) {
                deliveryExpiredDay = DateUtil.dateSubtrationToDay(DateUtil.getEndOfDay(new Date()),
                        DateUtil.getEndOfDay(this.getDeliveryExpiredDate()));
            }
            obj.put("deliveryExpiredDay", deliveryExpiredDay);
        }
        return obj;
    }

    public enum ReshipStatus {
        APPLY(0, "正在申请退货"), APPROVE(2, "商家同意退货"), WAITFORRECEIVE(4, "仓库正在收货"),
        REFUSE(-3, "商家拒绝退货"), MEMBERCLOSE(-2, "用户取消退货"), CLOSE(-1, "商家关闭退货"), SUCCESS(8, "仓库已经签收");
        private static Map<Integer, ReshipStatus> holder = new HashMap<>();

        static {
            for (ReshipStatus status : values()) {
                holder.put(status.getCode(), status);
            }
        }

        private int code;
        private String display;

        ReshipStatus(int code) {
            this.code = code;
        }

        ReshipStatus(int code, String display) {
            this.code = code;
            this.display = display;
        }

        public static ReshipStatus getStatus(int i) {
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
                case -3:
                    return "商家拒绝退货";
                case -2:
                    return "用户取消退货";
                case -1:
                    return "商家关闭退货";
                case 0:
                    return "正在申请退货";
                case 2:
                    return "商家同意退货";
                case 4:
                    return "仓库正在收货";
                case 8:
                    return "仓库已经签收";
            }
            return "未知";
        }
    }

    public enum ReshipReason {
        repair(0), damaged(1), wrong(2), not(3), quality(4), noReason(7);
        private static Map<Integer, ReshipReason> holder = new HashMap<>();
        private static Map<String, ReshipReason> stringHolder = new HashMap<>();

        static {
            for (ReshipReason rr : ReshipReason.values()) {
                holder.put(rr.code, rr);
                stringHolder.put(rr.name(), rr);
            }
        }

        private int code;

        ReshipReason(int code) {
            this.code = code;
        }

        public static ReshipReason getStatus(int i) {
            return holder.get(i);
        }

        public static ReshipReason getStatusByName(String name) {
            return stringHolder.get(name);
        }

        public int getCode() {
            return code;
        }
    }

}
