package cl.duoc.cordillera.dto;

import java.util.UUID;

public class TokenValidationResponseDTO {

    private boolean valido;
    private UUID usuarioId;
    private String email;

    public boolean isValido() {
        return valido;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public String getEmail() {
        return email;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}