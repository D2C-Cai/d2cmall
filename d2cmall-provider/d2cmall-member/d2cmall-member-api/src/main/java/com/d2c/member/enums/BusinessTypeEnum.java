package com.d2c.member.enums;

public enum BusinessTypeEnum {
    ORDER(1, "订单"), POINT(2, "积分"), PAY(3, "余额"), PROMOTION(4, "活动"),
    CASHCARD(5, "D2C卡"), MARGIN(6, "拍卖"), AFTERSALE(7, "售后"), AWARD(8, "抽奖"),
    PRESENT(9, "礼物"), BARRAGE(10, "弹幕"), COUPON(11, "优惠券"), COLLAGE(12, "拼团"), PARTNER(13, "分销"),
    SHAREREDPACKETS(14, "分享红包");
    private int code;
    private String display;

    BusinessTypeEnum(int code, String display) {
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
