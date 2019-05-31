package com.d2c.member.enums;

public enum PayTypeEnum {
    RECHARGE(1, "充值"), WITHDRAWCASH(2, "提现"), PAY(3, "支付"), TRANSFERS(4, "转账"), PROFIT(5, "分润"),
    REFUND(6, "退款"), CONVERT(7, "积分兑换"), GIVE(8, "赠送"), COMPENSATE(9, "赔偿"), CLEAN(10, "清零"),
    SERVICECHARGE(16, "服务费");
    private int code;
    private String display;

    PayTypeEnum(int code, String display) {
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
