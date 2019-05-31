package com.d2c.order.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.annotation.HideColumn;
import com.d2c.common.api.annotation.HideEnum;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.member.model.MemberInfo;
import com.d2c.order.dto.AddressDto;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.model.base.IAddressInterface;
import com.d2c.order.model.base.IMemberInterface;
import com.d2c.util.date.DateUtil;
import org.jsoup.helper.StringUtil;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 订单
 */
@Table(name = "orders")
public class Order extends PreUserDO implements IAddressInterface, IMemberInterface {

    private static final long serialVersionUID = 1L;
    // 编号信息
    /**
     * 临时ID， 防止重复生成订单
     */
    private String tempId;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 订单类型
     */
    private String type = OrderType.ordinary.name();
    /**
     * 订单状态
     */
    private Integer orderStatus = OrderStatus.WaitingForPay.code;
    // 会员信息
    /**
     * 会员ID
     */
    @AssertColumn("会员ID不能为空")
    private Long memberId;
    /**
     * 会员账号
     */
    @HideColumn(type = HideEnum.MOBILE)
    private String loginCode;
    /**
     * 邮件(目前用于保存收货地址里的微信号和邮箱)
     */
    private String memberEmail;
    /**
     * 电话
     */
    private String memberMobile;
    // 地址信息
    /**
     * 收货人姓名
     */
    private String reciver;
    /**
     * 联系方式
     */
    @HideColumn(type = HideEnum.MOBILE)
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
     * 区，县
     */
    private String district;
    /**
     * 收货地址
     */
    private String address;
    /**
     * X轴
     */
    private BigDecimal longitude;
    /**
     * Y轴
     */
    private BigDecimal latitude;
    /**
     * 留言
     */
    private String memo;
    // 支付信息
    /**
     * 支付单ID
     */
    private Long paymentId;
    /**
     * 支付方式
     */
    private Integer paymentType = PaymentTypeEnum.ALIPAY.getCode();
    /**
     * 支付编号
     */
    @Column(insertable = false)
    private String paymentSn;
    /**
     * 支付时间
     */
    @Column(insertable = false)
    private Date paymentTime;
    /**
     * 代付标志
     */
    private Integer substitute = 0;
    /**
     * 币种
     */
    private String currency;
    /**
     * 汇率
     */
    private BigDecimal exchangRate;
    // 发票信息
    /**
     * 受票人（买方），购买方名称
     */
    private String drawee;
    /**
     * 是否开票
     */
    private int invoiced = 0;
    /**
     * 开票人
     */
    @Column(insertable = false)
    private String drawer;
    /**
     * 开票时间
     */
    @Column(insertable = false)
    private Date drawDate;
    // 价格信息
    /**
     * 商品总数量
     */
    @AssertColumn(value = "商品总数量应该大于0", min = 0)
    private int productTotalQuantity = 0;
    /**
     * 商品总价格
     */
    private BigDecimal productTotalPrice = new BigDecimal("0.00");
    /**
     * 商品总计优惠金额
     */
    private BigDecimal productPromotionAmount = new BigDecimal("0.00");
    /**
     * 满减总计优惠金额
     */
    private BigDecimal promotionAmount = new BigDecimal("0.00");
    /**
     * 实际商品应付金额（ 商品总价格-商品总计优惠金额-满减总计优惠金额）
     */
    @AssertColumn(value = "订单金额应该大于等于0", mineq = 0)
    private BigDecimal totalAmount = new BigDecimal("0.00");
    /**
     * 优惠券使用金额
     */
    private BigDecimal couponAmount = new BigDecimal(0);
    /**
     * 红包使用金额
     */
    private BigDecimal redAmount = new BigDecimal("0.00");
    /**
     * 买手优惠金额
     */
    private BigDecimal partnerAmount = new BigDecimal("0.00");
    /**
     * 税费金额
     */
    private BigDecimal taxAmount = new BigDecimal("0.00");
    /**
     * 运费金额
     */
    private BigDecimal shippingRates = new BigDecimal(0);
    /**
     * 实际订单支付金额（实际商品支付金额-优惠券金额-红包金额-买手金额+税费+运费）
     */
    private BigDecimal paidAmount = new BigDecimal("0.00");
    // 物流信息
    /**
     * 物流编号
     */
    private String deliverySn;
    /**
     * 物流公司
     */
    private String deliveryCorpName;
    /**
     * 订单发货时间
     */
    @Column(insertable = false)
    private Date deliveryTime;
    // 活动关联
    /**
     * 砍价活动ID
     */
    private String bargainId;
    /**
     * 拼团活动ID
     */
    private Long collageId;
    /**
     * 分销商ID
     */
    private Long partnerId;
    /**
     * 优惠券ID
     */
    private Long couponId;
    /**
     * 红包ID
     */
    private Long redId;
    /**
     * 推荐人ID
     */
    private Long recId;
    /**
     * CRM小组ID
     */
    private Long crmGroupId;
    /**
     * 门店会员ID
     */
    private Long storeGroupId;
    /**
     * 订单满减优惠名称
     */
    private String promotionName;
    // 其他信息
    /**
     * 是否跨境 1：跨境 0：国内
     */
    private Integer crossBorder = 0;
    /**
     * 是否保护
     */
    private boolean protection;
    /**
     * 是否删除
     */
    private int deleted = 0;
    /**
     * 是否刷单
     */
    private int flag = 1;
    /**
     * 订单渠道
     */
    private String terminal;
    /**
     * 订单渠道ID
     */
    private String terminalId;
    /**
     * app设备
     */
    private String device = DeviceTypeEnum.APPIOS.toString();
    /**
     * app版本号
     */
    private String appVersion;
    /**
     * 客服备注
     */
    private String adminMemo;
    /**
     * 订单关闭人
     */
    @Column(insertable = false)
    private String orderCloseMan;
    /**
     * 订单关闭时间
     */
    @Column(insertable = false)
    private Date orderCloseTime;
    /**
     * 订单关闭原因
     */
    @Column(insertable = false)
    private String closeReason;

    public String getOrderTypeName() {
        if (StringUtil.isBlank(this.type)) {
            return "未知";
        }
        return OrderType.valueOf(this.type).getDisplay();
    }

    public String getOrderStatusName() {
        if (this.orderStatus == null) {
            return "未知";
        }
        return OrderStatus.holder.get(orderStatus).getName();
    }

    public String getOrderStatusString() {
        OrderStatus orderStatus = OrderStatus.getStatus(this.getOrderStatus());
        return orderStatus.name();
    }

    ;

    /**
     * 判断是否超过24小时价格保护期
     */
    public boolean isOver() {
        boolean over = System.currentTimeMillis() - getCreateDate().getTime() > 24 * 60 * 60 * 1000L;
        return over;
    }

    /**
     * 订单是否可以取消
     */
    public boolean isCancel() {
        if (orderStatus == 1) {
            return true;
        } else if (orderStatus == 3 && paymentType == 3) {
            return true;
        }
        return false;
    }

    public String getPaymentTypeName() {
        if (getPaymentType() != null) {
            PaymentTypeEnum type = PaymentTypeEnum.getByCode(getPaymentType());
            return type.getDisplay();
        }
        return "没有支付方式";
    }

    public String getPaymentTypeString() {
        PaymentTypeEnum paymentType = PaymentTypeEnum.getByCode(this.getPaymentType());
        return paymentType.name();
    }

    public String getProvince() {
        String province1 = province;
        if (province1 != null) {
            if (province1.equals("北京市")) {
                province1 = "北京";
            } else if (province1.equals("天津市")) {
                province1 = "天津";
            } else if (province1.equals("上海市")) {
                province1 = "上海";
            } else if (province1.equals("重庆市")) {
                province1 = "重庆";
            }
        }
        return province1;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        if (province != null && city != null) {
            if (province.equals("北京市") || province.equals("北京")) {
                city = "北京市";
            } else if (province.equals("天津市") || province.equals("天津")) {
                city = "天津市";
            } else if (province.equals("上海市") || province.equals("上海")) {
                city = "上海市";
            } else if (province.equals("重庆市") || province.equals("重庆")) {
                city = "重庆市";
            }
        }
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
    }

    public int getProductTotalQuantity() {
        return productTotalQuantity;
    }

    public void setProductTotalQuantity(int productTotalQuantity) {
        this.productTotalQuantity = productTotalQuantity;
    }

    public BigDecimal getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(BigDecimal productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public BigDecimal getProductPromotionAmount() {
        return productPromotionAmount;
    }

    public void setProductPromotionAmount(BigDecimal productPromotionAmount) {
        this.productPromotionAmount = productPromotionAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
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

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Long getRedId() {
        return redId;
    }

    public void setRedId(Long redId) {
        this.redId = redId;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
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

    public boolean isProtection() {
        return protection;
    }

    public void setProtection(boolean protection) {
        this.protection = protection;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
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

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public Integer getCrossBorder() {
        return crossBorder;
    }

    public void setCrossBorder(Integer crossBorder) {
        this.crossBorder = crossBorder;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
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

    public String getDrawee() {
        return drawee;
    }

    public void setDrawee(String drawee) {
        this.drawee = drawee;
    }

    public int getInvoiced() {
        return invoiced;
    }

    public void setInvoiced(int invoiced) {
        this.invoiced = invoiced;
    }

    public String getAdminMemo() {
        return adminMemo;
    }

    public void setAdminMemo(String adminMemo) {
        this.adminMemo = adminMemo;
    }

    public String getOrderCloseMan() {
        return orderCloseMan;
    }

    public void setOrderCloseMan(String orderCloseMan) {
        this.orderCloseMan = orderCloseMan;
    }

    public Date getOrderCloseTime() {
        return orderCloseTime;
    }

    public void setOrderCloseTime(Date orderCloseTime) {
        this.orderCloseTime = orderCloseTime;
    }

    public String getCloseReason() {
        return closeReason;
    }

    public void setCloseReason(String closeReason) {
        this.closeReason = closeReason;
    }

    public String getPaymentSn() {
        return paymentSn;
    }

    public void setPaymentSn(String paymentSn) {
        this.paymentSn = paymentSn;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Integer getSubstitute() {
        return substitute;
    }

    public void setSubstitute(Integer substitute) {
        this.substitute = substitute;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDrawer() {
        return drawer;
    }

    public void setDrawer(String drawer) {
        this.drawer = drawer;
    }

    public Date getDrawDate() {
        return drawDate;
    }

    public void setDrawDate(Date drawDate) {
        this.drawDate = drawDate;
    }

    public Long getRecId() {
        return recId;
    }

    public void setRecId(Long recId) {
        this.recId = recId;
    }

    public String getBargainId() {
        return bargainId;
    }

    public void setBargainId(String bargainId) {
        this.bargainId = bargainId;
    }

    public Long getCollageId() {
        return collageId;
    }

    public void setCollageId(Long collageId) {
        this.collageId = collageId;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public Long getCrmGroupId() {
        return crmGroupId;
    }

    public void setCrmGroupId(Long crmGroupId) {
        this.crmGroupId = crmGroupId;
    }

    public Long getStoreGroupId() {
        return storeGroupId;
    }

    public void setStoreGroupId(Long storeGroupId) {
        this.storeGroupId = storeGroupId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getExchangRate() {
        return exchangRate;
    }

    public void setExchangRate(BigDecimal exchangRate) {
        this.exchangRate = exchangRate;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getPromotionAmount() {
        if (promotionAmount == null) {
            promotionAmount = new BigDecimal(0);
        }
        return promotionAmount;
    }

    public void setPromotionAmount(BigDecimal promotionAmount) {
        this.promotionAmount = promotionAmount;
    }

    public BigDecimal getCouponAmount() {
        if (couponAmount == null) {
            couponAmount = new BigDecimal(0);
        }
        return couponAmount;
    }

    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }

    public BigDecimal getShippingRates() {
        if (shippingRates == null) {
            shippingRates = new BigDecimal(0);
        }
        return shippingRates;
    }

    public void setShippingRates(BigDecimal shippingRates) {
        this.shippingRates = shippingRates;
    }

    /**
     * 总的应付金额
     */
    public BigDecimal getTotalPay() {
        BigDecimal totalPay = this.getTotalAmount().add(this.getShippingRates()).subtract(this.getCouponAmount())
                .subtract(this.getRedAmount()).subtract(this.getPartnerAmount()).add(this.getTaxAmount());
        return totalPay;
    }

    /**
     * 购买人手机号
     */
    public String getBuyerInfo() {
        if (!StringUtils.isEmpty(this.getMemberMobile())) {
            return this.getMemberMobile();
        } else if (StringUtils.isEmpty(this.getMemberEmail())) {
            return this.getMemberEmail();
        }
        return "";
    }

    /**
     * 拼接收货地址
     */
    public String getSendAddress() {
        return this.province + " " + this.city + " " + this.address + " ";
    }

    /**
     * 拼接收货地址
     */
    public String getDeliveryAddress() {
        StringBuilder sb = new StringBuilder();
        if (this.getProvince() != null) {
            sb.append(getProvince() + " ");
        }
        if (this.getCity() != null) {
            sb.append(getCity() + " ");
        }
        if (this.getDistrict() != null) {
            if (this.getDistrict().equals("--")) {
                sb.append(getCity() + " ");
            } else {
                sb.append(getDistrict() + " ");
            }
        }
        if (this.getAddress() != null) {
            sb.append(getAddress().trim());
        }
        return sb.toString();
    }

    public void setMember(MemberInfo member) {
        if (member != null) {
            this.memberId = member.getId();
            this.memberEmail = member.getEmail();
            this.memberMobile = (member.getMobile());
        }
    }

    public void setReciverInfo(AddressDto address) {
        this.reciver = address.getName();
        if (address.getProvince() != null) {
            this.province = address.getProvince().getName();
        }
        if (address.getCity() != null) {
            this.city = address.getCity().getName();
        }
        if (address.getDistrict() != null) {
            if (address.getDistrict().equals("--")) {
                this.district = this.city;
                this.address = address.getStreet();
            } else {
                this.district = address.getDistrict().getName();
                this.address = address.getDistrict().getName() + address.getStreet();
            }
        } else {
            this.address = address.getStreet();
        }
        // 从微博拉取的详细地址不用拼接其他前缀
        if ("Weibo".equals(address.getOrigin())) {
            this.address = address.getStreet();
        }
        if (!StringUtils.isEmpty(address.getMobile())) {
            this.contact = address.getMobile();
        } else {
            this.contact = address.getPhonePrefix() + address.getPhoneMiddle() + address.getPhoneSuffix();
        }
        this.memberEmail = "微信号：" + address.getWeixin() + "，邮箱：" + address.getEmail();
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("type", this.getType());
        obj.put("tempId", this.getTempId());
        obj.put("memberId", this.getMemberId());
        obj.put("crossBorder", this.getCrossBorder());
        obj.put("createDate", DateUtil.second2str2(this.getCreateDate()));
        obj.put("orderSn", this.getOrderSn());
        obj.put("orderStatus", OrderStatus.getStatus(this.getOrderStatus()));
        obj.put("orderStatusCode", this.getOrderStatus());
        obj.put("orderStatusName", this.getOrderStatusName());
        String payType = PaymentTypeEnum.ALIPAY + "," + PaymentTypeEnum.WXAPPPAY;
        // if (this.getCrossBorder() == 0) {
        payType = payType + "," + PaymentTypeEnum.WALLET;
        // }
        obj.put("payParams", payType);
        obj.put("paymentSn", this.getPaymentSn());
        obj.put("paymentType", PaymentTypeEnum.getByCode(this.getPaymentType()));
        obj.put("paymentTypeName",
                this.getOrderStatus() == 1 ? "未支付" : PaymentTypeEnum.getByCode(this.getPaymentType()).getDisplay());
        obj.put("deliverySn", this.getDeliverySn());
        obj.put("deliveryCorpName", this.getDeliveryCorpName());
        obj.put("reciver", this.getReciver());
        obj.put("contact", this.getContact());
        obj.put("province", this.getProvince());
        obj.put("city", this.getCity());
        obj.put("district", this.getDistrict());
        obj.put("street", this.getAddress());
        obj.put("memo", this.getMemo());
        obj.put("cancel", this.isCancel());
        obj.put("productTotalPrice", this.getProductTotalPrice());
        obj.put("productPromotionAmount", this.getProductPromotionAmount());
        obj.put("promotionAmount", this.getPromotionAmount());
        obj.put("totalPay", this.getTotalPay());
        obj.put("totalAmount", this.getTotalAmount());
        obj.put("couponAmount", this.getCouponAmount());
        obj.put("redAmount", this.getRedAmount());
        obj.put("partnerAmount", this.getPartnerAmount());
        obj.put("taxAmount", this.getTaxAmount());
        obj.put("shippingRates", this.getShippingRates());
        obj.put("isOver", this.isOver());
        obj.put("drawee", this.getDrawee());
        return obj;
    }

    public enum OrderType {
        ordinary("普通订单"), distribution("经销订单"), seckill("秒杀订单"), collage("拼团订单"), bargain("砍价订单");
        private String display;

        OrderType(String display) {
            this.display = display;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }
    }

    public enum OrderTerminal {
        Partner("分销订单"), Weibo("微博订单");
        private String display;

        OrderTerminal(String display) {
            this.display = display;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }
    }

    public enum OrderStatus {
        Initial(0, "未处理"), WaitingForPay(1, "待付款"), WaitingForDelivery(3, "待发货"), Delivered(4, "已发货"),
        UserClose(-1, "用户取消"), MallClose(-3, "超时关闭"), Success(8, "订单完结");
        private static Map<Integer, OrderStatus> holder = new HashMap<>();

        static {
            for (OrderStatus orderStatus : values()) {
                holder.put(orderStatus.getCode(), orderStatus);
            }
        }

        private int code;
        private String display;

        OrderStatus(int code) {
            this.code = code;
        }

        OrderStatus(int code, String display) {
            this.code = code;
            this.display = display;
        }

        public static OrderStatus getStatus(int i) {
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
                    return "超时关闭";
                case -1:
                    return "用户取消";
                case 0:
                    return "未处理";
                case 1:
                    return "待付款";
                case 3:
                    return "待发货";
                case 4:
                    return "已发货";
                case 8:
                    return "订单完结";
            }
            return "未知";
        }
    }

}