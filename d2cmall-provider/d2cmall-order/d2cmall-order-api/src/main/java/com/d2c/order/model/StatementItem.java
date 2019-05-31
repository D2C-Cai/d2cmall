package com.d2c.order.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.model.Order.OrderType;
import com.d2c.product.model.ProductSku;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 对账单明细
 */
@Table(name = "o_statement_item")
public class StatementItem extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 订单明细ID
     */
    private Long orderItemId;
    /**
     * 订单创建日期
     */
    private Date orderItemTime;
    /**
     * 发生时间(发货时间)
     */
    private Date transactionTime;
    /**
     * 官网货号
     */
    private String inernalSn;
    /**
     * 官网条码
     */
    private String barCode;
    /**
     * 设计师货号
     */
    private String externalSn;
    /**
     * 设计师条码(sku的external_sn)
     */
    private String externalCode;
    /**
     * 设计师ID
     */
    private Long designerId;
    /**
     * 设计师编号
     */
    private String designerCode;
    /**
     * 品牌名称
     */
    private String designerName;
    /**
     * 品牌编码
     */
    @Transient
    private String brandCode;
    /**
     * 商品图片
     */
    private String productImg;
    /**
     * 颜色
     */
    private String sp1;
    /**
     * 尺码
     */
    private String sp2;
    /**
     * 门店ID
     */
    private Long storeId;
    /**
     * 门店名称
     */
    private String storeName;
    /**
     * 支付方式(门店支付的，直接默认为OFFLINE（18）)
     */
    private Integer paymentType;
    /**
     * 支付流水号
     */
    private String paySn;
    /**
     * 商品数量
     */
    private Integer quantity;
    /**
     * 吊牌价
     */
    private BigDecimal tagPrice;
    /**
     * 售价
     */
    private BigDecimal productPrice;
    /**
     * 实付金额
     */
    private BigDecimal factAmount;
    /**
     * 优惠券抵扣金额
     */
    private BigDecimal couponPromotionAmount;
    /**
     * 整单优惠
     */
    private BigDecimal orderPromotionAmount;
    /**
     * 商品优惠
     */
    private BigDecimal promotionAmount;
    /**
     * 结算单价
     */
    private BigDecimal settlePrice;
    /**
     * 结算金额
     */
    private BigDecimal settleAmount;
    /**
     * 结算年
     */
    private int settleYear;
    /**
     * 结算月
     */
    private int settleMonth;
    /**
     * 结算日
     */
    private int settleDay;
    /**
     * 备注
     */
    @Column(insertable = false)
    private String remark;
    /**
     * 设计师备注
     */
    @Column(insertable = false)
    private String designerMemo;
    /**
     * 对账单id
     */
    private Long statementId;
    /**
     * 1发货，-1退货
     */
    private int direction;
    /**
     * 明细单来源类型
     */
    private Integer type;
    /**
     * 商品id
     */
    private Long productId;
    /**
     * 申请退款退货的时间
     */
    private Date reshipTime;
    /**
     * 关联订单号
     */
    private String relationSn;
    /**
     * 订单类型
     */
    private String orderType = OrderType.ordinary.name();
    /**
     * 扣减库存的类型 1：扣减POP库存 0：扣减真实库存
     */
    private Integer pop;

    public StatementItem() {
    }

    ;

    // 签收订单生成对账单明细表
    public StatementItem(OrderItem dto, ProductSku productSku, Order order, Store store, Integer status) {
        this.status = status;
        this.orderItemId = dto.getId();
        this.orderSn = dto.getOrderSn();
        this.orderItemTime = dto.getCreateDate();
        this.inernalSn = dto.getProductSn();
        this.externalSn = dto.getExternalSn();
        this.productImg = dto.getProductImg();
        this.sp1 = dto.getSp1();
        this.sp2 = dto.getSp2();
        this.quantity = dto.getDeliveryQuantity();
        this.storeId = dto.getStoreId();
        if (store != null) {
            this.storeName = store.getName();
        } else {
            this.storeId = 0L;
            this.storeName = "官网";
        }
        if (productSku != null) {
            this.externalCode = productSku.getExternalSn();
        }
        this.barCode = dto.getDeliveryBarCode();
        this.designerId = dto.getDesignerId();
        this.designerCode = dto.getDesignerCode();
        this.designerName = dto.getDesignerName();
        this.tagPrice = dto.getOriginalPrice();
        this.paymentType = dto.getPaymentType();
        if (order != null) {
            this.paySn = order.getPaymentSn();
        }
        this.productPrice = dto.getProductPrice();
        // 比例,按实发数/下单数
        BigDecimal ratio = new BigDecimal(dto.getDeliveryQuantity()).divide(new BigDecimal(dto.getProductQuantity()), 4,
                BigDecimal.ROUND_HALF_DOWN);
        this.factAmount = dto.getActualAmount().multiply(ratio).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        this.orderPromotionAmount = dto.getOrderPromotionAmount().multiply(ratio).setScale(2,
                BigDecimal.ROUND_HALF_DOWN);
        this.couponPromotionAmount = dto.getCouponAmount().multiply(ratio).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        this.promotionAmount = dto.getPromotionAmount().multiply(ratio).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        // 拼团订单要特殊处理下，记在订单明细里的价格是优惠后的价格，可能小于结算价
        if (OrderType.collage.name().equals(dto.getType())) {
            this.productPrice = productSku.getPrice();
            this.promotionAmount = this.productPrice.subtract(this.factAmount).subtract(this.orderPromotionAmount)
                    .subtract(this.couponPromotionAmount);
        }
        Calendar c = Calendar.getInstance();
        if (dto.getPaymentType() != null && dto.getPaymentType().equals(PaymentTypeEnum.COD.getCode())) {
            c.setTime(dto.getBalanceDate());
            this.transactionTime = dto.getBalanceDate();
        } else {
            c.setTime(dto.getDeliveryTime());
            this.transactionTime = dto.getDeliveryTime();
        }
        this.settleYear = c.get(Calendar.YEAR);
        this.settleMonth = c.get(Calendar.MONTH) + 1;
        this.settleDay = c.get(Calendar.DAY_OF_MONTH);
        this.direction = 1;
        this.creator = "sys";
        this.productId = dto.getProductId();
        this.orderType = dto.getType();
        this.pop = dto.getPop();
    }

    public StatementItem(Reship reship, ProductSku sku, OrderItem orderItem, Integer status) {
        this.status = status;
        this.orderItemId = reship.getId();
        this.orderSn = reship.getReshipSn();
        this.orderItemTime = orderItem.getCreateDate();
        this.reshipTime = reship.getCreateDate();
        this.transactionTime = reship.getReceiveDate();
        this.inernalSn = reship.getProductSn();
        this.externalSn = reship.getExternalSn();
        this.productImg = reship.getProductImg();
        this.barCode = StringUtils.isNotBlank(orderItem.getDeliveryBarCode()) ? orderItem.getDeliveryBarCode()
                : sku.getBarCode();
        this.sp1 = reship.getSp1();
        this.sp2 = reship.getSp2();
        this.quantity = reship.getActualQuantity() * (-1);
        this.externalCode = sku.getExternalSn();
        this.designerId = reship.getDesignerId();
        this.designerCode = reship.getDesignerCode();
        this.designerName = reship.getDesignerName();
        this.tagPrice = sku.getOriginalCost();
        this.paymentType = reship.getOrderPayType();
        this.storeId = 0L;
        this.setStoreName("官网");
        this.productPrice = orderItem.getProductPrice();
        // 比例,按实发数/下单数
        BigDecimal ratio = new BigDecimal(reship.getActualQuantity())
                .divide(new BigDecimal(orderItem.getProductQuantity()), 4, BigDecimal.ROUND_HALF_DOWN);
        this.factAmount = orderItem.getActualAmount().multiply(ratio).setScale(2, BigDecimal.ROUND_HALF_DOWN).negate();
        this.orderPromotionAmount = orderItem.getOrderPromotionAmount().multiply(ratio).setScale(2,
                BigDecimal.ROUND_HALF_DOWN);
        this.couponPromotionAmount = orderItem.getCouponAmount().multiply(ratio).setScale(2,
                BigDecimal.ROUND_HALF_DOWN);
        this.promotionAmount = orderItem.getPromotionAmount().multiply(ratio).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        Calendar c = Calendar.getInstance();
        c.setTime(reship.getReceiveDate());
        this.settleYear = c.get(Calendar.YEAR);
        this.settleMonth = c.get(Calendar.MONTH) + 1;
        this.settleDay = c.get(Calendar.DAY_OF_MONTH);
        this.direction = -1;
        this.creator = "sys";
        this.productId = reship.getProductId();
        this.relationSn = orderItem.getOrderSn();
        this.orderType = orderItem.getType();
        this.pop = orderItem.getPop();
    }

    public String getStatusName() {
        if (this.status == null) {
            return null;
        }
        return ItemStaus.holder.get(status).getName();
    }

    ;

    public String getStatusString() {
        ItemStaus itemStaus = ItemStaus.getStatus(this.getStatus());
        return itemStaus.name();
    }

    public String getTypeName() {
        if (this.type == null) {
            return null;
        }
        return SourceType.holder.get(type).getName();
    }

    public String getTypeString() {
        SourceType type = SourceType.getType(this.getType());
        return type.name();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getInernalSn() {
        return inernalSn;
    }

    public void setInernalSn(String inernalSn) {
        this.inernalSn = inernalSn;
    }

    public String getExternalSn() {
        return externalSn;
    }

    public void setExternalSn(String externalSn) {
        this.externalSn = externalSn;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getExternalCode() {
        return externalCode;
    }

    public void setExternalCode(String externalCode) {
        this.externalCode = externalCode;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
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

    public String getDesignerMemo() {
        return designerMemo;
    }

    public void setDesignerMemo(String designerMemo) {
        this.designerMemo = designerMemo;
    }

    public String getDesignerCode() {
        return designerCode;
    }

    public void setDesignerCode(String designerCode) {
        this.designerCode = designerCode;
    }

    public BigDecimal getTagPrice() {
        return tagPrice;
    }

    public void setTagPrice(BigDecimal tagPrice) {
        this.tagPrice = tagPrice;
    }

    public BigDecimal getFactAmount() {
        return factAmount;
    }

    public void setFactAmount(BigDecimal factAmount) {
        this.factAmount = factAmount;
    }

    public BigDecimal getCouponPromotionAmount() {
        return couponPromotionAmount;
    }

    public void setCouponPromotionAmount(BigDecimal couponPromotionAmount) {
        this.couponPromotionAmount = couponPromotionAmount;
    }

    public BigDecimal getOrderPromotionAmount() {
        return orderPromotionAmount;
    }

    public void setOrderPromotionAmount(BigDecimal orderPromotionAmount) {
        this.orderPromotionAmount = orderPromotionAmount;
    }

    public BigDecimal getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(BigDecimal promotionAmount) {
        this.promotionAmount = promotionAmount;
    }

    public BigDecimal getSettleAmount() {
        if (settleAmount == null) {
            if (this.settlePrice != null && this.quantity != null) {
                settleAmount = settlePrice.multiply(new BigDecimal(quantity));
            }
        }
        return settleAmount;
    }

    public void setSettleAmount(BigDecimal settleAmount) {
        this.settleAmount = settleAmount;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaySn() {
        return paySn;
    }

    public void setPaySn(String paySn) {
        this.paySn = paySn;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public Date getOrderItemTime() {
        return orderItemTime;
    }

    public void setOrderItemTime(Date orderItemTime) {
        this.orderItemTime = orderItemTime;
    }

    public int getSettleYear() {
        return settleYear;
    }

    public void setSettleYear(int settleYear) {
        this.settleYear = settleYear;
    }

    public int getSettleMonth() {
        return settleMonth;
    }

    public void setSettleMonth(int settleMonth) {
        this.settleMonth = settleMonth;
    }

    public int getSettleDay() {
        return settleDay;
    }

    public void setSettleDay(int settleDay) {
        this.settleDay = settleDay;
    }

    public BigDecimal getSettlePrice() {
        return settlePrice;
    }

    public void setSettlePrice(BigDecimal settlePrice) {
        this.settlePrice = settlePrice;
    }

    public Long getStatementId() {
        return statementId;
    }

    public void setStatementId(Long statementId) {
        this.statementId = statementId;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public BigDecimal getActualSettleAmount() {
        if (settleAmount != null)
            return this.settleAmount.multiply(new BigDecimal(direction));
        else
            return settleAmount;
    }

    public void setActualSettleAmount(BigDecimal amount) {
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getPayType() {
        if (this.paymentType != null) {
            return PaymentTypeEnum.getByCode(this.paymentType).getDisplay();
        } else {
            return "未知";
        }
    }

    public Date getReshipTime() {
        return reshipTime;
    }

    public void setReshipTime(Date reshipTime) {
        this.reshipTime = reshipTime;
    }

    public String getSp1Value() {
        if (this.sp1 != null) {
            try {
                return JSONObject.parseObject(this.sp1).get("value").toString();
            } catch (Exception e) {
                return this.sp1;
            }
        } else {
            return "";
        }
    }

    public String getRelationSn() {
        return relationSn;
    }

    public void setRelationSn(String relationSn) {
        this.relationSn = relationSn;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Integer getPop() {
        return pop;
    }

    public void setPop(Integer pop) {
        this.pop = pop;
    }

    public String getSp2Value() {
        if (this.sp2 != null) {
            try {
                return JSONObject.parseObject(this.sp2).get("value").toString();
            } catch (Exception e) {
                return this.sp2;
            }
        } else {
            return "";
        }
    }

    public String getOrderTypeName() {
        if (StringUtil.isBlank(this.orderType)) {
            return "未知";
        }
        return OrderType.valueOf(this.orderType).getDisplay();
    }

    public enum ItemStaus {
        INIT(0), WAITSIGN(1), WAITCONFIRM(2), WAITPAY(3), SUCCESS(8);
        private static Map<Integer, ItemStaus> holder = new HashMap<>();

        static {
            for (ItemStaus itemStaus : values()) {
                holder.put(itemStaus.getCode(), itemStaus);
            }
        }

        private int code;

        ItemStaus(int code) {
            this.code = code;
        }

        public static ItemStaus getStatus(int i) {
            return holder.get(i);
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            switch (this.code) {
                case 0:
                    return "待发送";
                case 1:
                    return "待接收";
                case 2:
                    return "待确认";
                case 3:
                    return "待支付";
                case 8:
                    return "已结算";
            }
            return "未知";
        }
    }

    public enum SourceType {
        OnlineOrder(0), OnlineReship(1), LineOrder(2), LineReship(3), OnlineRefund(4);
        private static Map<Integer, SourceType> holder = new HashMap<>();

        static {
            for (SourceType sourceType : values()) {
                holder.put(sourceType.getCode(), sourceType);
            }
        }

        private int code;

        SourceType(int code) {
            this.code = code;
        }

        public static SourceType getType(int i) {
            return holder.get(i);
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            switch (this.code) {
                case 0:
                    return "官网订单";
                case 1:
                    return "官网退货单";
                case 2:
                    return "线下订单";
                case 3:
                    return "线下退货单";
                case 4:
                    return "官网线下退款单";
            }
            return "未知";
        }
    }

}
