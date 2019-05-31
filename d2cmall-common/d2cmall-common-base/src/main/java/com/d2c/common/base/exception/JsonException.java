package com.d2c.common.base.exception;

/**
 * JSON转换异常
 *
 * @author user
 */
public class JsonException extends BaseException {

    private static final long serialVersionUID = 1L;

    public JsonException() {
        super();
    }

    public JsonException(String message) {
        super(message);
    }

    public JsonException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonException(Throwable cause) {
        super(cause);
    }

}
