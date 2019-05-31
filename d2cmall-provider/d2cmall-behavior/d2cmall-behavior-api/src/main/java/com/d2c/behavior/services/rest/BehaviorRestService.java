package com.d2c.behavior.services.rest;

import com.d2c.frame.api.constant.MediaConst;

import javax.ws.rs.*;

@Path("behavior")
public interface BehaviorRestService {

    @POST
    @Path("test")
    @Produces(MediaConst.JSON)
    @Consumes(MediaConst.JSON)
    public Object test();

    @GET
    @Path("update/event")
    @Produces(MediaConst.JSON)
    @Consumes(MediaConst.JSON)
    public Object updateEvent();

}
