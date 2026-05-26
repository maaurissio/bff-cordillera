package cl.duoc.cordillera.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class UsuarioResponseDTO {

    public UUID id;
    public String rut;
    public String dv;
    public String nombre;
    public String apellido;
    public String email;
    public String telefono;
    public LocalDate fechaNacimiento;

    public String estado;
    public List<String> roles;
    public List<String> sucursales;
}
