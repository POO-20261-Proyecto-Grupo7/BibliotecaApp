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
