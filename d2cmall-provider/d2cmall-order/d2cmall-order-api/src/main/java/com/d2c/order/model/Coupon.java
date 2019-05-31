package com.d2c.order.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.order.model.CouponDef.Association;
import com.d2c.order.model.CouponDef.CouponType;
import com.d2c.order.model.CouponDef.ManagerStatus;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 优惠券
 */
@Table(name = "o_coupon")
public class Coupon extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 定义ID
     */
    @AssertColumn("优惠券定义ID不能为空")
    private Long defineId;
    /**
     * 编码
     */
    private String code;
    /**
     * 密码
     */
    @JsonIgnore
    private String password;
    /**
     * 金额
     */
    private Integer amount = 0;
    /**
     * 满XX减
     */
    private Integer needAmount = 0;
    /**
     * 名称
     */
    @AssertColumn("优惠券名称不能为空")
    private String name;
    /**
     * 副标题
     */
    private String subTitle;
    /**
     * 使用说明
     */
    private String remark;
    /**
     * 类型
     */
    private String type;
    /**
     * 状态
     */
    private String status;
    /**
     * 发放状态
     */
    private String managerStatus;
    /**
     * 发行时间
     */
    private Date sendDate;
    /**
     * 领用时间
     */
    private Date claimedTime;
    /**
     * 消费时间
     */
    private Date consumesTime;
    /**
     * 使用开始时间
     */
    private Date enableDate;
    /**
     * 使用结束时间
     */
    private Date expireDate;
    /**
     * 跳转链接
     */
    private String redirectUrl;
    /**
     * 是否允许转让
     */
    private Boolean transfer = false;
    /**
     * 会员ID
     */
    private Long memberId = 0L;
    /**
     * 会员账号
     */
    private String loginCode;
    /**
     * 订单ID
     */
    private Long orderId;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 使用门店 ID
     */
    private Long storeId;
    /**
     * 使用门店编号
     */
    private String storeCode;
    /**
     * 使用门店名称
     */
    private String storeName;
    /**
     * 领用来源
     */
    private String source;
    /**
     * 发放人
     */
    private String sendor;
    /**
     * 发放说明
     */
    private String sendmark;
    /**
     * 对应微信卡包的Code
     */
    private String wxCode;
    /**
     * 对应微信卡包的核销时间
     */
    private Date wxConsume = new Date();
    /**
     * 购买金额
     */
    private BigDecimal price;
    /**
     * 关联类型：包含0,不包含1
     */
    private Boolean exclude = false;
    /**
     * 1：全场，2：指定设计师，3：指定商品
     */
    private String checkAssociation = Association.ALL.toString();

    public String getCouponStatusName() {
        if (this.status == null) {
            return null;
        }
        CouponStatus cs = CouponStatus.valueOf(this.status);
        switch (cs.code) {
            case (-1):
                return "已过期";
            case (0):
                return "未领用";
            case (1):
                return "未使用";
            case (2):
                return "已锁定";
            case (3):
                return "已使用";
        }
        return "";
    }

    public String getCouponTypeName() {
        if (this.type == null) {
            return null;
        }
        CouponType ctype = CouponType.valueOf(type);
        switch (ctype.code) {
            case (0):
                return "抵用券";
            case (1):
                return "密码券";
            case (2):
                return "折扣券";
        }
        return "";
    }

    public String getManagerStatusName() {
        if (this.managerStatus == null) {
            return null;
        }
        ManagerStatus ms = ManagerStatus.valueOf(this.managerStatus);
        switch (ms.code) {
            case (0):
                return "未发放";
            case (1):
                return "已发放";
        }
        return "";
    }

    public boolean check() {
        boolean isNotBlanck = type != null && amount != null && StringUtils.isNotBlank(source) && enableDate != null
                && expireDate != null && StringUtils.isNotBlank(name);
        return isNotBlanck;
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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getClaimedTime() {
        return claimedTime;
    }

    public void setClaimedTime(Date claimedTime) {
        this.claimedTime = claimedTime;
    }

    public Date getEnableDate() {
        return enableDate;
    }

    public void setEnableDate(Date enableDate) {
        this.enableDate = enableDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Date getConsumesTime() {
        return consumesTime;
    }

    public void setConsumesTime(Date consumesTime) {
        this.consumesTime = consumesTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getNeedAmount() {
        return needAmount;
    }

    public void setNeedAmount(Integer needAmount) {
        this.needAmount = needAmount;
    }

    public String getManagerStatus() {
        return managerStatus;
    }

    public void setManagerStatus(String managerStatus) {
        this.managerStatus = managerStatus;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getSendor() {
        return sendor;
    }

    public void setSendor(String sendor) {
        this.sendor = sendor;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDefineId() {
        return defineId;
    }

    public void setDefineId(Long defineid) {
        this.defineId = defineid;
    }

    public String getSendmark() {
        return sendmark;
    }

    public void setSendmark(String sendmark) {
        this.sendmark = sendmark;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getWxCode() {
        return wxCode;
    }

    public void setWxCode(String wxCode) {
        this.wxCode = wxCode;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public Boolean getTransfer() {
        return transfer;
    }

    public void setTransfer(Boolean transfer) {
        this.transfer = transfer;
    }

    public Date getWxConsume() {
        return wxConsume;
    }

    public void setWxConsume(Date wxConsume) {
        this.wxConsume = wxConsume;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getExclude() {
        return exclude;
    }

    public void setExclude(Boolean exclude) {
        this.exclude = exclude;
    }

    public String getCheckAssociation() {
        return checkAssociation;
    }

    public void setCheckAssociation(String checkAssociation) {
        this.checkAssociation = checkAssociation;
    }

    public int getExpired() {
        Date now = new Date();
        if (this.getExpireDate() == null) {
            return 0;// 未过期
        }
        if (this.getExpireDate().after(now)) {
            return 0;// 未过期
        }
        return 1;// 过期
    }

    public BigDecimal getCalAmount(BigDecimal validAmout) {
        if (this.getType().equals(CouponType.DISCOUNT.toString())) {
            return validAmout.multiply(new BigDecimal(100 - this.getAmount())).divide(new BigDecimal(100))
                    .setScale(2, BigDecimal.ROUND_HALF_UP);
        } else {
            return new BigDecimal(this.getAmount());
        }
    }

    private boolean getToRedirect() {
        if (StringUtils.isNotBlank(this.getRedirectUrl())) {
            return true;
        }
        if (!this.getExclude() && !Association.ALL.name().equals(this.getCheckAssociation())) {
            return true;
        }
        return false;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("name", this.getName());
        obj.put("type", this.getType());
        obj.put("code", this.getCode());
        obj.put("amount", this.getAmount());
        obj.put("price", this.getPrice());
        obj.put("needAmount", this.getNeedAmount());
        obj.put("remark", this.getRemark());
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        obj.put("enabledate", this.getEnableDate() == null ? "" : fmt.format(this.getEnableDate()));
        obj.put("expiredate", this.getExpireDate() == null ? "" : fmt.format(this.getExpireDate()));
        obj.put("enablestamp", this.getEnableDate() == null ? 0 : this.getEnableDate().getTime());
        obj.put("expirestamp", this.getExpireDate() == null ? 0 : this.getExpireDate().getTime());
        obj.put("url", this.getRedirectUrl());
        obj.put("status", this.getStatus());
        obj.put("isExpired", this.getExpired());
        obj.put("transfer", this.getTransfer() ? 1 : 0);
        obj.put("memberId", this.getMemberId());
        obj.put("toRedirect", this.getToRedirect());
        obj.put("checkAssociation", this.getCheckAssociation());
        return obj;
    }

    /**
     * UNCLAIMED 未领用，CLAIMED 未使用，LOCKED 已锁定，USED 已使用，INVALID 已过期
     */
    public static enum CouponStatus {
        UNCLAIMED(0), CLAIMED(1), LOCKED(2), USED(3), INVALID(-1);
        private int code;

        CouponStatus(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

}