package com.d2c.flame.enums;

import java.util.HashMap;
import java.util.Map;

public enum CollageErrorCodeEnum {
    COLLAGE_1001("你已参加该商品其他团，是否去该团详情", "1001"),
    COLLAGE_1002("该拼团活动开团数已到上限，看看其他已经开的团吧", "1002"),
    COLLAGE_1003("你已超过该商品最大成团数", "1003");
    private static Map<String, CollageErrorCodeEnum> holder = new HashMap<>();

    static {
        for (CollageErrorCodeEnum errorCode : values()) {
            holder.put(errorCode.getCode(), errorCode);
        }
    }

    /**
     * 错误信息
     */
    private String message;
    /**
     * 错误码
     */
    private String code;

    CollageErrorCodeEnum(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
