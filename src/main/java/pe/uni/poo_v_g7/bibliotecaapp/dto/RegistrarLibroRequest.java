package pe.uni.poo_v_g7.bibliotecaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class RegistrarLibroRequest {

    private String titulo;
    private String isbn;
    private Integer anioPublicacion;
    private Integer stockInicial;
    private BigDecimal precio;
    private Integer idCategoria;
    private String sinopsis;
    private Integer idEditorial;
    private List<Integer> idsAutores;
    private List<Integer> idsCategorias;
    private List<Integer> idsEtiquetas;
}
