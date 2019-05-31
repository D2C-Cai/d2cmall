package com.d2c.member.enums;

import org.springframework.util.StringUtils;

public enum DeviceTypeEnum {
    PC(1, "电脑"), MOBILE(2, "手机"), TABLET(3, "平板"),
    APPIOS(4, "苹果APP"), APPANDROID(5, "安卓APP"), XCX(7, "小程序");
    private int code;
    private String display;

    DeviceTypeEnum(int code, String display) {
        this.code = code;
        this.display = display;
    }

    public static String divisionDevice(String terminal) {
        if (StringUtils.isEmpty(terminal)) {
            return DeviceTypeEnum.APPIOS.toString();
        }
        switch (terminal) {
            case "APPIOS":
            case "App.Buyer":
            case "App.Buyer.IOS":
            case "App.Boss.IOS":
                return DeviceTypeEnum.APPIOS.name();
            case "APPANDROID":
            case "App.Buyer/Android":
            case "APP.Buyer.Android":
            case "App.Buyer.Android":
                return DeviceTypeEnum.APPANDROID.name();
            case "XCX":
            case "XCX.iOS":
            case "XCX.Android":
            case "XCX.devtools":
            case "XCX.Manless.iOS":
            case "XCX.Manless.Android":
            case "XCX.Manless.devtools":
                return DeviceTypeEnum.XCX.name();
            case "PC":
                return DeviceTypeEnum.PC.name();
            case "MOBILE":
                return DeviceTypeEnum.MOBILE.name();
            default:
                return DeviceTypeEnum.APPIOS.name();
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }
}
