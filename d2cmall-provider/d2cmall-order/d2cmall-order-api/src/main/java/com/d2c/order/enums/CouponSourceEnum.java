package com.d2c.order.enums;

public enum CouponSourceEnum {
    PASSWD(0, "密码"), D2CMALL(1, "官网"), APP(2, "APP"),
    WXPROMOTION(3, "卡券"), SHARETASK(4, "分享"), PURCHASE(5, "购买");
    private int code;
    private String display;

    CouponSourceEnum(int code, String display) {
        this.code = code;
        this.display = display;
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
