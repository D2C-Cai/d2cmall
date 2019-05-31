package com.d2c.common.base.exception;

/**
 * 线程通用异常
 *
 * @author wull
 */
public class ThreadException extends BaseException {

    private static final long serialVersionUID = 1L;

    public ThreadException() {
        super();
    }

    public ThreadException(String message) {
        super(message);
    }

    public ThreadException(String message, Throwable cause) {
        super(message, cause);
    }

    public ThreadException(Throwable cause) {
        super(cause);
    }

}
