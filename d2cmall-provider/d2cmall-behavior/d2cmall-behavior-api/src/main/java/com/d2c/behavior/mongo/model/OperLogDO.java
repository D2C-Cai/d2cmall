package com.d2c.behavior.mongo.model;

import com.d2c.common.mongodb.model.BaseMongoDO;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 老数据导入表
 *
 * @author wull
 */
@Document
public class OperLogDO extends BaseMongoDO {

    private static final long serialVersionUID = -9004635569855548947L;
    /**
     * 用户ID
     */
    @Indexed
    private String memberId;
    /**
     * 创建日期
     */
    @Indexed
    private Date gmtModified;
    /**
     * 用户名
     */
    private String memberName;
    /**
     * 原日志ID
     */
    private Long logId;
    /**
     * 日志类型
     */
    @Indexed
    private String logType;
    /**
     * 操作对象ID
     */
    private String objectId;
    /**
     * 操作对象号
     */
    private String objectSn;
    /**
     * 操作对象名
     */
    private String objectName;
    /**
     * 父操作对象ID
     */
    private String parentId;
    /**
     * 父操作类型
     */
    private String parentType;
    /**
     * 来源IP
     */
    private String ip;
    /**
     * 页面来源
     */
    private String refer;
    /**
     * 0新建，1：已汇总
     */
    private Integer status;
    /**
     * 设备
     */
    private String device;
    /**
     * 设备标示
     */
    private String deviceToken;
    /**
     * 渠道
     */
    private String channel;
    /**
     * 版本
     */
    private String appVersion;
    /**
     * 广告编号
     */
    private String adSn;
    /**
     * 广告渠道来源
     */
    private String hmsr;

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectSn() {
        return objectSn;
    }

    public void setObjectSn(String objectSn) {
        this.objectSn = objectSn;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRefer() {
        return refer;
    }

    public void setRefer(String refer) {
        this.refer = refer;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAdSn() {
        return adSn;
    }

    public void setAdSn(String adSn) {
        this.adSn = adSn;
    }

    public String getHmsr() {
        return hmsr;
    }

    public void setHmsr(String hmsr) {
        this.hmsr = hmsr;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

}
