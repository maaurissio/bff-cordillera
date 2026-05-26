package cl.duoc.cordillera.resource;

import cl.duoc.cordillera.client.RolesClient;
import cl.duoc.cordillera.dto.RolRequestDTO;
import cl.duoc.cordillera.dto.RolResponseDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.UUID;

@Path("/bff/roles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BffRolResource {

    @Inject
    @RestClient
    RolesClient rolesClient;

    @GET
    public List<RolResponseDTO> getAll() {
        return rolesClient.getAll();
    }

    @GET
    @Path("/{id}")
    public RolResponseDTO getById(@PathParam("id") UUID id) {
        return rolesClient.getById(id);
    }

    @POST
    public RolResponseDTO create(RolRequestDTO request) {
        return rolesClient.create(request);
    }

    @PUT
    @Path("/{id}")
    public RolResponseDTO update(@PathParam("id") UUID id, RolRequestDTO request) {
        return rolesClient.update(id, request);
    }

    @PUT
    @Path("/{id}/activar")
    public RolResponseDTO activar(@PathParam("id") UUID id) {
        return rolesClient.activar(id);
    }

    @PUT
    @Path("/{id}/desactivar")
    public RolResponseDTO desactivar(@PathParam("id") UUID id) {
        return rolesClient.desactivar(id);
    }
}
