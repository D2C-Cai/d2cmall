package com.d2c.product.third.upyun.core;

public class Result {

    private boolean succeed;
    private int code;
    private String msg;

    public boolean isSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Result{" +
                "succeed=" + succeed +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

}

