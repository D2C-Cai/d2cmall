package com.d2c.common.api.response;

public enum ErrorCode {
    BUSINESS_ERROR("-1"),
    NO_PERMISSION("-2"),
    NO_LOGIN("-3");
    String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
