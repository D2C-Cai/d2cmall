package com.d2c.member.enums;

public enum RestrictTypeEnum {
    SHARECOMMENT(1, "买家秀留言"), COMMENT(2, "评价");
    private int code;
    private String display;

    RestrictTypeEnum(int code, String display) {
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
