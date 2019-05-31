package com.d2c.behavior.mongo.dto;

import com.d2c.behavior.mongo.enums.AppTerminalEnum;
import com.d2c.common.api.dto.MongoDTO;

/**
 * 用户设备表
 *
 * @author wull
 */
public class PersonDeviceDTO extends MongoDTO {

    private static final long serialVersionUID = -9004635569855548947L;
    /**
     * APP类型
     * <p>WEB("网页"), APPIOS("苹果APP"), APPANDROID("安卓APP"), XCX("小程序")
     *
     * @see AppTerminalEnum
     */
    private String appTerminal;
    /**
     * APP版本
     */
    private String appVersion;
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
     * 是否破解
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
     * 对应版本
     */
    private String version;

    public String getAppTerminal() {
        return appTerminal;
    }

    public void setAppTerminal(String appTerminal) {
        this.appTerminal = appTerminal;
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

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

}
