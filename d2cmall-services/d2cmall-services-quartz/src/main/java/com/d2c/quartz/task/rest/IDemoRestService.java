package com.d2c.quartz.task.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("demo")
public interface IDemoRestService {

    @GET
    @Path("/test")
    public void test();

}
