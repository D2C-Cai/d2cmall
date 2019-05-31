package com.d2c.common.base.exception;

/**
 * 业务异常
 */
public class BusinessException extends BaseException {

    private static final long serialVersionUID = 1L;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, String errorCode) {
        super(message, errorCode);
    }

}
