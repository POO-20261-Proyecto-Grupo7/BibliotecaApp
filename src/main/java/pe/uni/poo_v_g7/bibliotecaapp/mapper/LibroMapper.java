package pe.uni.poo_v_g7.bibliotecaapp.mapper;

import org.springframework.stereotype.Component;
import pe.uni.poo_v_g7.bibliotecaapp.dto.*;
import pe.uni.poo_v_g7.bibliotecaapp.entity.*;

import java.util.stream.Collectors;

@Component
public class LibroMapper {

    public LibroDetailedDto toDetailedDto(Libro libro) {
        LibroDetailedDto dto = new LibroDetailedDto();
        dto.setIdLibro(libro.getIdLibro());
        dto.setTitulo(libro.getTitulo());
        dto.setIsbn(libro.getIsbn());
        dto.setAnioPublicacion(libro.getAnioPublicacion());
        dto.setStock(libro.getStock());
        dto.setPrecio(libro.getPrecio());
        dto.setSinopsis(libro.getSinopsis());
        dto.setIdEditorial(libro.getEditorial() != null ? libro.getEditorial().getIdEditorial() : null);

        if (libro.getEditorial() != null) {
            dto.setEditorial(new EditorialDto(
                    libro.getEditorial().getIdEditorial(),
                    libro.getEditorial().getNombre()
            ));
        }

        dto.setAutores(libro.getAutores().stream()
                .map(a -> new AutorDto(a.getIdAutor(), a.getNombre()))
                .collect(Collectors.toList()));

        dto.setCategorias(libro.getCategorias().stream()
                .map(c -> new CategoriaDto(c.getIdCategoria(), c.getNombre(), c.getDescripcion()))
                .collect(Collectors.toList()));

        dto.setEtiquetas(libro.getEtiquetas().stream()
                .map(e -> new EtiquetaDto(e.getIdEtiqueta(), e.getNombre()))
                .collect(Collectors.toList()));

        return dto;
    }

    public LibroDto toDto(Libro libro) {
        return new LibroDto(
                libro.getIdLibro(),
                libro.getTitulo(),
                libro.getIsbn(),
                libro.getAnioPublicacion(),
                libro.getStock(),
                libro.getPrecio(),
                libro.getSinopsis(),
                libro.getEditorial() != null ? libro.getEditorial().getIdEditorial() : null
        );
    }
}