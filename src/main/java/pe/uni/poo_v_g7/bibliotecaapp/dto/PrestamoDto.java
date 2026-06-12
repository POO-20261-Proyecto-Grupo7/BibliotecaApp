package pe.uni.poo_v_g7.bibliotecaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.uni.poo_v_g7.bibliotecaapp.entity.EstadoPrestamo;

import java.time.LocalDateTime;

/**
 * Objeto de transferencia para {@link pe.uni.poo_v_g7.bibliotecaapp.entity.Prestamo}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public sealed class PrestamoDto permits PrestamoDetailedDto {
    Integer idPrestamo;
    ClienteDto cliente;
    LocalDateTime fechaPrestamo;
    LocalDateTime fechaLimite;
    EstadoPrestamo estado;
}
