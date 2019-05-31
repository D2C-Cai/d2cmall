package com.d2c.order.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 收货地址
 */
@Table(name = "o_address")
public class Address extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 用户名
     */
    @AssertColumn("收货名称不能为空")
    private String name;
    /**
     * 街道
     */
    private String street;
    /**
     * 邮政编码
     */
    private String zipCode;
    /**
     * 电话号码 xxx-xxx-xxxxxxxx
     */
    private String phonePrefix;
    private String phoneMiddle;
    private String phoneSuffix;
    /**
     * 手机号
     */
    @AssertColumn("收货手机号不能为空")
    private String mobile;
    /**
     * 默认
     */
    private boolean isdefault;
    /**
     * 用户ID
     */
    @AssertColumn("会员Id不能为空")
    private Long memberId;
    /**
     * 省号
     */
    private String regionPrefix;
    /**
     * 市号
     */
    private String regionMiddle;
    /**
     * 区号
     */
    private String regionSuffix;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 微信号
     */
    private String weixin;
    /**
     * X轴
     */
    private BigDecimal longitude;
    /**
     * Y轴
     */
    private BigDecimal latitude;

    public String getName() {
        return name == null ? "" : name.replaceAll("\'", "&apos;").replaceAll("\"", "&quot;");
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street == null ? "" : street.replaceAll("\'", "&apos;").replaceAll("\"", "&quot;");
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipcode) {
        this.zipCode = zipcode;
    }

    public String getPhonePrefix() {
        return phonePrefix;
    }

    public void setPhonePrefix(String phonePrefix) {
        this.phonePrefix = phonePrefix;
    }

    public String getPhoneMiddle() {
        return phoneMiddle;
    }

    public void setPhoneMiddle(String phoneMiddle) {
        this.phoneMiddle = phoneMiddle;
    }

    public String getPhoneSuffix() {
        return phoneSuffix;
    }

    public void setPhoneSuffix(String phoneSuffix) {
        this.phoneSuffix = phoneSuffix;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(boolean isdefault) {
        this.isdefault = isdefault;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getRegionPrefix() {
        return regionPrefix;
    }

    public void setRegionPrefix(String regionPrefix) {
        this.regionPrefix = regionPrefix;
    }

    public String getRegionMiddle() {
        return regionMiddle;
    }

    public void setRegionMiddle(String regionMiddle) {
        this.regionMiddle = regionMiddle;
    }

    public String getRegionSuffix() {
        return regionSuffix;
    }

    public void setRegionSuffix(String regionSuffix) {
        this.regionSuffix = regionSuffix;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
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

}
