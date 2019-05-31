package com.d2c.report.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 * 执行分销商任务
 *
 * @author wull
 */
@Path("partner")
public interface PartnerRestService {

    @GET
    @Path("job/sale")
    public String buildSale();

    @GET
    @Path("job/day")
    public String buildSaleDay(@QueryParam("day") Integer day);

    @GET
    @Path("job/month")
    public String buildSaleMonth(@QueryParam("month") Integer month);

    @GET
    @Path("job/rebuild")
    public String rebuildAll();

}
 