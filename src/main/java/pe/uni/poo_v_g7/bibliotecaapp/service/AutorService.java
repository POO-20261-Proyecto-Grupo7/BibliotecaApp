package pe.uni.poo_v_g7.bibliotecaapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pe.uni.poo_v_g7.bibliotecaapp.dto.ActualizarAutorRequest;
import pe.uni.poo_v_g7.bibliotecaapp.dto.AutorDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.RegistrarAutorRequest;
import pe.uni.poo_v_g7.bibliotecaapp.entity.Autor;
import pe.uni.poo_v_g7.bibliotecaapp.mapper.AutorMapper;
import pe.uni.poo_v_g7.bibliotecaapp.repository.AutorRepository;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    private final AutorMapper autorMapper;

    public AutorService(
            AutorRepository autorRepository,
            AutorMapper autorMapper
    ) {
        this.autorRepository = autorRepository;
        this.autorMapper = autorMapper;
    }

    public boolean checkAutorExists(int idAutor) {
        return autorRepository.existsById(idAutor);
    }

    static void validateAutorNombre(String nombre) {
        if (nombre == null) {
            throw new IllegalArgumentException("El nombre de autor no puede ser nulo.");
        }
        if (nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de autor no puede estar vacío.");
        }
    }

    public boolean checkAutorExistsByNombre(String nombre) {
        validateAutorNombre(nombre);
        return autorRepository.existsByNombre(nombre);
    }

    public boolean checkAutorExistsByNombreAndIdAutorNot(String nombre, int idAutor) {
        validateAutorNombre(nombre);
        return autorRepository.existsByNombreAndIdAutorNot(nombre, idAutor);
    }

    public AutorDto getAutor(int idAutor) {

        return autorMapper.toDto(autorRepository.findById(idAutor).orElseThrow(() ->
                new IllegalArgumentException("El autor con el ID '" + idAutor + "' no existe.")));
    }

    public AutorDto registerAutor(RegistrarAutorRequest request) {
        return autorMapper.toDto(registerAutorAndGetEntity(request));
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    Autor registerAutorAndGetEntity(RegistrarAutorRequest request) {

        String nombre = request.getNombre();

        if (checkAutorExistsByNombre(nombre)) {
            throw new IllegalArgumentException(
                    "El nombre del autor ya existe en el sistema."
            );
        }

        var autor = new Autor();
        autor.setNombre(nombre);
        return autorRepository.save(autor);
    }

    public AutorDto updateAutor(
            int idAutor,
            ActualizarAutorRequest request
    ) {
        return autorMapper.toDto(updateAutorAndGetEntity(idAutor, request));
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    Autor updateAutorAndGetEntity(
            int idAutor,
            ActualizarAutorRequest request
    ) {

        var autor = autorRepository.findById(idAutor).orElseThrow(() ->
                new IllegalArgumentException("El autor con el ID '" + idAutor + "' no existe."));

        if (!checkAutorExists(idAutor)) {
            throw new IllegalArgumentException("El autor con id " + idAutor + " no existe.");
        }

        if (request.isNombrePresent()) {
            String nombre = request.getNombre();
            if (checkAutorExistsByNombreAndIdAutorNot(nombre, idAutor)) {
                throw new IllegalArgumentException("El nombre de autor ya existe para otro autor en el sistema.");
            }
            autor.setNombre(nombre);
        }

        return autorRepository.save(autor);
    }
}