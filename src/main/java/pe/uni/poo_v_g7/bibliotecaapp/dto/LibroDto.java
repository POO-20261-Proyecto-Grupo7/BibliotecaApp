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

    @NonNull
    private Integer idLibro;
    @NonNull
    private String titulo;
    @NonNull
    private String isbn;
    private Integer anioPublicacion;
    @NonNull
    private Integer stock;
    @NonNull
    private BigDecimal precio;
    private String sinopsis;
    private Integer idEditorial;
}
