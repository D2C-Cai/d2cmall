package com.d2c.member.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 经销商（折扣）
 */
@Table(name = "m_distributor")
public class Distributor extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 登录账号
     */
    private String loginCode;
    /**
     * 0停用，1启用
     */
    private Integer status = 0;
    /**
     * 业绩指标
     */
    private BigDecimal performance = new BigDecimal(0);
    /**
     * 发货方式
     */
    private Integer shipType = 0;
    /**
     * 免运费:1是，0否
     */
    private Integer freeShipFee = 0;
    /**
     * 退款退货:0不允许，1允许
     */
    private Integer reship = 0;
    /**
     * 用户ID
     */
    @AssertColumn("会员ID不能为空")
    private Long memberId;
    /**
     * E-mail
     */
    private String email;
    /**
     * 性别
     */
    private String sex = "女";
    /**
     * 真实姓名
     */
    private String name;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * QQ号
     */
    private String qq;
    /**
     * 微信号
     */
    private String weixin;
    /**
     * 地址
     */
    private String address;
    /**
     * 折扣组
     */
    private Long groupId;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public BigDecimal getPerformance() {
        return performance;
    }

    public void setPerformance(BigDecimal performance) {
        this.performance = performance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getShipType() {
        return shipType;
    }

    public void setShipType(Integer shipType) {
        this.shipType = shipType;
    }

    public Integer getFreeShipFee() {
        return freeShipFee;
    }

    public void setFreeShipFee(Integer freeShipFee) {
        this.freeShipFee = freeShipFee;
    }

    public Integer getReship() {
        return reship;
    }

    public void setReship(Integer reship) {
        this.reship = reship;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

}
