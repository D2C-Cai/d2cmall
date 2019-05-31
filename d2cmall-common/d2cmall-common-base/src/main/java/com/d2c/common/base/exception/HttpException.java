package com.d2c.common.base.exception;

/**
 * HTTP发送异常
 */
public class HttpException extends BaseException {

    private static final long serialVersionUID = 1L;

    public HttpException() {
        super();
    }

    public HttpException(String message) {
        super(message);
    }

    public HttpException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpException(Throwable cause) {
        super(cause);
    }

}
