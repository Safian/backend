package com.safian.backend.user;

import jakarta.ws.rs.*;

@Path("/user/hello-world")
public class Resource {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }
}