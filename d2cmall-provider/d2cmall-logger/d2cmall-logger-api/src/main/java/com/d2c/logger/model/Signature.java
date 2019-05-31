package com.d2c.logger.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 微信签名票据
 */
@Table(name = "sys_signature")
public class Signature extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 用户唯一凭证,主键
     */
    @AssertColumn("appid不能为空")
    private String appid;
    /**
     * 用户密匙
     */
    private String appsecret;
    /**
     * token
     */
    private String token;
    /**
     * 临时票据
     */
    private String ticket;
    /**
     * 备注
     */
    private String info;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
