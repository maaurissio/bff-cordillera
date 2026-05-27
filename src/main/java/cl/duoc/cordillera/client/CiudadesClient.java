package cl.duoc.cordillera.client;

import cl.duoc.cordillera.dto.CiudadResponseDTO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;
import java.util.UUID;

@Path("/ciudades")
@RegisterRestClient(configKey = "ms-users")
@Produces(MediaType.APPLICATION_JSON)
public interface CiudadesClient {

    @GET
    List<CiudadResponseDTO> getAll();

    @GET
    @Path("/region/{regionId}")
    List<CiudadResponseDTO> getByRegion(@PathParam("regionId") UUID regionId);
}
