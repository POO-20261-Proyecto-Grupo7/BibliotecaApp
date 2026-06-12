package pe.uni.poo_v_g7.bibliotecaapp.mapper;

import org.springframework.stereotype.Component;
import pe.uni.poo_v_g7.bibliotecaapp.dto.SocioDto;
import pe.uni.poo_v_g7.bibliotecaapp.entity.Socio;

@Component
public class SocioMapper {

    public SocioDto toDto(Socio socio) {
        return new SocioDto(
                socio.getIdSocio(),
                socio.getNombres(),
                socio.getApellidos(),
                socio.getDni(),
                socio.getTelefono(),
                socio.getCorreo(),
                socio.getDireccion(),
                socio.getFechaRegistro().toString(),
                socio.getHabilitado()
        );
    }
}
