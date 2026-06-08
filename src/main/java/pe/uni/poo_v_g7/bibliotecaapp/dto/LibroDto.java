package pe.uni.poo_v_g7.bibliotecaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class LibroDto {

    @NonNull
    private Integer idLibro;
    @NonNull
    private String titulo;
    @NonNull
    private String autor;
    @NonNull
    private String isbn;
    private Integer anioPublicacion;
    @NonNull
    private Integer stock;
    @NonNull
    private Double precio;
    @NonNull
    private Integer idCategoria;
}
