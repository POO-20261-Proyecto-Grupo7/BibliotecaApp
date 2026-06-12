package pe.uni.poo_v_g7.bibliotecaapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pe.uni.poo_v_g7.bibliotecaapp.dto.ActualizarCategoriaRequest;
import pe.uni.poo_v_g7.bibliotecaapp.dto.CategoriaDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.RegistrarCategoriaRequest;
import pe.uni.poo_v_g7.bibliotecaapp.entity.Categoria;
import pe.uni.poo_v_g7.bibliotecaapp.mapper.CategoriaMapper;
import pe.uni.poo_v_g7.bibliotecaapp.repository.CategoriaRepository;

import static pe.uni.poo_v_g7.bibliotecaapp.util.ValidationUtils.*;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    private final CategoriaMapper categoriaMapper;

    public CategoriaService(
            final CategoriaRepository categoriaRepository,
            final CategoriaMapper categoriaMapper
    ) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
    }

    public boolean checkCategoriaExists(int idCategoria) {
        return categoriaRepository.existsById(idCategoria);
    }

    public CategoriaDto getCategoria(int idCategoria) {

        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new IllegalArgumentException("Categoria no existe."));

        return categoriaMapper.toDto(categoria);
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public CategoriaDto registerCategoria(
            RegistrarCategoriaRequest request
    ) {

        Categoria categoria = new Categoria();

        String nombre = requireNotBlank(
                requireNonNull(
                        request.getNombre(),
                        "El nombre de la categoría no puede ser nulo."
                ),
                "El nombre de la categoría no puede estar vacío."
        );

        categoria.setNombre(nombre);

        String descripcion = request.getDescripcion();

        categoria.setDescripcion(descripcion);

        Categoria saved = categoriaRepository.save(categoria);

        return categoriaMapper.toDto(saved);
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public CategoriaDto updateCategoria(
            int idCategoria,
            ActualizarCategoriaRequest request
    ) {

        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new IllegalArgumentException("La categoria no existe."));

        if (request.isNombrePresent()) {
            if (request.getNombre() != null) {
                categoria.setNombre(request.getNombre());
            } else {
                throw new IllegalArgumentException("El nombre no puede ser nulo.");
            }
        }

        if (request.isDescripcionPresent()) categoria.setDescripcion(request.getDescripcion());

        Categoria saved = categoriaRepository.save(categoria);

        return categoriaMapper.toDto(saved);
    }
}