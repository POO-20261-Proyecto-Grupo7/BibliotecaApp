package pe.uni.poo_v_g7.bibliotecaapp.dto.ai;

import java.util.List;

public record CatalogacionAiResponse(
        String isbn,
        String titulo,
        String sinopsis,
        List<String> categorias,
        List<String> etiquetas
) {
}
