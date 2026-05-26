package cl.duoc.cordillera.resource;

import cl.duoc.cordillera.client.SucursalesClient;
import cl.duoc.cordillera.dto.SucursalRequestDTO;
import cl.duoc.cordillera.dto.SucursalResponseDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.UUID;

@Path("/bff/sucursales")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BffSucursalResource {

    @Inject
    @RestClient
    SucursalesClient sucursalesClient;

    @GET
    public List<SucursalResponseDTO> getAll() {
        return sucursalesClient.getAll();
    }

    @GET
    @Path("/{id}")
    public SucursalResponseDTO getById(@PathParam("id") UUID id) {
        return sucursalesClient.getById(id);
    }

    @POST
    public SucursalResponseDTO create(SucursalRequestDTO request) {
        return sucursalesClient.create(request);
    }

    @PUT
    @Path("/{id}")
    public SucursalResponseDTO update(@PathParam("id") UUID id, SucursalRequestDTO request) {
        return sucursalesClient.update(id, request);
    }

    @PUT
    @Path("/{id}/activar")
    public SucursalResponseDTO activar(@PathParam("id") UUID id) {
        return sucursalesClient.activar(id);
    }

    @PUT
    @Path("/{id}/desactivar")
    public SucursalResponseDTO desactivar(@PathParam("id") UUID id) {
        return sucursalesClient.desactivar(id);
    }
}
