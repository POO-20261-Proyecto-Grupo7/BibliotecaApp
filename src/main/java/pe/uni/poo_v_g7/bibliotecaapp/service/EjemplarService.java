package pe.uni.poo_v_g7.bibliotecaapp.service;

import org.springframework.stereotype.Service;
import pe.uni.poo_v_g7.bibliotecaapp.dto.EjemplarDto;
import pe.uni.poo_v_g7.bibliotecaapp.entity.Ejemplar;
import pe.uni.poo_v_g7.bibliotecaapp.mapper.EjemplarMapper;
import pe.uni.poo_v_g7.bibliotecaapp.repository.EjemplarRepository;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EjemplarService {

    private final EjemplarRepository ejemplarRepository;

    private final EjemplarMapper ejemplarMapper;

    public EjemplarService(
            EjemplarRepository ejemplarRepository,
            EjemplarMapper ejemplarMapper
    ) {
        this.ejemplarRepository = ejemplarRepository;
        this.ejemplarMapper = ejemplarMapper;
    }

    Ejemplar getEjemplarEntity(int idEjemplar) {
        return ejemplarRepository.findById(idEjemplar).orElseThrow(
                () -> new IllegalArgumentException("El ejemplar con ID '" + idEjemplar + "' no existe.")
        );
    }

    public EjemplarDto getEjemplar(int idEjemplar) {
        return ejemplarMapper.toDto(getEjemplarEntity(idEjemplar));
    }

    Ejemplar getEjemplarEntityByCodigo(String codigo) {
        if (codigo == null) {
            throw new IllegalArgumentException("El código de ejemplar no puede ser nulo.");
        }
        if (codigo.isBlank()) {
            throw new IllegalArgumentException("El código de ejemplar no puese estar vacío.");
        }
        return ejemplarRepository.findByCodigo(codigo).orElseThrow(
                () -> new IllegalArgumentException("El ejemplar con código '" + codigo + "' no existe.")
        );
    }

    public EjemplarDto getEjemplarByCodigo(String codigo) {
        return ejemplarMapper.toDto(getEjemplarEntityByCodigo(codigo));
    }

    List<Ejemplar> findAllEjemplarEntityForUpdate(Set<String> codigosEjemplares) {
        if (codigosEjemplares == null) {
            throw new IllegalArgumentException("La collección de códigos de ejemplares no puede ser nula.");
        }

        var set = codigosEjemplares
                .stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(c -> !c.isBlank())
                .collect(Collectors.toSet());

        if (set.isEmpty()) {
            throw new IllegalArgumentException("La collección de códigos de ejemplares no puede estar vacía");
        }
        return ejemplarRepository.findAllForUpdateByCodigoIn(set);
    }
}
