package pe.uni.poo_v_g7.bibliotecaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDto {

    @NonNull
    private Integer idCategoria;
    @NonNull
    private String nombre;
    @NonNull
    private String descripcion;
}
