package cl.duoc.cordillera.client;

import cl.duoc.cordillera.dto.LoginRequestDTO;
import cl.duoc.cordillera.dto.LoginResponseDTO;
import cl.duoc.cordillera.dto.RegisterRequestDTO;
import cl.duoc.cordillera.dto.RefreshTokenRequestDTO;
import cl.duoc.cordillera.dto.TokenValidationResponseDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/auth")
@RegisterRestClient(configKey = "ms-auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface AuthClient {

    @POST
    @Path("/login")
    LoginResponseDTO login(LoginRequestDTO request);

    @POST
    @Path("/register")
    void register(RegisterRequestDTO request);

    @POST
    @Path("/refresh")
    LoginResponseDTO refresh(RefreshTokenRequestDTO request);

    @POST
    @Path("/logout")
    void logout(RefreshTokenRequestDTO request);

    @POST
    @Path("/validate")
    TokenValidationResponseDTO validate(@HeaderParam("Authorization") String authHeader);
}
