package cl.duoc.cordillera.resource;

import cl.duoc.cordillera.client.RegionesClient;
import cl.duoc.cordillera.dto.RegionResponseDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@Path("/bff/regiones")
@Produces(MediaType.APPLICATION_JSON)
public class BffRegionResource {

    @Inject
    @RestClient
    RegionesClient regionesClient;

    @GET
    public List<RegionResponseDTO> getAll() {
        return regionesClient.getAll();
    }
}
