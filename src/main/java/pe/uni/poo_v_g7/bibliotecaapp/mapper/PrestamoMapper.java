package pe.uni.poo_v_g7.bibliotecaapp.mapper;

import org.springframework.stereotype.Component;
import pe.uni.poo_v_g7.bibliotecaapp.dto.PrestamoDetailedDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.PrestamoDto;
import pe.uni.poo_v_g7.bibliotecaapp.entity.Prestamo;

import java.util.Objects;

@Component
public class PrestamoMapper {

    private final ClienteMapper clienteMapper;

    private final EjemplarMapper ejemplarMapper;

    public PrestamoMapper(
            ClienteMapper clienteMapper,
            EjemplarMapper ejemplarMapper
    ) {
        this.clienteMapper = clienteMapper;
        this.ejemplarMapper = ejemplarMapper;
    }

    private PrestamoDto applyDto(PrestamoDto dto, Prestamo entity) {
        dto.setIdPrestamo(entity.getIdPrestamo());
        dto.setCliente(clienteMapper.toDto(entity.getCliente()));
        dto.setFechaPrestamo(entity.getFechaPrestamo());
        dto.setFechaLimite(entity.getFechaLimite());
        dto.setEstado(entity.getEstado());
        return dto;
    }

    private PrestamoDetailedDto applyDetailedDto(PrestamoDetailedDto dto, Prestamo entity) {
        applyDto(dto, entity);
        dto.setEjemplares(entity.getEjemplares() == null ? null : entity.getEjemplares()
                .stream()
                .filter(Objects::nonNull)
                .map(ejemplarMapper::toDto)
                .toList());
        return dto;
    }

    public PrestamoDto toDto(Prestamo prestamo) {
        return applyDto(new PrestamoDto(), prestamo);
    }

    public PrestamoDetailedDto toDetailedDto(Prestamo prestamo) {
        return applyDetailedDto(new PrestamoDetailedDto(), prestamo);
    }
}
