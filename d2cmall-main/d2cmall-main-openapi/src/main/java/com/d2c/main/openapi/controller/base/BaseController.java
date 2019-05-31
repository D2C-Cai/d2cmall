package com.d2c.main.openapi.controller.base;

import com.d2c.common.api.response.ErrorCode;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.base.exception.AssertException;
import com.d2c.common.base.exception.BaseException;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.frame.web.control.BaseControl;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 基础控制层类
 */
public class BaseController extends BaseControl {

    /**
     * 用户未登录处理
     */
    @ExceptionHandler({NotLoginException.class})
    @ResponseBody
    public Response handleNotLoginException(NotLoginException ex) {
        ErrorResponse res = new ErrorResponse(ex);
        res.setLogin(false);
        res.setErrorCode(ErrorCode.NO_LOGIN);
        return res;
    }

    /**
     * 参数检查异常，业务异常
     */
    @ExceptionHandler({BusinessException.class, AssertException.class})
    @ResponseBody
    public Response handleBusinessException(BaseException ex) {
        return new ErrorResponse(ex);
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseBody
    public Response handleRuntimeException(RuntimeException ex) {
        logger.error(ex.getMessage(), ex);
        ErrorResponse res = new ErrorResponse("系统繁忙，请稍后再试");
        res.setStatus(-9);
        res.put("error", ex.getMessage());
        return res;
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Response handleException(Exception ex) {
        logger.error(ex.getMessage(), ex);
        ErrorResponse res = new ErrorResponse("系统繁忙，请稍后再试");
        res.setStatus(-9);
        res.put("error", ex.getMessage());
        return res;
    }

}
