package com.d2c.order.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.product.model.AuctionProduct;
import com.d2c.util.serial.SerialNumUtil;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 拍卖保证金
 */
@Table(name = "o_auction_margin")
public class AuctionMargin extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 保证金单号
     */
    @AssertColumn("单号不能为空")
    private String marginSn;
    /**
     * 拍卖活动ID，同一件商品可能多次拍卖
     */
    @AssertColumn("拍卖商品不能为空")
    private Long auctionId;
    /**
     * 拍卖商品ID
     */
    private Long auctionProductId;
    /**
     * 拍卖活动标题
     */
    private String auctionTitle;
    /**
     * 会员ID
     */
    @AssertColumn("拍卖会员不能为空")
    private Long memberId;
    /**
     * 会员ID
     */
    private Long oldMemberId;
    /**
     * 登录账号
     */
    private String loginCode;
    /**
     * 昵称
     */
    private String memberNick;
    /**
     * 保证金
     */
    private BigDecimal margin;
    /**
     * 最终价格
     */
    private BigDecimal offer;
    /**
     * 支付时间
     */
    private Date payDate;
    /**
     * 支付流水号，如果为空（支付）
     */
    private String paySn;
    /**
     * 支付ID
     */
    private Long paymentId;
    /**
     * 支付方式
     */
    private Integer paymentType = PaymentTypeEnum.ALIPAY.getCode();
    /**
     * 保证金状态 <br>
     * <p>
     * 0 初始创建， 1 已付保证金 ，-1 已删除，<br>
     * 2 竞拍成功 ，6 已付尾款， 8已发货，10已收货，<br>
     * -2 未中拍 ，-6 已违约，-8已退款，
     */
    private Integer status = 0;
    /**
     * 二次支付时间
     */
    private Date payDate2;
    /**
     * 二次支付流水号，如果为空（支付）
     */
    private String paySn2;
    /**
     * 二次支付Id
     */
    private Long paymentId2;
    /**
     * 支付方式
     */
    private Integer paymentType2 = PaymentTypeEnum.ALIPAY.getCode();
    /**
     * 收货地址ID
     */
    private Long addressId;
    /**
     * 物流公司名称
     */
    private String deliveryCorpName;
    /**
     * 物流编号
     */
    private String deliverySn;
    /**
     * 退款方式
     */
    private String refundType;
    /**
     * 退款时间
     */
    private Date refundDate;
    /**
     * 退款人
     */
    private String refunder;
    /**
     * 退款交易号
     */
    private String refundSn;

    public AuctionMargin() {
    }

    public AuctionMargin(AuctionProduct product) {
        String marginSn = SerialNumUtil.buildAuctionMarginSn();
        this.marginSn = marginSn;
        this.auctionId = product.getId();
        this.auctionProductId = product.getProductId();
        this.auctionTitle = product.getTitle();
        this.margin = product.getMargin();
    }

    public String getMarginSn() {
        return marginSn;
    }

    public void setMarginSn(String marginSn) {
        this.marginSn = marginSn;
    }

    public Long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Long auctionId) {
        this.auctionId = auctionId;
    }

    public Long getAuctionProductId() {
        return auctionProductId;
    }

    public void setAuctionProductId(Long auctionProductId) {
        this.auctionProductId = auctionProductId;
    }

    public String getAuctionTitle() {
        return auctionTitle;
    }

    public void setAuctionTitle(String auctionTitle) {
        this.auctionTitle = auctionTitle;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getOldMemberId() {
        return oldMemberId;
    }

    public void setOldMemberId(Long oldMemberId) {
        this.oldMemberId = oldMemberId;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public BigDecimal getMargin() {
        return margin;
    }

    public void setMargin(BigDecimal margin) {
        this.margin = margin;
    }

    public BigDecimal getOffer() {
        return offer;
    }

    public void setOffer(BigDecimal offer) {
        this.offer = offer;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public String getPaySn() {
        return paySn;
    }

    public void setPaySn(String paySn) {
        this.paySn = paySn;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getPayDate2() {
        return payDate2;
    }

    public void setPayDate2(Date payDate2) {
        this.payDate2 = payDate2;
    }

    public String getPaySn2() {
        return paySn2;
    }

    public void setPaySn2(String paySn2) {
        this.paySn2 = paySn2;
    }

    public Long getPaymentId2() {
        return paymentId2;
    }

    public void setPaymentId2(Long paymentId2) {
        this.paymentId2 = paymentId2;
    }

    public Integer getPaymentType2() {
        return paymentType2;
    }

    public void setPaymentType2(Integer paymentType2) {
        this.paymentType2 = paymentType2;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getDeliveryCorpName() {
        return deliveryCorpName;
    }

    public void setDeliveryCorpName(String deliveryCorpName) {
        this.deliveryCorpName = deliveryCorpName;
    }

    public String getDeliverySn() {
        return deliverySn;
    }

    public void setDeliverySn(String deliverySn) {
        this.deliverySn = deliverySn;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public Date getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(Date refundDate) {
        this.refundDate = refundDate;
    }

    public String getRefunder() {
        return refunder;
    }

    public void setRefunder(String refunder) {
        this.refunder = refunder;
    }

    public String getRefundSn() {
        return refundSn;
    }

    public void setRefundSn(String refundSn) {
        this.refundSn = refundSn;
    }

    public String getStatuText() {
        switch (this.status.intValue()) {
            case -8:
                return "已退款";
            case -6:
                return "已违约";
            case -2:
                return "未中拍";
            case -1:
                return "已删除";
            case 0:
                return "初始创建";
            case 1:
                return "已付保证金";
            case 2:
                return "竞拍成功";
            case 6:
                return "已付尾款";
            case 8:
                return "已发货";
            case 10:
                return "已收货";
            default:
                return "";
        }
    }

    public String getMemberNick() {
        if (memberNick != null) {
            return memberNick;
        } else {
            return "匿名_" + memberId;
        }
    }

    public void setMemberNick(String memberNick) {
        this.memberNick = memberNick;
    }

    public BigDecimal getTotalAmount() {
        if (this.getStatus() == 0 || this.getStatus() == -2) {
            return this.getMargin();
        } else if (this.getStatus() == 2) {
            return this.getOffer().subtract(this.getMargin());
        }
        return new BigDecimal("0");
    }

}
