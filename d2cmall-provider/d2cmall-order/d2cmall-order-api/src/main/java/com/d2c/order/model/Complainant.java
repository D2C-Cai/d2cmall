package com.d2c.order.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 投诉人
 */
@Table(name = "o_complainant")
public class Complainant extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 对应会员Id
     */
    @AssertColumn("会员不能为空")
    private Long memberId;
    /**
     * 对应登录账号
     */
    private String loginCode;
    /**
     * 联系人
     */
    private String name;
    /**
     * 联系电话
     */
    private String tel;
    /**
     * QQ
     */
    private String qq;
    /**
     * 微信
     */
    private String wechat;
    /**
     * email
     */
    private String email;
    /**
     * 性别
     */
    private String sex;
    /**
     * 投诉次数
     */
    private Integer times = 0;

    public Complainant() {
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
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

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

}
