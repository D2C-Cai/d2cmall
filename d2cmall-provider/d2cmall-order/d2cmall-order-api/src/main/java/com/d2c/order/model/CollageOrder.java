package com.d2c.order.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.member.model.MemberInfo;
import com.d2c.order.dto.AddressDto;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.handle.PromotionCalculateResult;
import com.d2c.product.model.Product;
import com.d2c.product.model.ProductSku;
import com.d2c.util.date.DateUtil;
import com.d2c.util.serial.SerialNumUtil;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 拼团信息
 */
@Table(name = "o_collage_order")
public class CollageOrder extends PreUserDO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 编号
     */
    private String sn;
    /**
     * 团id
     */
    private Long groupId;
    /**
     * 团编号
     */
    private String groupSn;
    /**
     * 活动id
     */
    private Long promotionId;
    /**
     * 状态
     */
    private Integer status = CollageOrderStatus.WAITFORPAY.getCode();
    /**
     * 商品id
     */
    private Long productId;
    /**
     * 商品图片
     */
    private String productImage;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * skuId
     */
    private Long skuId;
    /**
     * sp1
     */
    private String sp1;
    /***
     * sp2
     */
    private String sp2;
    // 会员相关
    /**
     * 会员id
     */
    private Long memberId;
    /**
     * 会员昵称
     */
    private String memberName;
    /**
     * 会员账号
     */
    private String loginCode;
    /**
     * 会员头像
     */
    private String headPic;
    /**
     * 成员角色
     */
    private Integer type = RoleType.MEMBER.getCode();
    // 收货相关
    /**
     * 地址id
     */
    private Long addressId;
    /**
     * 收货人
     */
    private String reciver;
    /**
     * 联系电话
     */
    private String contact;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String district;
    /**
     * 街道
     */
    private String address;
    // 支付相关
    /**
     * 支付id
     */
    private Long paymentId;
    /**
     * 实付金额
     */
    private BigDecimal paymentAmount = new BigDecimal(0);
    /**
     * 支付流水
     */
    private String paySn;
    /**
     * 支付时间
     */
    private Date payTime;
    /**
     * 支付方式
     */
    private Integer paymentType;
    /**
     * 交易金额
     */
    private BigDecimal totalAmount = new BigDecimal(0);
    /**
     * 运费
     */
    private BigDecimal shippingRates = new BigDecimal(0);
    /**
     * 钱包本金分解
     */
    private BigDecimal cashAmount = new BigDecimal(0);
    /**
     * 钱包赠金分解
     */
    private BigDecimal giftAmount = new BigDecimal(0);
    /**
     * 分销商id
     */
    private Long partnerId;
    /**
     * 关闭时间
     */
    private Date closeTime;
    /**
     * 关闭备注
     */
    private String closeMemo;
    /**
     * 受票人（买方），购买方名称
     */
    private String drawee;
    /**
     * app终端
     */
    private String appTerminal;
    /**
     * app版本
     */
    private String appVersion;
    /**
     * 退款支付流水
     */
    private String refundPaySn;
    /**
     * 退款支付方式
     */
    private Integer refundPaymentType;
    /**
     * 退款备注
     */
    private String refundMemo;
    /**
     * 退款时间
     */
    private Date refundTime;

    public CollageOrder() {
    }

    public CollageOrder(Long memberId) {
        this.sn = SerialNumUtil.buildOrderSn(String.valueOf(memberId));
    }

    public CollageOrder(AddressDto dto, MemberInfo memberInfo, CollageGroup group, Product product,
                        ProductSku productSku, Long partnerId, String appTerminal, String appVersion, String drawee) {
        this(memberInfo.getId());
        this.addressId = dto.getId();
        this.reciver = dto.getName();
        this.contact = dto.getMobile();
        this.province = dto.getProvince().getName();
        this.city = dto.getCity().getName();
        this.district = dto.getDistrict().getName();
        this.address = dto.getStreet();
        this.memberId = memberInfo.getId();
        this.loginCode = memberInfo.getLoginCode();
        this.memberName = memberInfo.getNickname();
        this.headPic = memberInfo.getHeadPic();
        this.groupId = group.getId();
        this.groupSn = group.getGroupSn();
        this.productId = group.getProductId();
        this.productImage = product.getProductImageListFirst();
        this.productName = product.getName();
        this.skuId = productSku.getId();
        this.sp1 = productSku.getSp1();
        this.sp2 = productSku.getSp2();
        this.totalAmount = productSku.getCollagePrice();
        this.partnerId = partnerId;
        this.promotionId = group.getPromotionId();
        this.appTerminal = appTerminal;
        this.appVersion = appVersion;
        this.drawee = drawee;
        // 普通订单运费满299包邮
        this.shippingRates = PromotionCalculateResult.shippingRates(new BigDecimal(299), new BigDecimal(10),
                productSku.getCollagePrice());
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getRefundPaySn() {
        return refundPaySn;
    }

    public void setRefundPaySn(String refundPaySn) {
        this.refundPaySn = refundPaySn;
    }

    public Integer getRefundPaymentType() {
        return refundPaymentType;
    }

    public void setRefundPaymentType(Integer refundPaymentType) {
        this.refundPaymentType = refundPaymentType;
    }

    public String getRefundMemo() {
        return refundMemo;
    }

    public void setRefundMemo(String refundMemo) {
        this.refundMemo = refundMemo;
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }

    public String getGroupSn() {
        return groupSn;
    }

    public void setGroupSn(String groupSn) {
        this.groupSn = groupSn;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
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

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
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

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
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

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getShippingRates() {
        return shippingRates;
    }

    public void setShippingRates(BigDecimal shippingRates) {
        this.shippingRates = shippingRates;
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

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public String getCloseMemo() {
        return closeMemo;
    }

    public void setCloseMemo(String closeMemo) {
        this.closeMemo = closeMemo;
    }

    public String getDrawee() {
        return drawee;
    }

    public void setDrawee(String drawee) {
        this.drawee = drawee;
    }

    public String getAppTerminal() {
        return appTerminal;
    }

    public void setAppTerminal(String appTerminal) {
        this.appTerminal = appTerminal;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getColorValue() {
        if (this.sp1 != null) {
            return JSONObject.parseObject(sp1).get("value").toString();
        } else {
            return "";
        }
    }

    public void setColorValue() {
    }

    public String getSizeValue() {
        if (this.sp2 != null) {
            return JSONObject.parseObject(sp2).get("value").toString();
        } else {
            return "";
        }
    }

    public void setSizeValue() {
    }

    public Long getColorId() {
        if (this.sp1 != null) {
            return JSONObject.parseObject(sp1).getLong("id");
        } else {
            return 0L;
        }
    }

    public void setColorId() {
    }

    public Long getSizeId() {
        if (this.sp2 != null) {
            return JSONObject.parseObject(sp2).getLong("id");
        } else {
            return 0L;
        }
    }

    public void setSizeId() {
    }

    public BigDecimal getPaidAmount() {
        return this.getTotalAmount().add(this.getShippingRates());
    }

    public void setPaidAmount() {
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("createDate", DateUtil.second2str2(this.getCreateDate()));
        obj.put("orderSn", this.getSn());
        obj.put("type", this.getType());
        obj.put("status", this.getStatus());
        obj.put("statusName", CollageOrderStatus.getStatus(this.getStatus()).getValue());
        obj.put("groupId", this.getGroupId());
        obj.put("promotionId", this.getPromotionId());
        obj.put("productId", this.getProductId());
        obj.put("productImage", this.getProductImage());
        obj.put("productName", this.getProductName());
        obj.put("skuId", this.getSkuId());
        obj.put("size", this.getSizeValue());
        obj.put("color", this.getColorValue());
        obj.put("memberId", this.getMemberId());
        obj.put("memberName", this.getMemberName());
        obj.put("loginCode", this.getLoginCode());
        obj.put("headPic", this.getHeadPic());
        obj.put("payParams", PaymentTypeEnum.ALIPAY + "," + PaymentTypeEnum.WXAPPPAY + "," + PaymentTypeEnum.WALLET);
        obj.put("totalAmount", this.getTotalAmount());
        obj.put("totalPay", this.getPaidAmount());
        return obj;
    }

    public enum RoleType {
        MEMBER(0, "普通成员"), INITIATOR(1, "团长");
        private String value;
        private int code;

        RoleType(int code, String value) {
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }

    public enum CollageOrderStatus {
        AFTERCLOSE(-8, "退款成功"), CLOSE(-2, "超时关闭"), REFUND(-1, "待退款"),
        WAITFORPAY(1, "待支付"), PROCESS(4, "拼团中"), SUCESS(8, "拼团成功");
        private static Map<Integer, CollageOrderStatus> holder = new HashMap<>();

        static {
            for (CollageOrderStatus collageStatus : values()) {
                holder.put(collageStatus.getCode(), collageStatus);
            }
        }

        private int code;
        private String value;

        private CollageOrderStatus(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public static CollageOrderStatus getStatus(int i) {
            return holder.get(i);
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
