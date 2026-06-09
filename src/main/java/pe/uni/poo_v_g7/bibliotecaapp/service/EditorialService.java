package pe.uni.poo_v_g7.bibliotecaapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pe.uni.poo_v_g7.bibliotecaapp.dto.ActualizarEditorialDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.EditorialDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.RegistrarEditorialDto;
import pe.uni.poo_v_g7.bibliotecaapp.repository.EditorialCommandRepository;
import pe.uni.poo_v_g7.bibliotecaapp.repository.EditorialQueryRepository;

import static pe.uni.poo_v_g7.bibliotecaapp.util.ValidationUtils.*;

@Service
public class EditorialService {

    @Autowired
    private EditorialQueryRepository editorialQueryRepository;

    @Autowired
    private EditorialCommandRepository editorialCommandRepository;

    public boolean checkEditorialExists(int idEditorial) {
        return editorialQueryRepository.checkEditorialExists(idEditorial);
    }

    public EditorialDto getEditorial(int idEditorial) {

        if (!checkEditorialExists(idEditorial)) {
            throw new IllegalArgumentException(
                    "La editorial con id " + idEditorial + " no existe."
            );
        }

        return editorialQueryRepository.getEditorial(idEditorial);
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public EditorialDto registerEditorial(RegistrarEditorialDto request) {

        String nombre = requireNotBlank(
                requireNonNull(
                        request.getNombre(),
                        "El nombre de la editorial no puede ser nulo."
                ),
                "El nombre de la editorial no puede estar vacío."
        );

        if (editorialQueryRepository.checkEditorialExistsByNombre(nombre)) {
            throw new IllegalArgumentException(
                    "El nombre de la editorial ya existe en el sistema."
            );
        }

        return editorialCommandRepository.insertEditorial(nombre);
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public EditorialDto updateEditorial(
            int idEditorial,
            ActualizarEditorialDto request
    ) {

        requireTrue(
                checkEditorialExists(idEditorial),
                "La editorial con id " + idEditorial + " no existe."
        );

        return editorialCommandRepository.updateEditorial(
                idEditorial,
                spec -> {

                    if (request.getNombre() != null) {

                        String nombre = requireNotBlank(
                                requireNonNull(
                                        request.getNombre().getValue(),
                                        "El nombre de la editorial no puede ser nulo."
                                ),
                                "El nombre de la editorial no puede estar vacío."
                        );

                        if (editorialQueryRepository.checkEditorialExistsByNombre(nombre)) {
                            throw new IllegalArgumentException(
                                    "El nombre de la editorial ya existe en el sistema."
                            );
                        }

                        spec.setNombre(nombre);
                    }
                }
        );
    }
}