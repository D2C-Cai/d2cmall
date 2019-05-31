package com.d2c.logger.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * 会员操作日志
 */
@Table(name = "useroperatelog")
public class UserOperateLog extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 广告编号
     */
    private String adSn;
    /**
     * 广告渠道来源
     */
    private String hmsr;
    /**
     * 用户名
     */
    private String memberName;
    /**
     * 用户ID
     */
    private String memberId;
    /**
     * 日志类型
     */
    @Column(name = "log_type")
    @Enumerated(EnumType.STRING)
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
    private int status = 0;
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

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
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

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getRefer() {
        return refer;
    }

    public void setRefer(String refer) {
        if (refer != null && refer.length() > 1000) {
            refer = refer.substring(999);
        }
        this.refer = refer;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
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

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public void createLog(String memberName, String memberId, String logType, String objectName, String ip) {
        this.memberName = memberName;
        this.memberId = memberId;
        this.logType = logType;
        this.objectName = objectName;
        this.ip = ip;
    }

    public static enum OperateLogType {
        /**
         * NAVIGATION:导航搜索 O2OCREATE:门店预约单创建 O2OCONFIRM:门店预约单确认
         * O2OSUBMIT:门店预约单提交. ORDER:订单创建 MAIN:首页 SHOWROOM:设计师品牌 STAR:明星风范
         * STORE:实体店首页 PRODUCTSEARCH:商品图片搜索
         * <p>
         * V_XXX APP各个监控锚点 O_XXX APP各个业务锚点
         */
        NAVIGATION, O2OCREATE, O2OCONFIRM, O2OSUBMIT, ORDER, MAIN, SHOWROOM, STAR, STORE,
        V_SUBMODULE, V_SHARETAG, V_DESIGNERTAG, V_NAVIGATION, V_PROMOTION, V_CROWD, V_ARTICLE, V_SHARE, V_STYLE, V_AREA, V_COUNTRY, V_TOPCATE, V_LIVE, V_PRODUCTCATE,
        V_PRODUCT, V_DESIGNER, V_PRODUCTCOMB, V_PRODUCTSEARCH,
        O_CART, O_ORDER, O_REGISTER, O_LOGIN;

        public static OperateLogType fromString(String value) {
            try {
                return OperateLogType.valueOf(value.toUpperCase());
            } catch (Exception e) {
                throw new IllegalArgumentException(String.format(
                        "Invalid value '%s' for OperateType given! Has to be either 'OperateType' or 'OperateType' (case insensitive).",
                        value), e);
            }
        }
    }

}
