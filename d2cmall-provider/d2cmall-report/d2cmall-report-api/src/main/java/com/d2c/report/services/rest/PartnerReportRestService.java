package com.d2c.report.services.rest;

import com.d2c.frame.api.constant.MediaConst;

import javax.ws.rs.*;

/**
 * 执行分销商任务
 *
 * @author wull
 */
@Path("partner/report")
public interface PartnerReportRestService {

    @GET
    @Path("build/{back}")
    @Produces(MediaConst.JSON)
    @Consumes(MediaConst.JSON)
    public Object build(@PathParam("back") Integer back);

    @GET
    @Path("test")
    @Produces(MediaConst.JSON)
    @Consumes(MediaConst.JSON)
    public Object test();

}
 