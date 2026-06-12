package pe.uni.poo_v_g7.bibliotecaapp.mapper;

import org.springframework.stereotype.Component;
import pe.uni.poo_v_g7.bibliotecaapp.dto.EjemplarDto;
import pe.uni.poo_v_g7.bibliotecaapp.entity.Ejemplar;

@Component
public class EjemplarMapper {

    private final LibroMapper libroMapper;

    public EjemplarMapper(
            EditorialMapper editorialMapper
    ) {
        this.libroMapper = new LibroMapper(editorialMapper);
    }

    public EjemplarDto toDto(Ejemplar ejemplar) {
        return new EjemplarDto(
                ejemplar.getIdEjemplar(),
                libroMapper.toDto(ejemplar.getLibro()),
                ejemplar.getCodigo(),
                ejemplar.getEstado().toString()
        );
    }
}
