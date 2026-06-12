package pe.uni.poo_v_g7.bibliotecaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public sealed class LibroDto permits LibroDetailedDto {

    private Integer idLibro;
    private String titulo;
    private String isbn;
    private Integer anioPublicacion;
    private Integer stock;
    private BigDecimal precio;
    private String sinopsis;
    private EditorialDto editorial;
}
