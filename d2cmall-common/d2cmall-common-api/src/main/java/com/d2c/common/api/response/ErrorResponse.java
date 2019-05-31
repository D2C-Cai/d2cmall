package com.d2c.common.api.response;

/**
 * 一般错误提示
 */
public class ErrorResponse extends Response {

    private static final long serialVersionUID = 1L;
    private String errorCode = "";

    public ErrorResponse(Exception e) {
        this(e.getMessage());
    }

    public ErrorResponse(String message) {
        this.errorCode = ErrorCode.BUSINESS_ERROR.getCode();
        this.message = message;
        this.status = -1;
    }

    public ErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
        this.status = -1;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode.getCode();
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
