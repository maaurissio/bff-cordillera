package cl.duoc.cordillera.dto;

import java.util.List;
import java.util.UUID;

public class UsuarioResponseDTO {

    public UUID id;
    public String rut;
    public String nombre;
    public String apellido;
    public String email;

    public List<String> roles;
}