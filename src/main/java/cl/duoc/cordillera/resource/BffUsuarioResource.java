package cl.duoc.cordillera.resource;

import cl.duoc.cordillera.client.AuthClient;
import cl.duoc.cordillera.client.UsersClient;
import cl.duoc.cordillera.dto.*;
import jakarta.inject.Inject;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.jboss.resteasy.reactive.ClientWebApplicationException;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.UUID;

@Path("/bff/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
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
        userRequest.telefono = "";
        userRequest.fechaNacimiento = null;

        UsuarioResponseDTO creado;
        try {
            creado = usersClient.create(userRequest);
        } catch (ClientWebApplicationException ex) {
            throw new WebApplicationException("Error creando usuario en ms-users", ex.getResponse().getStatus());
        } catch (WebApplicationException ex) {
            throw new WebApplicationException("Error creando usuario en ms-users: " + ex.getMessage(), ex.getResponse().getStatus());
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
    @SecurityRequirement(name = "BearerAuth")
    public List<UsuarioResponseDTO> getAll(@HeaderParam("Authorization") String authHeader) {
        validarToken(authHeader);
        return usersClient.getAll();
    }

    @GET
    @Path("/{id}")
    @SecurityRequirement(name = "BearerAuth")
    public UsuarioResponseDTO getById(@HeaderParam("Authorization") String authHeader, @PathParam("id") UUID id) {
        validarToken(authHeader);
        return usersClient.getById(id);
    }

    @PUT
    @Path("/{id}")
    @SecurityRequirement(name = "BearerAuth")
    public UsuarioResponseDTO update(@HeaderParam("Authorization") String authHeader, @PathParam("id") UUID id, UsuarioUpdateRequestDTO request) {
        validarToken(authHeader);
        return usersClient.update(id, request);
    }

    @PUT
    @Path("/{id}/desactivar")
    @SecurityRequirement(name = "BearerAuth")
    public UsuarioResponseDTO desactivar(@HeaderParam("Authorization") String authHeader, @PathParam("id") UUID id) {
        validarToken(authHeader);
        return usersClient.desactivar(id);
    }

    @PUT
    @Path("/{id}/activar")
    @SecurityRequirement(name = "BearerAuth")
    public UsuarioResponseDTO activar(@HeaderParam("Authorization") String authHeader, @PathParam("id") UUID id) {
        validarToken(authHeader);
        return usersClient.activar(id);
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

    private void validarToken(String authHeader) {
        TokenValidationResponseDTO validation = authClient.validate(authHeader);
        if (!validation.isValido()) {
            throw new WebApplicationException("Token inválido", 401);
        }
    }
}
