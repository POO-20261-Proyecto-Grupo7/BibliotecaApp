package pe.uni.poo_v_g7.bibliotecaapp.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.uni.poo_v_g7.bibliotecaapp.dto.UbicacionDetailedDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.UbicacionDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.UbicacionInfoDto;
import pe.uni.poo_v_g7.bibliotecaapp.entity.Ubicacion;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UbicacionMapper {

    private final EjemplarMapper ejemplarMapper;

    public UbicacionMapper(EjemplarMapper ejemplarMapper) {
        this.ejemplarMapper = ejemplarMapper;
    }

    private UbicacionDto applyInfoDto(UbicacionDto dto, Ubicacion entity) {
        dto.setSede(entity.getSede());
        dto.setPasillo(entity.getPasillo());
        dto.setEstante(entity.getEstante());
        dto.setNivel(entity.getNivel());
        return dto;
    }

    private UbicacionDto applyDto(UbicacionDto dto, Ubicacion entity) {
        applyInfoDto(dto, entity);
        dto.setIdUbicacion(entity.getIdUbicacion());
        return dto;
    }

    private UbicacionDetailedDto applyDetailedDto(UbicacionDetailedDto dto, Ubicacion entity) {
        applyDto(dto, entity);
        dto.setEjemplares(entity.getEjemplares().stream().filter(Objects::nonNull).map(ejemplarMapper::toDto).collect(Collectors.toSet()));
        return dto;
    }

    public UbicacionInfoDto toInfoDto(Ubicacion ubicacion) {
        return applyInfoDto(new UbicacionDto(), ubicacion);
    }

    public UbicacionDto toDto(Ubicacion ubicacion) {
        return applyDto(new UbicacionDto(), ubicacion);
    }

    public UbicacionDetailedDto toDetailedDto(Ubicacion ubicacion) {
        return applyDetailedDto(new UbicacionDetailedDto(), ubicacion);
    }
}
