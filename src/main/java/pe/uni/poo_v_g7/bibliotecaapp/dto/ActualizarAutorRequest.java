package pe.uni.poo_v_g7.bibliotecaapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public final class ActualizarAutorRequest {

    private String nombre = null;

    @JsonIgnore
    private boolean nombrePresent = false;

    public void setNombre(String nombre) {
        this.nombre = nombre;
        this.nombrePresent = true;
    }

    public void unsetNombre() {
        this.nombre = null;
        this.nombrePresent = false;
    }
}