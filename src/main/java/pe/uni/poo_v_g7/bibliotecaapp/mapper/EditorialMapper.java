package pe.uni.poo_v_g7.bibliotecaapp.mapper;

import org.springframework.stereotype.Component;
import pe.uni.poo_v_g7.bibliotecaapp.dto.EditorialDto;
import pe.uni.poo_v_g7.bibliotecaapp.entity.Editorial;

@Component
public class EditorialMapper {

    public EditorialDto toDto(Editorial editorial) {
        return new EditorialDto(
                editorial.getIdEditorial(),
                editorial.getNombre()
        );
    }
}
