package pe.uni.poo_v_g7.bibliotecaapp.service;

import org.springframework.stereotype.Service;
import pe.uni.poo_v_g7.bibliotecaapp.dto.ClienteDto;
import pe.uni.poo_v_g7.bibliotecaapp.entity.Cliente;
import pe.uni.poo_v_g7.bibliotecaapp.mapper.ClienteMapper;
import pe.uni.poo_v_g7.bibliotecaapp.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    private final ClienteMapper clienteMapper;

    public ClienteService(
            ClienteRepository clienteRepository,
            ClienteMapper clienteMapper
    ) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    Cliente getClienteEntity(int idCliente) {
        return clienteRepository.findById(idCliente).orElseThrow(
                () -> new IllegalArgumentException("El cliente con ID '" + idCliente + "' no existe.")
        );
    }

    public ClienteDto getCliente(int idCliente) {
        return clienteMapper.toDto(getClienteEntity(idCliente));
    }
}
