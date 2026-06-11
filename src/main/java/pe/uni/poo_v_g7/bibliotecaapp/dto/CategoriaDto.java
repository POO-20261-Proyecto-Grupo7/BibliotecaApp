package pe.uni.poo_v_g7.bibliotecaapp.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class CategoriaDto extends CategoriaInfoDto {

    @NonNull
    private Integer idCategoria;

    public CategoriaDto(@NonNull Integer idCategoria, @NonNull String nombre, String descripcion) {
        super(nombre, descripcion);
        this.idCategoria = idCategoria;
    }
}
