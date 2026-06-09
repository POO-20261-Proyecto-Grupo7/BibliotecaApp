package pe.uni.poo_v_g7.bibliotecaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class EtiquetaDto {

    @NonNull
    private Integer idEtiqueta;
    @NonNull
    private String nombre;
}