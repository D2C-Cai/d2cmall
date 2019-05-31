package com.d2c.similar.service.rest;

import com.d2c.frame.api.constant.MediaConst;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * 商品相似度
 *
 * @author wull
 */
@Path("similar")
public interface SimilarRestService {

    @GET
    @Path("init")
    @Produces(MediaConst.JSON)
    @Consumes(MediaConst.JSON)
    public Object init();

    @GET
    @Path("test")
    @Produces(MediaConst.JSON)
    @Consumes(MediaConst.JSON)
    public Object test();

    @GET
    @Path("start")
    @Produces(MediaConst.JSON)
    @Consumes(MediaConst.JSON)
    public Object start();

    @GET
    @Path("reset")
    @Produces(MediaConst.JSON)
    @Consumes(MediaConst.JSON)
    public Object reset();

}
