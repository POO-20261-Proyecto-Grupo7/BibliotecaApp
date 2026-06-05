package pe.uni.poo_v_g7.bibliotecaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class RegistrarLibroDto {

    private String titulo;
    private String autor;
    private String isbn;
    private Integer anioPublicacion;
    private Integer stockInicial;
    private Double precio;
    private Integer idCategoria;
}
