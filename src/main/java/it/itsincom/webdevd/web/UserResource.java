package it.itsincom.webdevd.web;

import it.itsincom.webdevd.service.UserService;
import it.itsincom.webdevd.web.model.CreateUserRequest;
import it.itsincom.webdevd.web.model.UserResponse;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/user")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    public UserResponse register(CreateUserRequest request) {
        return userService.createUser(request);
    }

    //diventa stampa con id facoltativo
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "USER"})
    public List<UserResponse> stampAll() {
            return userService.findAll();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/{id}")
    public UserResponse stampById(@PathParam("id") String id) {
        return userService.findById(Long.parseLong(id));
    }

    //modifica(PUT "/{id}") con id obbligatorio, se ruolo user e id è il suo, se admin non si controlla id
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/{id}")
    public UserResponse update(@PathParam("id") String id, CreateUserRequest request) {
        return null;
    }

    //elimina solo se admin, id obbligatorio
}
