package pe.uni.poo_v_g7.bibliotecaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto de transferencia para {@link pe.uni.poo_v_g7.bibliotecaapp.entity.Ejemplar}.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class EjemplarDto {

    private Integer idEjemplar;
    private UbicacionDto ubicacion;
    private LibroDto libro;
    private String codigo;
    private String estado;
}
