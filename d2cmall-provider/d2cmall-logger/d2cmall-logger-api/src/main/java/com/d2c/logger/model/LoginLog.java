package com.d2c.logger.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 登录日志
 */
@Table(name = "log_login")
public class LoginLog extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 业务类型
     */
    private String type;
    /**
     * 操作信息
     */
    private String info;
    /**
     * 操作IP
     */
    private String ip;
    /**
     * 设备标识
     */
    private String device;
    /**
     * app终端版本
     */
    private String version;
    /**
     * web终端标识
     */
    private String userAgent;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

}