package cl.duoc.cordillera.dto;

import java.util.List;
import java.util.UUID;

public class BffSucursalRequestDTO {

    public String nombre;
    public String direccion;
    public UUID ciudadId;
    public List<UUID> usuarioIds;
}
