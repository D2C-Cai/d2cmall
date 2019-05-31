package com.d2c.similar.service.rest;

import com.d2c.frame.api.constant.MediaConst;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * 商品推荐值
 *
 * @author wull
 */
@Path("recom")
public interface RecomRestService {

    /**
     * 重建推荐规则
     */
    @GET
    @Path("reset/rule")
    @Produces(MediaConst.JSON)
    @Consumes(MediaConst.JSON)
    public Object resetRules();

    /**
     * 清除运营推荐
     */
    @GET
    @Path("clean/oper")
    @Produces(MediaConst.JSON)
    @Consumes(MediaConst.JSON)
    public Object cleanOperRecom();

    /**
     * 数据重建
     */
    @GET
    @Path("reset")
    @Produces(MediaConst.JSON)
    @Consumes(MediaConst.JSON)
    public Object reset();

}
