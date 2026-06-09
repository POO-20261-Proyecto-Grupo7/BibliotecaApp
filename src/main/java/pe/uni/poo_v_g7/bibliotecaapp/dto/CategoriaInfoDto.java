package pe.uni.poo_v_g7.bibliotecaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public sealed class CategoriaInfoDto permits CategoriaDto {

    @NonNull
    private String nombre;
    private String descripcion;
}
