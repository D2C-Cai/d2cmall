package com.d2c.openapi.api.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * 执行分销商任务
 *
 * @author wull
 */
@Path("openapi")
public interface OpenApiRestService {

    @GET
    @Path("test")
    public String test();

}
 