package cl.duoc.cordillera.resource;

import cl.duoc.cordillera.client.AuthClient;
import cl.duoc.cordillera.client.UsersClient;
import cl.duoc.cordillera.dto.BffUsuarioRegisterRequestDTO;
import cl.duoc.cordillera.dto.RegisterRequestDTO;
import cl.duoc.cordillera.dto.TokenValidationResponseDTO;
import cl.duoc.cordillera.dto.UsuarioRequestDTO;
import cl.duoc.cordillera.dto.UsuarioResponseDTO;
import jakarta.inject.Inject;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.jboss.resteasy.reactive.ClientWebApplicationException;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.UUID;

@Path("/bff/usuarios")
@Produces(MediaType.APPLICATION_JSON)
public class BffUsuarioResource {

    @Inject
    @RestClient
    AuthClient authClient;

    @Inject
    @RestClient
    UsersClient usersClient;

    @POST
    @Path("/register")
    public Response register(BffUsuarioRegisterRequestDTO request) {
        if (request.password == null || request.password.isBlank()) {
            throw new WebApplicationException("password es obligatoria", 400);
        }

        UsuarioRequestDTO userRequest = new UsuarioRequestDTO();
        userRequest.rut = request.rut;
        userRequest.dv = request.dv;
        userRequest.nombre = request.nombre;
        userRequest.apellido = request.apellido;
        userRequest.email = request.email;

        UsuarioResponseDTO creado;
        try {
            creado = usersClient.create(userRequest);
        } catch (ClientWebApplicationException ex) {
            throw new WebApplicationException("Error creando usuario en ms-users", ex.getResponse().getStatus());
        }

        RegisterRequestDTO authRequest = new RegisterRequestDTO();
        authRequest.usuarioId = creado.id;
        authRequest.email = creado.email;
        authRequest.password = request.password;

        try {
            authClient.register(authRequest);
        } catch (ClientWebApplicationException ex) {
            usersClient.desactivar(creado.id);
            throw new WebApplicationException("Error registrando credenciales en ms-auth", ex.getResponse().getStatus());
        } catch (WebApplicationException ex) {
            usersClient.desactivar(creado.id);
            throw ex;
        } catch (Exception ex) {
            usersClient.desactivar(creado.id);
            throw new WebApplicationException("No fue posible registrar credenciales", 502);
        }

        return Response.status(Response.Status.CREATED).entity(creado).build();
    }

    @GET
    @Path("/me")
    @SecurityRequirement(name = "BearerAuth")
    public UsuarioResponseDTO getPerfil(@HeaderParam("Authorization") String authHeader) {

        TokenValidationResponseDTO validation = authClient.validate(authHeader);

        if (!validation.isValido()) {
            throw new WebApplicationException("Token inválido", 401);
        }
        UUID usuarioId = validation.getUsuarioId();

        return usersClient.getById(usuarioId);
    }
}
