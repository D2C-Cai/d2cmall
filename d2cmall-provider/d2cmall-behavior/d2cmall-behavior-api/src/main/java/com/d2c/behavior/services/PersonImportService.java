package com.d2c.behavior.services;

import com.d2c.frame.api.constant.MediaConst;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("personImport")
public interface PersonImportService {

    @GET
    @Path("init")
    @Produces(MediaConst.JSON)
    @Consumes(MediaConst.JSON)
    public Object init();

    @GET
    @Path("person")
    public void importPerson();

    @GET
    @Path("address")
    public void importAddress();

}
