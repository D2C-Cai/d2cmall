package com.d2c.member.enums;

public enum MemberTypeEnum {
    D2CMall("D2C账号"), QQ("QQ账号"), Taobao("淘宝账号"), Weibo("新浪微博账号"),
    WeixinGz("微信公众平台"), Weixin("微信开放平台"), WeixinXcx("微信小程序"), Mime("米么金服");
    private String display;

    MemberTypeEnum(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }
}