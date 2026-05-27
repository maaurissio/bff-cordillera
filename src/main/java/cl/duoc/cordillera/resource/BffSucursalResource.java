package cl.duoc.cordillera.resource;

import cl.duoc.cordillera.client.SucursalesClient;
import cl.duoc.cordillera.client.UsuarioSucursalesClient;
import cl.duoc.cordillera.dto.BffSucursalRequestDTO;
import cl.duoc.cordillera.dto.SucursalRequestDTO;
import cl.duoc.cordillera.dto.SucursalResponseDTO;
import cl.duoc.cordillera.dto.UsuarioSucursalRequestDTO;
import cl.duoc.cordillera.dto.UsuarioSucursalResponseDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/bff/sucursales")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BffSucursalResource {

    @Inject
    @RestClient
    SucursalesClient sucursalesClient;

    @Inject
    @RestClient
    UsuarioSucursalesClient usuarioSucursalesClient;

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
    public SucursalResponseDTO create(BffSucursalRequestDTO request) {
        SucursalRequestDTO createRequest = new SucursalRequestDTO();
        createRequest.nombre = request.nombre;
        createRequest.direccion = request.direccion;
        createRequest.ciudadId = request.ciudadId;

        SucursalResponseDTO created = sucursalesClient.create(createRequest);
        syncSucursalUsuarios(created.id, request.usuarioIds);
        return sucursalesClient.getById(created.id);
    }

    @PUT
    @Path("/{id}")
    public SucursalResponseDTO update(@PathParam("id") UUID id, BffSucursalRequestDTO request) {
        SucursalRequestDTO updateRequest = new SucursalRequestDTO();
        updateRequest.nombre = request.nombre;
        updateRequest.direccion = request.direccion;
        updateRequest.ciudadId = request.ciudadId;

        SucursalResponseDTO updated = sucursalesClient.update(id, updateRequest);
        syncSucursalUsuarios(id, request.usuarioIds);
        return sucursalesClient.getById(updated.id);
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

    private void syncSucursalUsuarios(UUID sucursalId, List<UUID> usuarioIds) {
        Set<UUID> desiredIds = sanitizeIds(usuarioIds);
        List<UsuarioSucursalResponseDTO> currentAssignments = usuarioSucursalesClient.getBySucursal(sucursalId);

        for (UsuarioSucursalResponseDTO assignment : currentAssignments) {
            if (!desiredIds.contains(assignment.usuarioId)) {
                usuarioSucursalesClient.desactivar(assignment.id);
            }
        }

        Set<UUID> currentIds = currentAssignments.stream()
                .map(assignment -> assignment.usuarioId)
                .collect(Collectors.toSet());

        for (UUID usuarioId : desiredIds) {
            if (currentIds.contains(usuarioId)) continue;
            UsuarioSucursalRequestDTO request = new UsuarioSucursalRequestDTO();
            request.usuarioId = usuarioId;
            request.sucursalId = sucursalId;
            usuarioSucursalesClient.create(request);
        }
    }

    private Set<UUID> sanitizeIds(List<UUID> ids) {
        if (ids == null) return Set.of();
        return ids.stream()
                .filter(id -> id != null)
                .collect(Collectors.toSet());
    }
}
