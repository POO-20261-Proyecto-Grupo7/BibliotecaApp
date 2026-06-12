package pe.uni.poo_v_g7.bibliotecaapp.mapper;

import org.springframework.stereotype.Component;
import pe.uni.poo_v_g7.bibliotecaapp.dto.ClienteDto;
import pe.uni.poo_v_g7.bibliotecaapp.entity.Cliente;

@Component
public class ClienteMapper {

    public ClienteDto toDto(Cliente cliente) {
        return new ClienteDto(
                cliente.getIdCliente(),
                cliente.getNombres(),
                cliente.getApellidos(),
                cliente.getDni(),
                cliente.getTelefono(),
                cliente.getCorreo(),
                cliente.getDireccion(),
                cliente.getFechaRegistro().toString(),
                cliente.getHabilitado()
        );
    }
}
