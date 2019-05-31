package com.d2c.logger.support;

import com.d2c.logger.model.SmsLog.SmsLogType;

import java.io.Serializable;

public class SmsBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 地区编号
     */
    private String nationCode;
    /**
     * 短信类型
     */
    private SmsLogType smsLogType;
    /**
     * 内容
     */
    private String content;
    /**
     * 业务ID
     */
    private Long buzId;
    /**
     * IP
     */
    private String ip;

    public SmsBean() {
    }

    public SmsBean(String nationCode, String mobile, SmsLogType smsLogType, String content) {
        this.nationCode = nationCode;
        this.mobile = mobile;
        this.smsLogType = smsLogType;
        this.content = content;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNationCode() {
        return nationCode;
    }

    public void setNationCode(String nationCode) {
        this.nationCode = nationCode;
    }

    public SmsLogType getSmsLogType() {
        return smsLogType;
    }

    public void setSmsLogType(SmsLogType smsLogType) {
        this.smsLogType = smsLogType;
    }

    public Long getBuzId() {
        return buzId;
    }

    public void setBuzId(Long buzId) {
        this.buzId = buzId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}
