package pe.uni.poo_v_g7.bibliotecaapp.dto;

public record CatalogacionAiRequest(
        String isbn,
        String titulo,
        String descripcion
) {
}
