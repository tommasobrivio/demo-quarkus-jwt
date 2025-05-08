package it.itsincom.webdevd.web;

import io.smallrye.jwt.build.Jwt;
import it.itsincom.webdevd.service.UserService;
import it.itsincom.webdevd.web.model.AccessTokenResponse;
import it.itsincom.webdevd.web.model.LoginRequest;
import it.itsincom.webdevd.web.model.TokenResponse;
import it.itsincom.webdevd.web.model.UserResponse;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.Claims;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;

@Path("/auth")
public class AuthenticationResource {

    private final UserService userService;

    public AuthenticationResource(UserService userService) {
        this.userService = userService;
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest request) {
        UserResponse user = userService.authenticate(request.getUsername(), request.getPassword());
        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .build();
        }
        String accessToken = getAccessToken(user);
        String refreshToken = getRefreshToken(user);
        return Response.ok(new TokenResponse(accessToken, refreshToken)).build();
    }

    @POST
    @Path("/refresh")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"refresh_token"})
    public Response refresh(@Context SecurityContext securityContext) {
        String username = securityContext.getUserPrincipal().getName();
        UserResponse user = userService.getUserByUsername(username);
        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .build();
        }
        String accessToken = getAccessToken(user);
        return Response.ok(new AccessTokenResponse(accessToken)).build();
    }




    private String getAccessToken(UserResponse user) {
        String token = Jwt
                .issuer("demo-quarkus-jwt")
                .subject(String.valueOf(user.getId()))
                .upn(user.getUsername())
                .groups(Set.of(user.getRole(), "access_token"))
                .claim(Claims.nickname.name(), user.getUsername())
                .expiresIn(Duration.ofMinutes(15))
                .issuedAt(Instant.now())
                .sign();
        return token;
    }

    private String getRefreshToken(UserResponse user) {
        String token = Jwt
                .issuer("demo-quarkus-jwt")
                .subject(String.valueOf(user.getId()))
                .upn(user.getUsername())
                .groups(Set.of(user.getRole(), "refresh_token"))
                .claim(Claims.nickname.name(), user.getUsername())
                .expiresIn(Duration.ofHours(1))
                .issuedAt(Instant.now())
                .sign();
        return token;
    }
}
