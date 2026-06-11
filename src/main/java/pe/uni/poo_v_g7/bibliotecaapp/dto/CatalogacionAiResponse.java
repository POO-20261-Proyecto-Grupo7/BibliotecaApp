package pe.uni.poo_v_g7.bibliotecaapp.dto;

import java.util.List;

public record CatalogacionAiResponse(
        String isbn,
        String titulo,
        String sinopsis,
        List<CategoriaInfoDto> categorias,
        List<EtiquetaInfoDto> etiquetas
) {
}
