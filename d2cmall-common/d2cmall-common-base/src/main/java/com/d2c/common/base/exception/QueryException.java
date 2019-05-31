package com.d2c.common.base.exception;

/**
 * 查询校验异常
 */
public class QueryException extends BaseException {

    private static final long serialVersionUID = 1L;

    public QueryException() {
        super();
    }

    public QueryException(String message) {
        super(message);
    }

    public QueryException(String message, Throwable cause) {
        super(message, cause);
    }

    public QueryException(Throwable cause) {
        super(cause);
    }

}
