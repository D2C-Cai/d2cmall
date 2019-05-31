package com.d2c.order.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.util.serial.SerialNumUtil;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 优惠券订单
 */
@Table(name = "o_coupon_order")
public class CouponOrder extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 订单状态
     */
    private Integer orderStatus = CouponOrderStatus.INIT.code;
    /**
     * 优惠券模板ID
     */
    private Long couponDefId;
    /**
     * 优惠券价格
     */
    private BigDecimal couponPrice;
    /**
     * 优惠券名称
     */
    private String couponName;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 总价
     */
    private BigDecimal totalAmount;
    /**
     * 支付流水号
     */
    private String paySn;
    /**
     * 支付时间
     */
    private Date payTime;
    /**
     * 支付金额
     */
    private BigDecimal payAmount;
    /**
     * 支付ID
     */
    private Long paymentId;
    /**
     * 支付类型
     */
    private Integer paymentType;
    /**
     * 买家ID
     */
    private Long memberId;
    /**
     * 买家账号
     */
    private String loginCode;

    public CouponOrder() {
    }

    public CouponOrder(CouponDef couponDef) {
        this.orderSn = SerialNumUtil.buildCouponOrderSn();
        this.couponDefId = couponDef.getId();
        this.couponPrice = couponDef.getPrice();
        this.couponName = couponDef.getName();
    }

    public String getOrderStatusName() {
        if (this.orderStatus == null) {
            return null;
        }
        return CouponOrderStatus.holder.get(orderStatus).getName();
    }

    ;

    public String getOrderStatusString() {
        CouponOrderStatus orderStatus = CouponOrderStatus.getStatus(this.getOrderStatus());
        return orderStatus.name();
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getCouponDefId() {
        return couponDefId;
    }

    public void setCouponDefId(Long couponDefId) {
        this.couponDefId = couponDefId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public BigDecimal getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(BigDecimal couponPrice) {
        this.couponPrice = couponPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaySn() {
        return paySn;
    }

    public void setPaySn(String paySn) {
        this.paySn = paySn;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
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

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("orderSn", this.getOrderSn());
        obj.put("couponName", this.getCouponName());
        obj.put("quantity", this.getQuantity());
        obj.put("totalAmount", this.getTotalAmount());
        obj.put("payParams", PaymentTypeEnum.ALIPAY + "," + PaymentTypeEnum.WXAPPPAY + "," + PaymentTypeEnum.WALLET);
        return obj;
    }

    public enum CouponOrderStatus {
        CLOSE(-1), INIT(0), PAY(1), SUCCESS(8);
        private static Map<Integer, CouponOrderStatus> holder = new HashMap<Integer, CouponOrderStatus>();

        static {
            for (CouponOrderStatus orderStatus : values()) {
                holder.put(orderStatus.getCode(), orderStatus);
            }
        }

        private int code;

        CouponOrderStatus(int code) {
            this.code = code;
        }

        public static CouponOrderStatus getStatus(int i) {
            return holder.get(i);
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            switch (this.code) {
                case -1:
                    return "交易关闭";
                case 0:
                    return "待付款 ";
                case 1:
                    return "付款成功";
                case 8:
                    return "交易完成";
            }
            return "未知";
        }
    }

}
