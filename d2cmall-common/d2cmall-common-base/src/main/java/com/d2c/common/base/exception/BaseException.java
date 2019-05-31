package com.d2c.common.base.exception;

public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    /**
     * 错误码
     */
    private String errCode;

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, String errorCode) {
        super(message);
        this.errCode = errorCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

}
