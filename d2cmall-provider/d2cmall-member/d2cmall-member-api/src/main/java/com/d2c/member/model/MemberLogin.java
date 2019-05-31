package com.d2c.member.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 会员登录记录
 */
@Table(name = "m_member_login")
public class MemberLogin extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 主账号
     */
    private String loginCode;
    /**
     * 设备
     */
    private String device;
    /**
     * 票据
     */
    private String token;

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
