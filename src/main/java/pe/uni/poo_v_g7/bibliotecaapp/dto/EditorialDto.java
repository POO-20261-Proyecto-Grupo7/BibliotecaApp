package pe.uni.poo_v_g7.bibliotecaapp.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class EditorialDto extends EditorialInfoDto {

    @NonNull
    private Integer idEditorial;
}