package com.d2c.logger.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 会员设备号
 */
@Table(name = "device_member")
public class MemberDevice extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * APP设备
     */
    private String app;
    /**
     * APP版本号
     */
    private String version;
    /**
     * 客户端设备
     */
    private String device;
    /**
     * 客户端操作系统
     */
    private String os;
    /**
     * 客户端IP地址
     */
    private String ip;
    /**
     * 个推ID
     */
    private String clientId;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}
