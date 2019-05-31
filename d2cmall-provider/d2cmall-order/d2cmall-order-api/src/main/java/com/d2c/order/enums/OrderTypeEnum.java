package com.d2c.order.enums;

public enum OrderTypeEnum {
    ORDER(1, "订单"), MARGIN(2, "拍卖"), AWARD(3, "抽奖"), PAY(4, "活动"),
    PRESENT(5, "礼物"), BARRAGE(6, "弹幕"), COUPON(7, "优惠券"), COLLAGE(8, "拼团"),
    REFUND(-1, "退款");
    private int code;
    private String display;

    OrderTypeEnum(int code, String display) {
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
