package com.d2c.behavior.services.rest;

import com.d2c.frame.api.constant.MediaConst;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("spark")
public interface SparkService {

    @GET
    @Path("test")
    @Consumes(value = MediaConst.JSON)
    @Produces(value = MediaConst.JSON)
    public Object test();

}
