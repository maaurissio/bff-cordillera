package cl.duoc.cordillera.resource;

import cl.duoc.cordillera.client.AuthClient;
import cl.duoc.cordillera.dto.LoginRequestDTO;
import cl.duoc.cordillera.dto.LoginResponseDTO;
import cl.duoc.cordillera.dto.RefreshTokenRequestDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.ClientWebApplicationException;

@Path("/bff/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BffAuthResource {

    @Inject
    @RestClient
    AuthClient authClient;

    @POST
    @Path("/login")
    public LoginResponseDTO login(LoginRequestDTO request) {
        try {
            return authClient.login(request);
        } catch (ClientWebApplicationException ex) {
            throw new WebApplicationException("Error autenticando usuario", ex.getResponse().getStatus());
        }
    }

    @POST
    @Path("/refresh")
    public LoginResponseDTO refresh(RefreshTokenRequestDTO request) {
        try {
            return authClient.refresh(request);
        } catch (ClientWebApplicationException ex) {
            throw new WebApplicationException("Error renovando sesion", ex.getResponse().getStatus());
        }
    }

    @POST
    @Path("/logout")
    public Response logout(RefreshTokenRequestDTO request) {
        try {
            authClient.logout(request);
            return Response.noContent().build();
        } catch (ClientWebApplicationException ex) {
            throw new WebApplicationException("Error cerrando sesion", ex.getResponse().getStatus());
        }
    }
}
