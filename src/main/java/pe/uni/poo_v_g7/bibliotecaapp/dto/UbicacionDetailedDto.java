package pe.uni.poo_v_g7.bibliotecaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class UbicacionDetailedDto extends UbicacionDto {

    private Set<EjemplarDto> ejemplares;
}
