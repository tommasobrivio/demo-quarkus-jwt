package it.itsincom.webdevd.web;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/protected")
public class ProtectedResource {

    @GET
    @Path("/admin")
    @Produces(MediaType.TEXT_PLAIN)
    public String onlyForAdmins() {
        return "Only for admins!";
    }

    @GET
    @Path("/user")
    @Produces(MediaType.TEXT_PLAIN)
    public String onlyForUsers() {
        return "Only for users!";
    }

    @GET
    @Path("/all")
    @Produces(MediaType.TEXT_PLAIN)
    public String forEveryone() {
        return "For everyone!";
    }
}
