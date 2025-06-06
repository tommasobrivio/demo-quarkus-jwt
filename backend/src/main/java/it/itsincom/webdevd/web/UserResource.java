package it.itsincom.webdevd.web;

import io.quarkus.runtime.console.ConsoleRuntimeConfig;
import io.smallrye.jwt.auth.principal.JWTCallerPrincipal;
import it.itsincom.webdevd.persistence.model.ApplicationUser;
import it.itsincom.webdevd.service.UserService;
import it.itsincom.webdevd.web.model.CreateUserRequest;
import it.itsincom.webdevd.web.model.UserResponse;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;
import java.util.Set;

@Path("/user")
@DenyAll
public class UserResource {

    private final UserService userService;
    private final JsonWebToken jwt;

    public UserResource(UserService userService, JsonWebToken jwt) {
        this.userService = userService;
        this.jwt = jwt;
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
    public UserResponse stampById(@PathParam("id") int id) {
        return userService.findById(id);
    }

    //modifica(PUT "/{id}") con id obbligatorio, se ruolo user e id è il suo, se admin non si controlla id
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/{id}")
    public Response update(
            @PathParam("id") int id,
            ApplicationUser modifiedUser
    ) {
        Set<String> groups = jwt.getGroups();
        System.out.println(groups);
        boolean response = false;

        if (id != Integer.parseInt(jwt.getSubject()) && groups.contains("USER")) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        response = userService.updateUser(id, modifiedUser);

        return Response.ok(response).build();
    }

    //elimina solo se admin, id obbligatorio
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    public void deleteById(@PathParam("id") int id) {
        userService.deleteUser(id);
    }

}
