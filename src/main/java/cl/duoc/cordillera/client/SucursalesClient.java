package cl.duoc.cordillera.client;

import cl.duoc.cordillera.dto.SucursalRequestDTO;
import cl.duoc.cordillera.dto.SucursalResponseDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;
import java.util.UUID;

@Path("/sucursales")
@RegisterRestClient(configKey = "ms-users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface SucursalesClient {

    @GET
    List<SucursalResponseDTO> getAll();

    @GET
    @Path("/{id}")
    SucursalResponseDTO getById(@PathParam("id") UUID id);

    @POST
    SucursalResponseDTO create(SucursalRequestDTO request);

    @PUT
    @Path("/{id}")
    SucursalResponseDTO update(@PathParam("id") UUID id, SucursalRequestDTO request);

    @PUT
    @Path("/{id}/activar")
    SucursalResponseDTO activar(@PathParam("id") UUID id);

    @PUT
    @Path("/{id}/desactivar")
    SucursalResponseDTO desactivar(@PathParam("id") UUID id);
}
