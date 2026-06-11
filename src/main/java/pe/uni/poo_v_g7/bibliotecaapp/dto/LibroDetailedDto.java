package pe.uni.poo_v_g7.bibliotecaapp.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public final class LibroDetailedDto extends LibroDto {
    private EditorialDto editorial;
    private List<AutorDto> autores;
    private List<CategoriaDto> categorias;
    private List<EtiquetaDto> etiquetas;
}