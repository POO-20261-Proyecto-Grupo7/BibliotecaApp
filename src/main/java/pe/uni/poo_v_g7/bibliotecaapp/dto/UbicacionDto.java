package pe.uni.poo_v_g7.bibliotecaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Objeto de transferencia para {@link pe.uni.poo_v_g7.bibliotecaapp.entity.Ubicacion}
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public sealed class UbicacionDto extends UbicacionInfoDto permits UbicacionDetailedDto {

    private Integer idUbicacion;
}
