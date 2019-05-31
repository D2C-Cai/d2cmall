package com.d2c.order.model;

import com.d2c.common.api.model.PreUserDO;
import com.d2c.member.model.MemberInfo;
import com.d2c.util.serial.SerialNumUtil;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 礼物记录
 */
@Table(name = "o_present_order")
public class PresentOrder extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 直播ID
     */
    private Long liveId;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 订单状态
     */
    private Integer orderStatus = PresentOrderStatus.INIT.code;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品价格
     */
    private BigDecimal productPrice;
    /**
     * 数量
     */
    private int quantity = 0;
    /**
     * 总价
     */
    private BigDecimal totalAmount;
    /**
     * 虚拟系数
     */
    private Integer ratio;
    /**
     * 支付类型
     */
    private Integer paymentType;
    /**
     * 支付ID
     */
    private Long paymentId;
    /**
     * 支付流水号
     */
    private String paySn;
    /**
     * 支付金额
     */
    private BigDecimal payAmount;
    /**
     * 支付时间
     */
    private Date payTime;
    /**
     * 买家ID
     */
    private Long buyMemberInfoId;
    /**
     * 买家账号
     */
    private String loginCode;
    /**
     * 接收人ID
     */
    private Long receiveMemberInfoId;
    /**
     * 接收人账号
     */
    private String receiveLoginCode;

    public PresentOrder() {
    }

    public PresentOrder(MemberInfo memberInfo, MemberInfo toMemberInfo) {
        this.setOrderSn(SerialNumUtil.buildVirtualOrderSn());
        this.setBuyMemberInfoId(memberInfo.getId());
        this.setLoginCode(memberInfo.getLoginCode());
        this.setCreator(memberInfo.getLoginCode());
        this.setLastModifyMan(memberInfo.getDisplayName());
        this.setReceiveMemberInfoId(toMemberInfo.getId());
        this.setReceiveLoginCode(toMemberInfo.getLoginCode());
        this.setOrderStatus(PresentOrderStatus.INIT.getCode());
    }

    public String getOrderStatusName() {
        if (this.orderStatus == null) {
            return null;
        }
        return PresentOrderStatus.holder.get(orderStatus).getName();
    }

    ;

    public String getOrderStatusString() {
        PresentOrderStatus orderStatus = PresentOrderStatus.getStatus(this.getOrderStatus());
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Long getBuyMemberInfoId() {
        return buyMemberInfoId;
    }

    public void setBuyMemberInfoId(Long buyMemberInfoId) {
        this.buyMemberInfoId = buyMemberInfoId;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getRatio() {
        return ratio;
    }

    public void setRatio(Integer ratio) {
        this.ratio = ratio;
    }

    public String getPaySn() {
        return paySn;
    }

    public void setPaySn(String paySn) {
        this.paySn = paySn;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Long getReceiveMemberInfoId() {
        return receiveMemberInfoId;
    }

    public void setReceiveMemberInfoId(Long receiveMemberInfoId) {
        this.receiveMemberInfoId = receiveMemberInfoId;
    }

    public String getReceiveLoginCode() {
        return receiveLoginCode;
    }

    public void setReceiveLoginCode(String receiveLoginCode) {
        this.receiveLoginCode = receiveLoginCode;
    }

    public Long getLiveId() {
        return liveId;
    }

    public void setLiveId(Long liveId) {
        this.liveId = liveId;
    }

    public enum PresentOrderStatus {
        CLOSE(-1), INIT(0), PAY(1), SUCCESS(8);
        private static Map<Integer, PresentOrderStatus> holder = new HashMap<Integer, PresentOrderStatus>();

        static {
            for (PresentOrderStatus orderStatus : values()) {
                holder.put(orderStatus.getCode(), orderStatus);
            }
        }

        private int code;

        PresentOrderStatus(int code) {
            this.code = code;
        }

        public static PresentOrderStatus getStatus(int i) {
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
