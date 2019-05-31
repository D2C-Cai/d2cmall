package com.d2c.member.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 会员详情
 */
@Table(name = "m_member_detail")
public class MemberDetail extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 主账号ID
     */
    private Long memberInfoId;
    /**
     * D2C账号
     */
    private String loginCode;
    /**
     * 电话
     */
    private String phone;
    /**
     * 真实姓名
     */
    private String name;
    /**
     * QQ号
     */
    private String qq;
    /**
     * 微信号
     */
    private String weixin;
    /**
     * 省份
     */
    private Integer regionPrefix;
    /**
     * 城市
     */
    private Integer regionMiddle;
    /**
     * 区县
     */
    private Integer regionSuffix;
    /**
     * 街道
     */
    private String street;
    /**
     * 身高
     */
    private BigDecimal height;
    /**
     * 体重
     */
    private BigDecimal weight;
    /**
     * 胸围
     */
    private BigDecimal chest;
    /**
     * 腰围
     */
    private BigDecimal waistline;
    /**
     * 臀围
     */
    private BigDecimal hipline;
    /**
     * 脚长
     */
    private BigDecimal footLength;
    /**
     * 会员等级
     */
    private Integer level = 0;
    /**
     * 会员积分
     */
    private Integer integration = 0;
    /**
     * 365天之内的累计消费金额
     */
    private Integer additionalAmount = 0;
    /**
     * 最近一次升级时间
     */
    private Date upgradeDate;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getIntegration() {
        return integration;
    }

    public void setIntegration(Integer integration) {
        this.integration = integration;
    }

    public Integer getAdditionalAmount() {
        return additionalAmount;
    }

    public void setAdditionalAmount(Integer additionalAmount) {
        this.additionalAmount = additionalAmount;
    }

    public Date getUpgradeDate() {
        return upgradeDate;
    }

    public void setUpgradeDate(Date upgradeDate) {
        this.upgradeDate = upgradeDate;
    }

    public Long getMemberInfoId() {
        return memberInfoId;
    }

    public void setMemberInfoId(Long memberInfoId) {
        this.memberInfoId = memberInfoId;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getChest() {
        return chest;
    }

    public void setChest(BigDecimal chest) {
        this.chest = chest;
    }

    public BigDecimal getWaistline() {
        return waistline;
    }

    public void setWaistline(BigDecimal waistline) {
        this.waistline = waistline;
    }

    public BigDecimal getHipline() {
        return hipline;
    }

    public void setHipline(BigDecimal hipline) {
        this.hipline = hipline;
    }

    public BigDecimal getFootLength() {
        return footLength;
    }

    public void setFootLength(BigDecimal footLength) {
        this.footLength = footLength;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public Integer getRegionPrefix() {
        return regionPrefix;
    }

    public void setRegionPrefix(Integer regionPrefix) {
        this.regionPrefix = regionPrefix;
    }

    public Integer getRegionMiddle() {
        return regionMiddle;
    }

    public void setRegionMiddle(Integer regionMiddle) {
        this.regionMiddle = regionMiddle;
    }

    public Integer getRegionSuffix() {
        return regionSuffix;
    }

    public void setRegionSuffix(Integer regionSuffix) {
        this.regionSuffix = regionSuffix;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("level", this.getLevel());
        obj.put("integration", this.getIntegration());
        obj.put("additionalAmount", this.getAdditionalAmount() == null ? 0 : this.getAdditionalAmount());
        obj.put("upgradeDate", this.getUpgradeDate());
        return obj;
    }

}
