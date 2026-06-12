package pe.uni.poo_v_g7.bibliotecaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pe.uni.poo_v_g7.bibliotecaapp.entity.EstadoPrestamo;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class PrestamoDetailedDto extends PrestamoDto {

    private List<EjemplarDto> ejemplares;

    public PrestamoDetailedDto(
            Integer idPrestamo,
            ClienteDto cliente,
            LocalDateTime fechaPrestamo,
            LocalDateTime fechaLimite,
            EstadoPrestamo estado,
            List<EjemplarDto> ejemplares
    ) {
        super(idPrestamo, cliente, fechaPrestamo, fechaLimite, estado);
        this.ejemplares = ejemplares;
    }
}
