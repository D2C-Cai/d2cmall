package com.d2c.behavior.services;

import com.d2c.frame.api.constant.MediaConst;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("collabor")
public interface CollaborService {

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

    /**
     * 查询
     */
    @GET
    @Path("find")
    @Produces(MediaConst.JSON)
    @Consumes(MediaConst.JSON)
    public Object find();

    /**
     * 初始化
     */
    @GET
    @Path("loadAll")
    @Produces(MediaConst.JSON)
    @Consumes(MediaConst.JSON)
    public Object loadAll();

}
