package com.d2c.frame.backweb.control.bak;

import com.d2c.common.api.dto.DtoHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 基础页面控制器
 *
 * @author wull
 */
public abstract class SuperBackControl {

    private static final Logger logger = LoggerFactory.getLogger(SuperBackControl.class);

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public Object handleAllException(RuntimeException e) {
        logger.error("控制器调用RuntimeException异常", e);
        return DtoHandler.error(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Object handleAllException(Exception e) {
        logger.error("控制器调用Exception异常", e);
        return DtoHandler.error(e.getMessage());
    }

}
