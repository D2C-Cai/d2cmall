package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;
import com.d2c.util.string.LoginUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class CouponSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    private String couponName;
    /**
     * 用户ID
     */
    private Long memberId;
    /**
     * 用户账号
     */
    private String loginCode;
    /**
     * 门店优惠券用户ID
     */
    private Long storeMemberId;
    /**
     * 优惠券持有者
     */
    private String couponOwner;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 真实姓名
     */
    private String name;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 订单ID
     */
    private Long orderId;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 消费时间
     */
    private Date consumestimeStart;
    /**
     * 消费时间
     */
    private Date consumestimeEnd;
    /**
     * 领取时间
     */
    private Date claimedtimeStart;
    /**
     * 领取时间
     */
    private Date claimedtimeEnd;
    /**
     * 启用时间
     */
    private Date enabledateStart;
    /**
     * 启用时间
     */
    private Date enabledateEnd;
    /**
     * 到期时间
     */
    private Date expiredateStart;
    /**
     * 到期时间
     */
    private Date expiredateEnd;
    /**
     * 发放时间
     */
    private Date sendDateStart;
    /**
     * 发放时间
     */
    private Date sendDateEnd;
    /**
     * 金额
     */
    private Integer amount;
    /**
     * 所需金额
     */
    private Integer needAmount;
    /**
     * 状态
     */
    private String[] status;
    /**
     * 来源
     */
    private String source;
    /**
     * 优惠券编码
     */
    private String code;
    /**
     * 优惠卷类型
     */
    private String[] type;
    /**
     * 优惠券密码
     */
    private String password;
    /**
     * 管理状态
     */
    private String[] managerStatus;
    /**
     * 定义ID
     */
    private Long defineId;
    /**
     * 优惠券ID
     */
    private Long id;

    /**
     * 分析couponOwner是真实姓名、昵称、邮箱、手机中的哪种
     */
    public void analyseCouponOwner(boolean endWithName) {
        if (StringUtils.isBlank(couponOwner)) {
            return;
        }
        if (endWithName) {
            nickname = null;
            name = couponOwner;
            return;
        }
        if (LoginUtil.checkMobile(couponOwner)) {
            mobile = couponOwner;
            return;
        }
        nickname = couponOwner;
    }

    public Date getSendDateStart() {
        return sendDateStart;
    }

    public void setSendDateStart(Date sendDateStart) {
        this.sendDateStart = sendDateStart;
    }

    public Date getSendDateEnd() {
        return sendDateEnd;
    }

    public void setSendDateEnd(Date sendDateEnd) {
        this.sendDateEnd = sendDateEnd;
    }

    public Date getEnabledateStart() {
        return enabledateStart;
    }

    public void setEnabledateStart(Date enabledateStart) {
        this.enabledateStart = enabledateStart;
    }

    public Date getEnabledateEnd() {
        return enabledateEnd;
    }

    public void setEnabledateEnd(Date enabledateEnd) {
        this.enabledateEnd = enabledateEnd;
    }

    public String getCouponOwner() {
        return couponOwner;
    }

    public void setCouponOwner(String couponOwner) {
        this.couponOwner = couponOwner;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getNeedAmount() {
        return needAmount;
    }

    public void setNeedAmount(Integer needAmount) {
        this.needAmount = needAmount;
    }

    public Date getConsumestimeStart() {
        return consumestimeStart;
    }

    public void setConsumestimeStart(Date consumestimeStart) {
        this.consumestimeStart = consumestimeStart;
    }

    public Date getConsumestimeEnd() {
        return consumestimeEnd;
    }

    public void setConsumestimeEnd(Date consumestimeEnd) {
        this.consumestimeEnd = consumestimeEnd;
    }

    public Date getClaimedtimeStart() {
        return claimedtimeStart;
    }

    public void setClaimedtimeStart(Date claimedtimeStart) {
        this.claimedtimeStart = claimedtimeStart;
    }

    public Date getClaimedtimeEnd() {
        return claimedtimeEnd;
    }

    public void setClaimedtimeEnd(Date claimedtimeEnd) {
        this.claimedtimeEnd = claimedtimeEnd;
    }

    public Date getExpiredateStart() {
        return expiredateStart;
    }

    public void setExpiredateStart(Date expiredateStart) {
        this.expiredateStart = expiredateStart;
    }

    public Date getExpiredateEnd() {
        return expiredateEnd;
    }

    public void setExpiredateEnd(Date expiredateEnd) {
        this.expiredateEnd = expiredateEnd;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String[] getStatus() {
        return status;
    }

    public void setStatus(String[] status) {
        this.status = status;
    }

    public String[] getType() {
        return type;
    }

    public void setType(String[] type) {
        this.type = type;
    }

    public String[] getManagerStatus() {
        return managerStatus;
    }

    public void setManagerStatus(String[] managerStatus) {
        this.managerStatus = managerStatus;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Long getDefineId() {
        return defineId;
    }

    public void setDefineId(Long defineId) {
        this.defineId = defineId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Long getStoreMemberId() {
        return storeMemberId;
    }

    public void setStoreMemberId(Long storeMemberId) {
        this.storeMemberId = storeMemberId;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void analysisStatus() {
        if (this.getStatus() != null && this.getStatus()[0].equals("USED")) {
            this.setStatus(new String[]{"LOCKED", "USED"});
        }
    }

}
