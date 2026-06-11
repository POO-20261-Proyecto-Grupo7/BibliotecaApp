package pe.uni.poo_v_g7.bibliotecaapp.dto;

import lombok.*;
import org.jetbrains.annotations.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class EtiquetaDto extends EtiquetaInfoDto {

    @NonNull
    private Integer idEtiqueta;

    public EtiquetaDto(@NotNull Integer idEtiqueta, @NotNull String nombre) {
        this.idEtiqueta = idEtiqueta;
        super(nombre);
    }
}