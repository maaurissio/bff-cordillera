package cl.duoc.cordillera.client;

import cl.duoc.cordillera.dto.RolRequestDTO;
import cl.duoc.cordillera.dto.RolResponseDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;
import java.util.UUID;

@Path("/roles")
@RegisterRestClient(configKey = "ms-users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface RolesClient {

    @GET
    List<RolResponseDTO> getAll();

    @GET
    @Path("/{id}")
    RolResponseDTO getById(@PathParam("id") UUID id);

    @POST
    RolResponseDTO create(RolRequestDTO request);

    @PUT
    @Path("/{id}")
    RolResponseDTO update(@PathParam("id") UUID id, RolRequestDTO request);

    @PUT
    @Path("/{id}/activar")
    RolResponseDTO activar(@PathParam("id") UUID id);

    @PUT
    @Path("/{id}/desactivar")
    RolResponseDTO desactivar(@PathParam("id") UUID id);
}
