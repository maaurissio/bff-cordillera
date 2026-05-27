package cl.duoc.cordillera.resource;

import cl.duoc.cordillera.client.CiudadesClient;
import cl.duoc.cordillera.dto.CiudadResponseDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.UUID;

@Path("/bff/ciudades")
@Produces(MediaType.APPLICATION_JSON)
public class BffCiudadResource {

    @Inject
    @RestClient
    CiudadesClient ciudadesClient;

    @GET
    public List<CiudadResponseDTO> getAll() {
        return ciudadesClient.getAll();
    }

    @GET
    @Path("/region/{regionId}")
    public List<CiudadResponseDTO> getByRegion(@PathParam("regionId") UUID regionId) {
        return ciudadesClient.getByRegion(regionId);
    }
}
