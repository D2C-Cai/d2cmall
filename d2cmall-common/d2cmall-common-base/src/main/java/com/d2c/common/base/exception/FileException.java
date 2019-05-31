package com.d2c.common.base.exception;

/**
 * 文件处理异常
 *
 * @author wull
 */
public class FileException extends BaseException {

    private static final long serialVersionUID = 1L;

    public FileException() {
        super();
    }

    public FileException(String message) {
        super(message);
    }

    public FileException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileException(Throwable cause) {
        super(cause);
    }

}
