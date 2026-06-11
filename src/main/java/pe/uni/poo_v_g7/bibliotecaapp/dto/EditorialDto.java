package pe.uni.poo_v_g7.bibliotecaapp.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class EditorialDto extends EditorialInfoDto {

    @NonNull
    private Integer idEditorial;

    public EditorialDto(@NonNull Integer idEditorial, @NonNull String nombre) {
        super(nombre);
        this.idEditorial = idEditorial;
    }
}