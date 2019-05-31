package com.d2c.common.base.exception;

/**
 * 未登录异常
 */
public class NotLoginException extends BaseException {

    private static final long serialVersionUID = 1L;
    /**
     * 0.未登录 1.未绑定
     */
    private int code;

    public NotLoginException() {
        super();
    }

    public NotLoginException(String message) {
        super(message);
    }

    public NotLoginException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
