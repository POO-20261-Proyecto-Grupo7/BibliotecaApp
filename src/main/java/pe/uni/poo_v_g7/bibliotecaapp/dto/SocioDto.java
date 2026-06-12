package pe.uni.poo_v_g7.bibliotecaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.uni.poo_v_g7.bibliotecaapp.entity.Socio;

/**
 * Objeto de transferencia para {@link Socio}.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class SocioDto {

    private Integer idSocio;
    private String nombres;
    private String apellidos;
    private String dni;
    private String telefono;
    private String correo;
    private String direccion;

    private String fechaRegistro;
    private Boolean habilitado;
}
