package cl.duoc.cordillera.client;

import cl.duoc.cordillera.dto.UsuarioResponseDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.UUID;

@Path("/usuarios")
@RegisterRestClient(configKey = "ms-users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface UsersClient {

    @GET
    @Path("/{id}")
    UsuarioResponseDTO getById(@PathParam("id") UUID id);
}