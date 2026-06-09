package pe.uni.poo_v_g7.bibliotecaapp.dto;

import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class LibroDetailedDto extends LibroDto {

    @NonNull
    private CategoriaInfoDto categoriaInfo;

    private EditorialInfoDto editorialInfo;

    private List<AutorDto> autores;

    private List<EtiquetaDto> etiquetas;
}
