package com.d2c.behavior.mongo.model;

import com.d2c.behavior.mongo.enums.AppTerminalEnum;
import com.d2c.behavior.mongo.model.base.PersonBasic;
import com.d2c.common.mongodb.model.SuperMongoDO;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 用户设备表
 *
 * @author wull
 */
@Document
public class PersonDeviceDO extends SuperMongoDO implements PersonBasic {

    private static final long serialVersionUID = -9004635569855548947L;
    /**
     * Unique Device Identifier 设备唯一标识
     * <p> Android: IMEI
     * <br>IOS: UDID
     * <br>WEB: Cookie SessionId
     */
    @Id
    private String udid;
    /**
     * 用户表关联ID
     */
    @Indexed
    private String personId;
    /**
     * APP类型
     * <p>WEB("网页"), APPIOS("苹果APP"), APPANDROID("安卓APP"), XCX("小程序")
     *
     * @see AppTerminalEnum
     */
    @Indexed
    private String appTerminal;
    /**
     * APP版本号
     */
    private String appVersion;
    /**
     * 原APP类型数据
     */
    private String appTerminalVersion;
    /**
     * 第三方账户绑定ID, 一对一绑定
     */
    private String thirdId;
    /**
     * memberId 访客ID
     */
    private Long memberId;
    /**
     * 平台系统 ANDROID, IOS, PC
     */
    private String platform;
    /**
     * 会员Token
     */
    private String token;
    /**
     * 设备类型
     * <p>MOBILE 手机, TABLET 平板电脑, NORMAL 其他普通设备PC电脑
     *
     * @see org.springframework.mobile.device.DeviceType
     */
    private String deviceType;
    /**
     * App版本Id
     */
    private String appVersionId;
    /**
     * 水平像素
     */
    private Integer lpx;
    /**
     * 垂直像素
     */
    private Integer hpx;
    /**
     * 设备商标
     */
    private String deviceBrand;
    /**
     * 设备型号
     */
    private String deviceModel;
    /**
     * 分辨率
     */
    private String resolution;
    /**
     * mac地址
     */
    private String mac;
    /**
     * 是否越狱
     */
    private Boolean isPrisonBreak;
    /**
     * 是否解锁
     */
    private Boolean isCrack;
    /**
     * 语言
     */
    private String language;
    /**
     * 时区
     */
    private String timezone;
    /**
     * 操作系统版本
     */
    private String version;
    /**
     * IP地址
     */
    private String ip;
    /**
     * Header: User-Agent
     */
    private String userAgent;
    //****************************
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户头像
     */
    private String headImg;
    /**
     * 用户性别
     */
    private String sex;
    @Transient
    private PersonThirdDO personThird;
    @Transient
    private AppVersionDO app;

    public PersonDeviceDO() {
    }

    public PersonDeviceDO(String udid) {
        this.udid = udid;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }
    //***************************************

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getThirdId() {
        return thirdId;
    }

    public void setThirdId(String thirdId) {
        this.thirdId = thirdId;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getAppTerminal() {
        return appTerminal;
    }

    public void setAppTerminal(String appTerminal) {
        this.appTerminal = appTerminal;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getLpx() {
        return lpx;
    }

    public void setLpx(Integer lpx) {
        this.lpx = lpx;
    }

    public Integer getHpx() {
        return hpx;
    }

    public void setHpx(Integer hpx) {
        this.hpx = hpx;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Boolean getIsPrisonBreak() {
        return isPrisonBreak;
    }

    public void setIsPrisonBreak(Boolean isPrisonBreak) {
        this.isPrisonBreak = isPrisonBreak;
    }

    public Boolean getIsCrack() {
        return isCrack;
    }

    public void setIsCrack(Boolean isCrack) {
        this.isCrack = isCrack;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAppVersionId() {
        return appVersionId;
    }

    public void setAppVersionId(String appVersionId) {
        this.appVersionId = appVersionId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public PersonThirdDO getPersonThird() {
        return personThird;
    }

    public void setPersonThird(PersonThirdDO personThird) {
        if (personThird != null) {
            this.thirdId = personThird.getUnionId();
            this.memberId = personThird.getMemberId();
        }
        this.personThird = personThird;
    }

    public AppVersionDO getApp() {
        return app;
    }

    public void setApp(AppVersionDO app) {
        if (app != null) {
            this.appVersionId = app.getId();
        }
        this.app = app;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getAppTerminalVersion() {
        return appTerminalVersion;
    }

    public void setAppTerminalVersion(String appTerminalVersion) {
        this.appTerminalVersion = appTerminalVersion;
    }

}
