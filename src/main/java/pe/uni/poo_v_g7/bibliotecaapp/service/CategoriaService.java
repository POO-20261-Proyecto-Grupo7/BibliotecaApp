package pe.uni.poo_v_g7.bibliotecaapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pe.uni.poo_v_g7.bibliotecaapp.dto.ActualizarCategoriaDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.CategoriaDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.RegistrarCategoriaDto;
import pe.uni.poo_v_g7.bibliotecaapp.repository.CategoriaCommandRepository;
import pe.uni.poo_v_g7.bibliotecaapp.repository.CategoriaQueryRepository;

import static pe.uni.poo_v_g7.bibliotecaapp.util.ValidationUtils.*;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaQueryRepository categoriaQueryRepository;

    @Autowired
    private CategoriaCommandRepository categoriaCommandRepository;

    public boolean checkCategoriaExists(int idCategoria) {
        return categoriaQueryRepository.checkCategoriaExists(idCategoria);
    }

    public CategoriaDto getCategoria(int idCategoria) {

        if (!checkCategoriaExists(idCategoria)) {
            throw new IllegalArgumentException(
                    "La categoría con id " + idCategoria + " no existe."
            );
        }

        return categoriaQueryRepository.getCategoria(idCategoria);
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public CategoriaDto registerCategoria(
            RegistrarCategoriaDto request
    ) {

        String nombre = requireNotBlank(
                requireNonNull(
                        request.getNombre(),
                        "El nombre de la categoría no puede ser nulo."
                ),
                "El nombre de la categoría no puede estar vacío."
        );

        String descripcion = requireNotBlank(
                requireNonNull(
                        request.getDescripcion(),
                        "La descripción de la categoría no puede ser nula."
                ),
                "La descripción de la categoría no puede estar vacía."
        );

        return categoriaCommandRepository.insertCategoria(
                nombre,
                descripcion
        );
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public CategoriaDto updateCategoria(
            int idCategoria,
            ActualizarCategoriaDto request
    ) {

        requireTrue(
                checkCategoriaExists(idCategoria),
                "La categoría con id " + idCategoria + " no existe."
        );

        return categoriaCommandRepository.updateCategoria(
                idCategoria,
                spec -> {

                    if (request.getNombre() != null) {

                        String nombre = requireNotBlank(
                                requireNonNull(
                                        request.getNombre().getValue(),
                                        "El nombre de la categoría no puede ser nulo."
                                ),
                                "El nombre de la categoría no puede estar vacío."
                        );

                        spec.setNombre(nombre);
                    }

                    if (request.getDescripcion() != null) {

                        if (request.getDescripcion().getValue() == null) {

                            spec.setDescripcionNull();

                        } else {

                            String descripcion = requireNotBlank(
                                    request.getDescripcion().getValue(),
                                    "La descripción de la categoría no puede estar vacía."
                            );

                            spec.setDescripcion(descripcion);
                        }
                    }
                }
        );
    }
}