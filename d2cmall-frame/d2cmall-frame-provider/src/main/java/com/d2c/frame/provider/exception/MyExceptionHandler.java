package com.d2c.frame.provider.exception;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.d2c.common.api.dto.DtoHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class MyExceptionHandler implements ExceptionMapper<Exception> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Response toResponse(Exception e) {
        logger.error("Dubbo/Api调用异常:" + e.getMessage(), e);
        return Response.ok(DtoHandler.error(e.getMessage()), ContentType.APPLICATION_JSON_UTF_8).build();
    }

}
