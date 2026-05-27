package cl.duoc.cordillera.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class BffUsuarioUpdateRequestDTO {

    public String nombre;
    public String apellido;
    public String email;
    public String telefono;
    public LocalDate fechaNacimiento;
    public List<UUID> sucursalIds;
}
