package com.d2c.behavior.services.rest;

import com.d2c.frame.api.constant.MediaConst;

import javax.ws.rs.*;

@Path("depict")
public interface DepictRestService {

    /**
     * 查询
     */
    @GET
    @Path("find")
    @Produces(MediaConst.JSON)
    @Consumes(MediaConst.JSON)
    public Object find();

    @GET
    @Path("find/{tagId}")
    @Produces(MediaConst.JSON)
    @Consumes(MediaConst.JSON)
    public Object findPage(@PathParam("tagId") String tagId);

    /**
     * 初始化
     */
    @GET
    @Path("init")
    @Produces(MediaConst.JSON)
    @Consumes(MediaConst.JSON)
    public Object init();

    /**
     * 清理
     */
    @GET
    @Path("clean")
    public String clean();

    @GET
    @Path("cleanData")
    public String cleanData();

    @GET
    @Path("order/{limit}")
    @Produces(MediaConst.JSON)
    public Object loadOrder(@PathParam("limit") Integer limit);

    @GET
    @Path("count")
    @Produces(MediaConst.JSON)
    @Consumes(MediaConst.JSON)
    public Object count();

}
