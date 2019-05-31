package com.d2c.behavior.service;

import com.d2c.frame.api.constant.MediaConst;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("operlog")
public interface UserOperateLogService {

    @GET
    @Path("find")
    @Produces(MediaConst.JSON)
    @Consumes(MediaConst.JSON)
    public Object find();

    @GET
    @Path("export")
    @Produces(MediaConst.JSON)
    @Consumes(MediaConst.JSON)
    public Object exportAll();

}
