package it.itsincom.webdevd.web;

import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/protected")
@DenyAll
public class ProtectedResource {

    @GET
    @Path("/admin")
    @RolesAllowed("ADMIN")
    @Produces(MediaType.TEXT_PLAIN)
    public String onlyForAdmins() {
        return "Only for admins!";
    }

    @GET
    @Path("/user")
    @RolesAllowed({"ADMIN", "USER"})
    @Produces(MediaType.TEXT_PLAIN)
    public String onlyForUsers() {
        return "Only for users!";
    }

    @GET
    @Path("/all")
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public String forEveryone() {
        return "For everyone!";
    }
}
