package pe.uni.poo_v_g7.bibliotecaapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public final class ActualizarCategoriaRequest {

    private String nombre = null;
    @JsonIgnore
    private boolean nombrePresent = false;

    public void setNombre(String nombre) {
        this.nombre = nombre;
        nombrePresent = true;
    }

    public void unsetNombre() {
        nombre = null;
        nombrePresent = false;
    }

    private String descripcion = null;
    @JsonIgnore
    private boolean descripcionPresent = false;

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        descripcionPresent = true;
    }

    public void unsetDescripcion() {
        descripcion = null;
        descripcionPresent = false;
    }
}