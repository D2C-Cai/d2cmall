package com.d2c.common.base.exception;

/**
 * 数据校验断言异常
 */
public class AssertException extends BaseException {

    private static final long serialVersionUID = 1L;

    public AssertException() {
        super();
    }

    public AssertException(String message) {
        super(message);
    }

    public AssertException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssertException(Throwable cause) {
        super(cause);
    }

}
