package pe.uni.poo_v_g7.bibliotecaapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pe.uni.poo_v_g7.bibliotecaapp.dto.ActualizarCategoriaDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.CategoriaDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.RegistrarCategoriaDto;
import pe.uni.poo_v_g7.bibliotecaapp.repository.CategoriaRepository;

import static pe.uni.poo_v_g7.bibliotecaapp.util.ValidationUtils.*;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public boolean checkCategoriaExists(int idCategoria) {
        return categoriaRepository.checkCategoriaExists(idCategoria);
    }

    public CategoriaDto getCategoria(int idCategoria) {

        if (!checkCategoriaExists(idCategoria)) {
            throw new IllegalArgumentException(
                    "La categoría con id " + idCategoria + " no existe."
            );
        }

        return categoriaRepository.getCategoria(idCategoria);
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

        return categoriaRepository.insertCategoria(
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

        return categoriaRepository.updateCategoria(
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