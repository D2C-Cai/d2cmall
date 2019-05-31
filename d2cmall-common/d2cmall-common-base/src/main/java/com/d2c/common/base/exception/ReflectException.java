package com.d2c.common.base.exception;

/**
 * 反射异常
 */
public class ReflectException extends BaseException {

    private static final long serialVersionUID = 1L;

    public ReflectException() {
        super();
    }

    public ReflectException(String message) {
        super(message);
    }

    public ReflectException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflectException(Throwable cause) {
        super(cause);
    }

    public ReflectException(String message, String errorCode) {
        super(message, errorCode);
    }

}
