package com.d2c.order.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.util.date.DateUtil;
import com.d2c.util.serial.SerialNumUtil;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 换货单
 */
@Table(name = "o_order_exchange")
public class Exchange extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 换货编号
     */
    private String exchangeSn;
    /**
     * 退款单ID
     */
    private Long refundId;
    /**
     * 换货状态
     */
    private Integer exchangeStatus = ExchangeStatus.APPLY.code;
    /**
     * 换货理由
     */
    private String exchangeReason;
    /**
     * 物流公司名称
     */
    private String deliveryCorp;
    /**
     * 物流编号
     */
    private String deliverySn;
    /**
     * 用户说明
     */
    private String memo;
    /**
     * 客服备注
     */
    private String adminMemo;
    /**
     * SKU的Id
     */
    @AssertColumn("商品条码ID不能为空")
    private Long oldSkuId;
    /**
     * 条码
     */
    @AssertColumn("商品条码不能为空")
    private String oldSkuSn;
    /**
     * 商品ID
     */
    @AssertColumn("商品不能为空")
    private Long oldProductId;
    /**
     * 商品名称
     */
    private String oldProductName;
    /**
     * 商品货号
     */
    private String oldProductSn;
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
     * 销售属性1
     */
    private String oldSp1;
    /**
     * 销售属性2
     */
    private String oldSp2;
    /**
     * 销售属性3
     */
    private String oldSp3;
    /**
     * 商品图片
     */
    private String productImg;
    /**
     * 数量
     */
    private Integer quantity = 1;
    /**
     * 参考金额
     */
    private BigDecimal price;
    /**
     * 支付方式
     */
    private Integer orderPayType;
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
     * 对应订单 Id
     */
    @AssertColumn("订单ID不能为空")
    private Long orderId;
    /**
     * 对应订单 编号
     */
    private String orderSn;
    /**
     * 明细ID
     */
    @AssertColumn("订单明细ID不能为空")
    private Long orderItemId;
    /**
     * 要换SKU的ID
     */
    private Long skuId;
    /**
     * 要换商品的ID
     */
    private Long productId;
    /**
     * 要换商品的Sn
     */
    private String productSn;
    /**
     * 设计师货号
     */
    private String externalSn;
    /**
     * 要换SKU的Sn
     */
    private String skuSn;
    /**
     * 要换SKU的名称
     */
    private String skuName;
    /**
     * 要换SKU的sp1
     */
    private String sp1;
    /**
     * 要换SKU的sp2
     */
    private String sp2;
    /**
     * 要换SKU的sp3
     */
    private String sp3;
    /**
     * 要换SKU的金额
     */
    private BigDecimal exchangePrice;
    /**
     * 要换发货物流公司
     */
    private String exchangeDeliveryCorp;
    /**
     * 要换发货物流编号
     */
    private String exchangeDeliverySn;
    /**
     * 发货时间
     */
    private Date exchangeDeliveryDate;
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
     * 是否代理（1 是，0 不是）
     */
    private Integer proxy = 0;
    /**
     * 设计师直发退货地址
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
     * 寄件人
     */
    private String sender;
    /**
     * 寄件人联系电话
     */
    private String mobile;
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

    public String getExchangeStatusName() {
        if (this.getExchangeStatus() == null) {
            return "";
        }
        ExchangeStatus status = ExchangeStatus.getStatus(getExchangeStatus());
        if (status == null) {
            return "";
        }
        return status.getName();
    }

    public String getExchangeReasonName() {
        if (this.getExchangeReason() == null) {
            return "";
        }
        ExchangeReason reason = ExchangeReason.getStatusByName(this.getExchangeReason());
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
                return "七天无理由换货";
            default:
                return "";
        }
    }

    public void createExchange() {
        exchangeSn = SerialNumUtil.buildExchageSn();
        exchangeStatus = ExchangeStatus.APPLY.getCode();
    }

    public void createExchange(Order order, OrderItem orderItem) {
        this.createExchange();
        this.setOldSkuId(orderItem.getProductSkuId());
        this.setOldSkuSn(orderItem.getDeliveryBarCode());
        this.setOldProductId(orderItem.getProductId());
        this.setOldProductName(orderItem.getProductName());
        this.setOldProductSn(orderItem.getProductSn());
        this.setSaleCategory(orderItem.getSaleCategory());
        this.setProductTradeType(orderItem.getProductTradeType());
        this.setProductSource(orderItem.getProductSource());
        this.setExternalSn(orderItem.getExternalSn());
        this.setOldSp1(orderItem.getSp1());
        this.setOldSp2(orderItem.getSp2());
        this.setOldSp3(orderItem.getSp3());
        this.setProductImg(orderItem.getProductImg());
        this.setQuantity(orderItem.getDeliveryQuantity());
        this.setPrice(orderItem.getActualAmount());
        this.setMemberId(orderItem.getBuyerMemberId());
        this.setMemberName(orderItem.getBuyerMemberName());
        this.setLoginCode(order.getLoginCode());
        this.setCreator(order.getLoginCode());
        this.setOrderItemId(orderItem.getId());
        this.setOrderSn(order.getOrderSn());
        this.setOrderId(order.getId());
        this.setSender(order.getReciver());
        this.setMobile(order.getContact());
        this.setOrderPayType(order.getPaymentType());
        this.setDesignerId(orderItem.getDesignerId());
        this.setDesignerCode(orderItem.getDesignerCode());
        this.setDesignerName(orderItem.getDesignerName());
    }

    public String getExchangeSn() {
        return exchangeSn;
    }

    public void setExchangeSn(String exchangeSn) {
        this.exchangeSn = exchangeSn;
    }

    public Long getRefundId() {
        return refundId;
    }

    public void setRefundId(Long refundId) {
        this.refundId = refundId;
    }

    public Integer getExchangeStatus() {
        return exchangeStatus;
    }

    public void setExchangeStatus(Integer exchangeStatus) {
        this.exchangeStatus = exchangeStatus;
    }

    public String getExchangeReason() {
        return exchangeReason;
    }

    public void setExchangeReason(String exchangeReason) {
        this.exchangeReason = exchangeReason;
    }

    public String getDeliveryCorp() {
        return deliveryCorp;
    }

    public void setDeliveryCorp(String deliveryCorp) {
        this.deliveryCorp = deliveryCorp;
    }

    public String getDeliverySn() {
        return deliverySn;
    }

    public void setDeliverySn(String deliverySn) {
        this.deliverySn = deliverySn;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getAdminMemo() {
        return adminMemo;
    }

    public void setAdminMemo(String adminMemo) {
        this.adminMemo = adminMemo;
    }

    public Long getOldSkuId() {
        return oldSkuId;
    }

    public void setOldSkuId(Long oldSkuId) {
        this.oldSkuId = oldSkuId;
    }

    public String getOldSkuSn() {
        return oldSkuSn;
    }

    public void setOldSkuSn(String oldSkuSn) {
        this.oldSkuSn = oldSkuSn;
    }

    public Long getOldProductId() {
        return oldProductId;
    }

    public void setOldProductId(Long oldProductId) {
        this.oldProductId = oldProductId;
    }

    public String getOldProductName() {
        return oldProductName;
    }

    public void setOldProductName(String oldProductName) {
        this.oldProductName = oldProductName;
    }

    public String getOldProductSn() {
        return oldProductSn;
    }

    public void setOldProductSn(String oldProductSn) {
        this.oldProductSn = oldProductSn;
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

    public String getOldSp1() {
        return oldSp1;
    }

    public void setOldSp1(String oldSp1) {
        this.oldSp1 = oldSp1;
    }

    public String getOldSp2() {
        return oldSp2;
    }

    public void setOldSp2(String oldSp2) {
        this.oldSp2 = oldSp2;
    }

    public String getOldSp3() {
        return oldSp3;
    }

    public void setOldSp3(String oldSp3) {
        this.oldSp3 = oldSp3;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getOrderPayType() {
        return orderPayType;
    }

    public void setOrderPayType(Integer orderPayType) {
        this.orderPayType = orderPayType;
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

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
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

    public BigDecimal getExchangePrice() {
        return exchangePrice;
    }

    public void setExchangePrice(BigDecimal exchangePrice) {
        this.exchangePrice = exchangePrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getExchangeDeliveryCorp() {
        return exchangeDeliveryCorp;
    }

    public void setExchangeDeliveryCorp(String exchangeDeliveryCorp) {
        this.exchangeDeliveryCorp = exchangeDeliveryCorp;
    }

    public String getExchangeDeliverySn() {
        return exchangeDeliverySn;
    }

    public void setExchangeDeliverySn(String exchangeDeliverySn) {
        this.exchangeDeliverySn = exchangeDeliverySn;
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

    public Integer getProxy() {
        return proxy;
    }

    public void setProxy(Integer proxy) {
        this.proxy = proxy;
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

    public String getProductColor() {
        JSONObject obejct = JSONObject.parseObject(oldSp1);
        return obejct.getString("value");
    }

    public String getProductSize() {
        if (this.sp2 != null) {
            return JSONObject.parseObject(sp2).get("value").toString();
        } else {
            return "";
        }
    }

    public Date getExchangeDeliveryDate() {
        return exchangeDeliveryDate;
    }

    public void setExchangeDeliveryDate(Date exchangeDeliveryDate) {
        this.exchangeDeliveryDate = exchangeDeliveryDate;
    }

    private Map<String, String> getCheckBackAddress() {
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
        obj.put("exchangeSn", this.getExchangeSn());
        obj.put("orderSn", this.getOrderSn());
        obj.put("statusName", this.getExchangeStatusName());
        obj.put("statusCode", this.getExchangeStatus());
        obj.put("productName", this.getOldProductName());
        obj.put("productImg", this.getProductImg());
        obj.put("productColor", this.getProductColor());
        obj.put("productSize", this.getProductSize());
        obj.put("totalAmount", this.getPrice());
        obj.put("backAddress", this.getCheckBackAddress());
        obj.put("refundReason", this.getExchangeReasonName());
        obj.put("memo", this.getMemo());
        obj.put("quantity", this.getQuantity());
        obj.put("productPrice", this.getPrice());
        obj.put("deliveryCorpCode", this.getDeliveryCorp());
        obj.put("deliveryCorpName", this.getDeliveryCorp());
        obj.put("deliverySn", this.getDeliverySn());
        obj.put("exchangeDeliveryCorp", this.getExchangeDeliveryCorp());
        obj.put("exchangeDeliveryCode", this.getExchangeDeliveryCorp());
        obj.put("exchangeDeliverySn", this.getExchangeDeliverySn());
        obj.put("customerMemo", this.getAdminMemo());
        obj.put("productSn", this.getProductSn());
        obj.put("productSource", this.getProductSource());
        obj.put("productTradeType", this.getProductTradeType());
        obj.put("color", this.getSp1() != null ? JSONObject.parseObject(this.getSp1()).getString("value") : "");
        obj.put("size", this.getSp2() != null ? JSONObject.parseObject(this.getSp2()).getString("value") : "");
        obj.put("skuSn", this.getSkuSn());
        obj.put("closeDate", DateUtil.second2str2(this.getCloseDate()));
        obj.put("creator", this.getCreator());
        return obj;
    }

    public enum ExchangeStatus {
        APPLY(0, "正在申请换货"), APPROVE(1, "商家同意换货"),
        WAITFORRECEIVE(2, "仓库正在收货"), WAITDELIVERED(3, "等待商家换出"), DELIVERED(4, "等待用户收货"),
        REFUSE(-3, "商家拒绝换货"), MEMBERCLOSE(-2, "用户取消换货"), CLOSE(-1, "商家关闭换货"), SUCCESS(8, "换货成功");
        private static Map<Integer, ExchangeStatus> holder = new HashMap<>();

        static {
            for (ExchangeStatus status : values()) {
                holder.put(status.getCode(), status);
            }
        }

        private int code;
        private String display;

        ExchangeStatus(int code) {
            this.code = code;
        }

        ExchangeStatus(int code, String display) {
            this.code = code;
            this.display = display;
        }

        public static ExchangeStatus getStatus(int i) {
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
                    return "商家拒绝换货";
                case -2:
                    return "用户取消换货";
                case -1:
                    return "商家关闭换货";
                case 0:
                    return "正在申请换货";
                case 1:
                    return "商家同意换货";
                case 2:
                    return "仓库正在收货";
                case 3:
                    return "等待商家换出";
                case 4:
                    return "等待用户收货";
                case 8:
                    return "换货成功";
            }
            return "未知";
        }
    }

    public enum ExchangeReason {
        repair(0), damaged(1), wrong(2), not(3), quality(4), noReason(7);
        private static Map<Integer, ExchangeReason> holder = new HashMap<>();
        private static Map<String, ExchangeReason> stringHolder = new HashMap<>();

        static {
            holder.put(0, ExchangeReason.repair);
            holder.put(1, ExchangeReason.damaged);
            holder.put(2, ExchangeReason.wrong);
            holder.put(3, ExchangeReason.not);
            holder.put(4, ExchangeReason.quality);
            holder.put(7, ExchangeReason.noReason);
            stringHolder.put(ExchangeReason.repair.name(), ExchangeReason.repair);
            stringHolder.put(ExchangeReason.damaged.name(), ExchangeReason.damaged);
            stringHolder.put(ExchangeReason.wrong.name(), ExchangeReason.wrong);
            stringHolder.put(ExchangeReason.not.name(), ExchangeReason.not);
            stringHolder.put(ExchangeReason.quality.name(), ExchangeReason.quality);
            stringHolder.put(ExchangeReason.noReason.name(), ExchangeReason.noReason);
        }

        private int code;

        ExchangeReason(int code) {
            this.code = code;
        }

        public static ExchangeReason getStatus(int i) {
            return holder.get(i);
        }

        public static ExchangeReason getStatusByName(String name) {
            return stringHolder.get(name);
        }

        public int getCode() {
            return code;
        }
    }

}
