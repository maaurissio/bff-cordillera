package cl.duoc.cordillera.resource;

import cl.duoc.cordillera.client.UsuarioSucursalesClient;
import cl.duoc.cordillera.dto.UsuarioSucursalRequestDTO;
import cl.duoc.cordillera.dto.UsuarioSucursalResponseDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.UUID;

@Path("/bff/usuario-sucursales")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BffUsuarioSucursalResource {

    @Inject
    @RestClient
    UsuarioSucursalesClient usuarioSucursalesClient;

    @GET
    public List<UsuarioSucursalResponseDTO> getAll() {
        return usuarioSucursalesClient.getAll();
    }

    @POST
    public UsuarioSucursalResponseDTO create(UsuarioSucursalRequestDTO request) {
        return usuarioSucursalesClient.create(request);
    }

    @GET
    @Path("/usuario/{usuarioId}")
    public List<UsuarioSucursalResponseDTO> getByUsuario(@PathParam("usuarioId") UUID usuarioId) {
        return usuarioSucursalesClient.getByUsuario(usuarioId);
    }

    @GET
    @Path("/sucursal/{sucursalId}")
    public List<UsuarioSucursalResponseDTO> getBySucursal(@PathParam("sucursalId") UUID sucursalId) {
        return usuarioSucursalesClient.getBySucursal(sucursalId);
    }

    @PUT
    @Path("/{id}/desactivar")
    public UsuarioSucursalResponseDTO desactivar(@PathParam("id") UUID id) {
        return usuarioSucursalesClient.desactivar(id);
    }
}
