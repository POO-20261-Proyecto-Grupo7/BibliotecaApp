package pe.uni.poo_v_g7.bibliotecaapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.uni.poo_v_g7.bibliotecaapp.dto.EjemplarDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.UbicacionDetailedDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.UbicacionDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.UbicacionInfoDto;
import pe.uni.poo_v_g7.bibliotecaapp.entity.Ejemplar;
import pe.uni.poo_v_g7.bibliotecaapp.entity.Ubicacion;
import pe.uni.poo_v_g7.bibliotecaapp.mapper.EditorialMapper;
import pe.uni.poo_v_g7.bibliotecaapp.mapper.EjemplarMapper;
import pe.uni.poo_v_g7.bibliotecaapp.mapper.UbicacionMapper;
import pe.uni.poo_v_g7.bibliotecaapp.repository.EjemplarRepository;
import pe.uni.poo_v_g7.bibliotecaapp.repository.UbicacionRepository;

import java.util.Optional;

@Service
public class UbicacionService {

    private final EjemplarMapper ejemplarMapper;
    private final EjemplarRepository ejemplarRepository;
    private final EjemplarService ejemplarService;
    private final UbicacionMapper ubicacionMapper;
    private final UbicacionRepository ubicacionRepository;

    public UbicacionService(
            EditorialMapper editorialMapper,
            EjemplarRepository ejemplarRepository, UbicacionRepository ubicacionRepository
    ) {
        this.ejemplarMapper = new EjemplarMapper(editorialMapper);
        this.ejemplarRepository = ejemplarRepository;
        this.ejemplarService = new EjemplarService(ejemplarRepository, ejemplarMapper);
        this.ubicacionMapper = new UbicacionMapper(ejemplarMapper);
        this.ubicacionRepository = ubicacionRepository;
    }

    Optional<Ubicacion> findUbicacionEntity(int idUbicacion) {
        return ubicacionRepository.findById(idUbicacion);
    }

    Ubicacion getUbicacionEntity(int idUbicacion) {
        return findUbicacionEntity(idUbicacion).orElseThrow(
                () -> new IllegalArgumentException("La ubicación con ID '" + idUbicacion + "' no existe.")
        );
    }

    public UbicacionDto getUbicacion(int idUbicacion) {
        return ubicacionMapper.toDto(getUbicacionEntity(idUbicacion));
    }

    public UbicacionDetailedDto getUbicacionDetailed(int idUbicacion) {
        return ubicacionMapper.toDetailedDto(getUbicacionEntity(idUbicacion));
    }

    Optional<Ubicacion> findUbicacionEntity(String sede, String pasillo, String estante, String nivel) {
        if (sede == null) {
            throw new IllegalArgumentException("La sede no puede ser nula");
        }
        if (sede.isBlank()) {
            throw new IllegalArgumentException("La sede no puede ser vacía.");
        }
        if (pasillo == null) {
            throw new IllegalArgumentException("El pasillo no puede ser nula");
        }
        if (pasillo.isBlank()) {
            throw new IllegalArgumentException("El pasillo no puede estar vacío.");
        }
        if (estante == null) {
            throw new IllegalArgumentException("El estante no puede ser nula");
        }
        if (estante.isBlank()) {
            throw new IllegalArgumentException("El estante no puede estar vacío.");
        }
        if (nivel == null) {
            throw new IllegalArgumentException("El nivel no puede ser nulo");
        }
        if (nivel.isBlank()) {
            throw new IllegalArgumentException("El nivel no puede estar vacío.");
        }
        return ubicacionRepository.findBySedeAndPasilloAndEstanteAndNivel(sede, pasillo, estante, nivel);
    }

    Ubicacion getUbicacionEntity(String sede, String pasillo, String estante, String nivel) {
        return findUbicacionEntity(sede, pasillo, estante, nivel).orElseThrow(
                () -> new IllegalArgumentException("La ubicación '" + String.join(" -> ", sede, pasillo, estante, nivel) + "' no existe.")
        );
    }

    public UbicacionDto getUbicacion(String sede, String pasillo, String estante, String nivel) {
        return ubicacionMapper.toDto(getUbicacionEntity(sede, pasillo, estante, nivel));
    }

    public UbicacionDetailedDto getUbicacionDetailed(String sede, String pasillo, String estante, String nivel) {
        return ubicacionMapper.toDetailedDto(getUbicacionEntity(sede, pasillo, estante, nivel));
    }

    @Transactional
    Ejemplar asignarUbicacionAndGetEjemplarEntity(
            Integer idEjemplar,
            Integer idUbicacion
    ) {

        Ejemplar ejemplar = ejemplarService.getEjemplarEntity(idEjemplar);

        Ubicacion ubicacion = getUbicacionEntity(idUbicacion);

        ejemplar.setUbicacion(ubicacion);

        return ejemplarRepository.save(ejemplar);
    }

    @Transactional
    Ejemplar asignarUbicacionAndGetEjemplarEntity(
            String codigoEjemplar,
            UbicacionInfoDto dto,
            boolean createUbicacionIfAbsent
    ) {

        Ejemplar ejemplar = ejemplarService.getEjemplarEntityByCodigo(codigoEjemplar);

        var found = findUbicacionEntity(dto.getSede(), dto.getPasillo(), dto.getEstante(), dto.getNivel());

        Ubicacion ubicacion;
        if (found.isPresent()) {
            ubicacion = found.get();
        } else if (createUbicacionIfAbsent) {
            var newUbicacion = new Ubicacion();
            newUbicacion.setSede(dto.getSede());
            newUbicacion.setPasillo(dto.getPasillo());
            newUbicacion.setEstante(dto.getEstante());
            newUbicacion.setNivel(dto.getNivel());
            ubicacion = ubicacionRepository.save(newUbicacion);
        } else {
            throw new IllegalArgumentException("La ubicación '" + String.join(" -> ",
                    dto.getSede(), dto.getPasillo(), dto.getEstante(), dto.getNivel()) + "' no existe.");
        }

        ejemplar.setUbicacion(ubicacion);

        return ejemplarRepository.save(ejemplar);
    }

    public EjemplarDto asignarUbicacionAndGetEjemplar(
            String codigoEjemplar,
            UbicacionInfoDto dto,
            boolean createUbicacionIfAbsent
    ) {
        return ejemplarMapper.toDto(asignarUbicacionAndGetEjemplarEntity(codigoEjemplar, dto, createUbicacionIfAbsent));
    }
}
