package com.d2c.behavior.mongo.enums;

/**
 * APP类型
 *
 * @author wull
 */
public enum AppTerminalEnum {
    WEB("网页"), APPIOS("苹果APP"), APPANDROID("安卓APP"), XCX("小程序");
    String display;

    AppTerminalEnum(String display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return display;
    }
}
