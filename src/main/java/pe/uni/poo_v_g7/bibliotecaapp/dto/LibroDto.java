package pe.uni.poo_v_g7.bibliotecaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class LibroDto {

    private String idLibro;
    private String titulo;
    private String autor;
    private String isbn;
    private Integer anioPublicacion;
    private Integer stock;
    private Double precio;
    private String idCategoria;
}
