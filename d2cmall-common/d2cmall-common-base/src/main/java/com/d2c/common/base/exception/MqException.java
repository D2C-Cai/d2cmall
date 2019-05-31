package com.d2c.common.base.exception;

/**
 * MQ消息传送异常
 *
 * @author wull
 */
public class MqException extends BaseException {

    private static final long serialVersionUID = 1L;

    public MqException() {
        super();
    }

    public MqException(String message) {
        super(message);
    }

    public MqException(String message, Throwable cause) {
        super(message, cause);
    }

    public MqException(Throwable cause) {
        super(cause);
    }

}
