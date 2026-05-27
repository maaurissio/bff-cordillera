package cl.duoc.cordillera.client;

import cl.duoc.cordillera.dto.UsuarioSucursalRequestDTO;
import cl.duoc.cordillera.dto.UsuarioSucursalResponseDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;
import java.util.UUID;

@Path("/usuario-sucursales")
@RegisterRestClient(configKey = "ms-users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface UsuarioSucursalesClient {

    @GET
    List<UsuarioSucursalResponseDTO> getAll();

    @POST
    UsuarioSucursalResponseDTO create(UsuarioSucursalRequestDTO request);

    @GET
    @Path("/usuario/{usuarioId}")
    List<UsuarioSucursalResponseDTO> getByUsuario(@PathParam("usuarioId") UUID usuarioId);

    @GET
    @Path("/sucursal/{sucursalId}")
    List<UsuarioSucursalResponseDTO> getBySucursal(@PathParam("sucursalId") UUID sucursalId);

    @PUT
    @Path("/{id}/desactivar")
    UsuarioSucursalResponseDTO desactivar(@PathParam("id") UUID id);
}
