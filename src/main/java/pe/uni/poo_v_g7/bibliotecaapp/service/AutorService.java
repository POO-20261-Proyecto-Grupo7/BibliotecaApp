package pe.uni.poo_v_g7.bibliotecaapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pe.uni.poo_v_g7.bibliotecaapp.dto.ActualizarAutorDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.AutorDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.RegistrarAutorDto;
import pe.uni.poo_v_g7.bibliotecaapp.repository.AutorCommandRepository;
import pe.uni.poo_v_g7.bibliotecaapp.repository.AutorQueryRepository;

import static pe.uni.poo_v_g7.bibliotecaapp.util.ValidationUtils.*;

@Service
public class AutorService {

    @Autowired
    private AutorQueryRepository autorQueryRepository;

    @Autowired
    private AutorCommandRepository autorCommandRepository;

    public boolean checkAutorExists(int idAutor) {
        return autorQueryRepository.checkAutorExists(idAutor);
    }

    public AutorDto getAutor(int idAutor) {

        if (!checkAutorExists(idAutor)) {
            throw new IllegalArgumentException(
                    "El autor con id " + idAutor + " no existe."
            );
        }

        return autorQueryRepository.getAutor(idAutor);
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public AutorDto registerAutor(RegistrarAutorDto request) {

        String nombre = requireNotBlank(
                requireNonNull(
                        request.getNombre(),
                        "El nombre del autor no puede ser nulo."
                ),
                "El nombre del autor no puede estar vacío."
        );

        if (autorQueryRepository.checkAutorExistsByNombre(nombre)) {
            throw new IllegalArgumentException(
                    "El nombre del autor ya existe en el sistema."
            );
        }

        return autorCommandRepository.insertAutor(nombre);
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public AutorDto updateAutor(
            int idAutor,
            ActualizarAutorDto request
    ) {

        requireTrue(
                checkAutorExists(idAutor),
                "El autor con id " + idAutor + " no existe."
        );

        return autorCommandRepository.updateAutor(
                idAutor,
                spec -> {

                    if (request.getNombre() != null) {

                        String nombre = requireNotBlank(
                                requireNonNull(
                                        request.getNombre().getValue(),
                                        "El nombre del autor no puede ser nulo."
                                ),
                                "El nombre del autor no puede estar vacío."
                        );

                        if (autorQueryRepository.checkAutorExistsByNombre(nombre)) {
                            throw new IllegalArgumentException(
                                    "El nombre del autor ya existe en el sistema."
                            );
                        }

                        spec.setNombre(nombre);
                    }
                }
        );
    }
}