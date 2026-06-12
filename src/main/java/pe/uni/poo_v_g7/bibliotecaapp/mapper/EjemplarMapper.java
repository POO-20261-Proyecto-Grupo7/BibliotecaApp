package pe.uni.poo_v_g7.bibliotecaapp.mapper;

import org.springframework.stereotype.Component;
import pe.uni.poo_v_g7.bibliotecaapp.dto.EjemplarDto;
import pe.uni.poo_v_g7.bibliotecaapp.entity.Ejemplar;

@Component
public class EjemplarMapper {

    private final LibroMapper libroMapper;

    private final UbicacionMapper ubicacionMapper;

    public EjemplarMapper(
            EditorialMapper editorialMapper
    ) {
        this.libroMapper = new LibroMapper(editorialMapper);
        this.ubicacionMapper = new UbicacionMapper(this);
    }

    public EjemplarDto toDto(Ejemplar ejemplar) {
        return new EjemplarDto(
                ejemplar.getIdEjemplar(),
                ejemplar.getUbicacion() == null ? null : ubicacionMapper.toDto(ejemplar.getUbicacion()),
                libroMapper.toDto(ejemplar.getLibro()),
                ejemplar.getCodigo(),
                ejemplar.getEstado().toString()
        );
    }
}
