package pe.uni.poo_v_g7.bibliotecaapp.mapper;

import org.springframework.stereotype.Component;
import pe.uni.poo_v_g7.bibliotecaapp.dto.AutorDto;
import pe.uni.poo_v_g7.bibliotecaapp.entity.Autor;

@Component
public class AutorMapper {

    public AutorDto toDto(Autor autor) {
        return new AutorDto(
                autor.getIdAutor(),
                autor.getNombre()
        );
    }
}
