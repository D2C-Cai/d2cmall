package com.d2c.member.enums;

public enum PartnerLevelEnum {
    AM(0, "AM"), DM(1, "DM"), BUYER(2, "买手");
    private int code;
    private String display;

    PartnerLevelEnum(int code, String display) {
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
