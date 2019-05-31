package com.d2c.order.third.kd100.core;

public class NoticeResponse {

    /**
     * 结果
     */
    private Boolean result;
    /**
     * 结果状态码
     */
    private String returnCode;
    /**
     * 信息
     */
    private String message;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
