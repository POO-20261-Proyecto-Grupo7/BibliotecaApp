package pe.uni.poo_v_g7.bibliotecaapp.mapper;

import org.springframework.stereotype.Component;
import pe.uni.poo_v_g7.bibliotecaapp.dto.CategoriaDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.CategoriaInfoDto;
import pe.uni.poo_v_g7.bibliotecaapp.entity.Categoria;

@Component
public class CategoriaMapper {

    public CategoriaDto toDto(Categoria categoria) {
        return new CategoriaDto(
                categoria.getIdCategoria(),
                categoria.getNombre(),
                categoria.getDescripcion()
        );
    }

    public CategoriaInfoDto toInfoDto(Categoria categoria) {
        return new CategoriaInfoDto(
                categoria.getNombre(),
                categoria.getDescripcion()
        );
    }
}