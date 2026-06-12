package pe.uni.poo_v_g7.bibliotecaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto de transferencia para {@link pe.uni.poo_v_g7.bibliotecaapp.entity.Cliente}.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class ClienteDto {

    private Integer idCliente;
    private String nombres;
    private String apellidos;
    private String dni;
    private String telefono;
    private String correo;
    private String direccion;

    private String fechaRegistro;
    private Boolean habilitado;
}
