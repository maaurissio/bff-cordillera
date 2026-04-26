package cl.duoc.cordillera.resource;

import cl.duoc.cordillera.client.AuthClient;
import cl.duoc.cordillera.client.UsersClient;
import cl.duoc.cordillera.dto.TokenValidationResponseDTO;
import cl.duoc.cordillera.dto.UsuarioResponseDTO;
import jakarta.inject.Inject;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.UUID;

@Path("/bff/usuarios")
@Produces(MediaType.APPLICATION_JSON)
public class BffUsuarioResource {

    @Inject
    @RestClient
    AuthClient authClient;

    @Inject
    @RestClient
    UsersClient usersClient;

    @GET
    @Path("/me")
    @SecurityRequirement(name = "BearerAuth")
    public UsuarioResponseDTO getPerfil(@HeaderParam("Authorization") String authHeader) {

        TokenValidationResponseDTO validation = authClient.validate(authHeader);

        if (!validation.isValido()) {
            throw new WebApplicationException("Token inválido", 401);
        }

        UUID usuarioId = validation.getUsuarioId();

        return usersClient.getById(usuarioId);
    }
}