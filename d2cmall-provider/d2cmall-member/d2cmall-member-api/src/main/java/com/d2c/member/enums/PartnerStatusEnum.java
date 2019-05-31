package com.d2c.member.enums;

public enum PartnerStatusEnum {
    TRY_TIME(0, "试用期"), NORMAL(1, "正常"), NO_PASS(-1, "未通过"), CLOSE(-9, "永久关闭");
    private int code;
    private String display;

    PartnerStatusEnum(int code, String display) {
        this.code = code;
        this.display = display;
    }

    @Override
    public String toString() {
        return display;
    }

    public int getCode() {
        return code;
    }

    public String getDisplay() {
        return display;
    }
}
