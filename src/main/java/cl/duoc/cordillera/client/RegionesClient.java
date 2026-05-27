package cl.duoc.cordillera.client;

import cl.duoc.cordillera.dto.RegionResponseDTO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/regiones")
@RegisterRestClient(configKey = "ms-users")
@Produces(MediaType.APPLICATION_JSON)
public interface RegionesClient {

    @GET
    List<RegionResponseDTO> getAll();
}
