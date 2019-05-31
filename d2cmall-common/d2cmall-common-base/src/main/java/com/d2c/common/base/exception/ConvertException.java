package com.d2c.common.base.exception;

/**
 * 类型转换异常
 */
public class ConvertException extends BaseException {

    private static final long serialVersionUID = 1L;

    public ConvertException() {
        super();
    }

    public ConvertException(String message) {
        super(message);
    }

    public ConvertException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConvertException(Throwable cause) {
        super(cause);
    }

}
