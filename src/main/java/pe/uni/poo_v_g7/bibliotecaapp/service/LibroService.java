package pe.uni.poo_v_g7.bibliotecaapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pe.uni.poo_v_g7.bibliotecaapp.dto.ActualizarLibroRequest;
import pe.uni.poo_v_g7.bibliotecaapp.dto.LibroDetailedDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.LibroDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.RegistrarLibroRequest;
import pe.uni.poo_v_g7.bibliotecaapp.entity.*;
import pe.uni.poo_v_g7.bibliotecaapp.mapper.LibroMapper;
import pe.uni.poo_v_g7.bibliotecaapp.repository.*;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;

import static pe.uni.poo_v_g7.bibliotecaapp.util.ValidationUtils.*;

@Service
public class LibroService {

    private static final Pattern ISBN_PATTERN = Pattern.compile("\\d{10}|\\d{13}");

    private final LibroRepository libroRepository;

    private final EditorialRepository editorialRepository;

    private final AutorRepository autorRepository;

    private final CategoriaRepository categoriaRepository;

    private final EtiquetaRepository etiquetaRepository;

    private final LibroMapper libroMapper;

    public LibroService(
            LibroRepository libroRepository,
            LibroMapper libroMapper,
            EditorialRepository editorialRepository,
            AutorRepository autorRepository,
            CategoriaRepository categoriaRepository,
            EtiquetaRepository etiquetaRepository
    ) {

        this.libroRepository = libroRepository;
        this.editorialRepository = editorialRepository;
        this.autorRepository = autorRepository;
        this.categoriaRepository = categoriaRepository;
        this.etiquetaRepository = etiquetaRepository;
        this.libroMapper = libroMapper;
    }

    public boolean checkLibroExists(int idLibro) {
        return libroRepository.existsById(idLibro);
    }

    public boolean checkLibroExistsByIsbn(String isbn) {
        requireNonNull(
                isbn,
                "El ISBN del libro no puede ser nulo."
        );
        requireNotBlank(
                isbn,
                "El ISBN del libro no puede estar vacío."
        );
        requireTrue(
                ISBN_PATTERN.matcher(isbn).matches(),
                "El ISBN del libro debe tener 10 o 13 dígitos."
        );
        return libroRepository.existsByIsbn(isbn);
    }

    public LibroDetailedDto getLibro(int idLibro) {
        Libro libro = libroRepository.findDetailedByIdLibro(idLibro)
                .orElseThrow(() -> new IllegalArgumentException("Libro no existe"));
        return libroMapper.toDetailedDto(libro);
    }

    public LibroDetailedDto getLibroDetailed(int idLibro) {
        return this.getLibro(idLibro);
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public LibroDetailedDto registerLibro(RegistrarLibroRequest request) {
        return libroMapper.toDetailedDto(registerLibroEntity(request));
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    Libro registerLibroEntity(RegistrarLibroRequest request) {
        Libro libro = prepareLibroEntity(request);
        return libroRepository.save(libro);
    }

    Libro prepareLibroEntity(RegistrarLibroRequest request) {

        Libro libro = new Libro();

        if (request.getTitulo() == null) {
            throw new IllegalArgumentException("El título del libro no puede ser nulo.");
        }

        if (request.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El título del libro no puede estar vacío.");
        }

        libro.setTitulo(request.getTitulo());

        if (checkLibroExistsByIsbn(request.getIsbn())) {
            throw new IllegalArgumentException(
                    "El libro con el ISBN especificado ya existe."
            );
        }

        libro.setIsbn(request.getIsbn());

        if (request.getAnioPublicacion() == null) {
            throw new IllegalArgumentException(
                    "El año de publicación no puede ser nulo."
            );
        }

        libro.setAnioPublicacion(request.getAnioPublicacion());

        if (request.getStockInicial() == null) {
            throw new IllegalArgumentException(
                    "El stock inicial no puede ser nulo."
            );
        }

        if (request.getStockInicial() < 0) {
            throw new IllegalArgumentException(
                    "El stock inicial no puede ser negativo."
            );
        }

        libro.setStock(request.getStockInicial());

        if (request.getPrecio() == null) {
            throw new IllegalArgumentException(
                    "El precio no puede ser nulo."
            );
        }

        if (request.getPrecio().doubleValue() < 0.0) {
            throw new IllegalArgumentException(
                    "El precio no puede ser negativo."
            );
        }

        libro.setPrecio(request.getPrecio());

        libro.setSinopsis(request.getSinopsis());

        Integer idEditorial = request.getIdEditorial();

        if (idEditorial != null) {
            Editorial editorial = editorialRepository.findById(idEditorial)
                    .orElseThrow(() ->
                            new IllegalArgumentException("Editorial no existe"));

            libro.setEditorial(editorial);
        }

        if (request.getIdsAutores() != null) {
            libro.getAutores().addAll(
                    loadAutores(request.getIdsAutores()).values()
            );
        }

        if (request.getIdsCategorias() != null) {
            libro.getCategorias().addAll(
                    loadCategorias(request.getIdsCategorias()).values()
            );
        }

        if (request.getIdsEtiquetas() != null) {
            libro.getEtiquetas().addAll(
                    loadEtiquetas(request.getIdsEtiquetas()).values()
            );
        }

        return libro;
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public LibroDto updateLibro(
            int idLibro,
            ActualizarLibroRequest request
    ) {

        if (!checkLibroExists(idLibro)) {
            throw new IllegalArgumentException("El libro con id " + idLibro + " no existe.");
        }

        Libro libro = libroRepository.findDetailedByIdLibro(idLibro)
                .orElseThrow(() -> new IllegalArgumentException("Libro no existe"));

        if (request.isTituloPresent()) {
            if (request.getTitulo() == null) {
                throw new IllegalArgumentException("El titulo no puede ser nulo.");
            }
            if (request.getTitulo().trim().isEmpty()) {
                throw new IllegalArgumentException("El titulo no puede estar vacío.");
            }
            libro.setTitulo(request.getTitulo());
        }

        if (request.isAnioPublicacionPresent()) {
            libro.setAnioPublicacion(request.getAnioPublicacion());
        }

        if (request.isStockPresent()) {
            if (request.getStock() == null) {
                throw new IllegalArgumentException("El stock no puede ser nulo.");
            }
            if (request.getStock() < 0) {
                throw new IllegalArgumentException("El stock no puede ser negativo.");
            }
            libro.setStock(request.getStock());
        }

        if (request.isPrecioPresent()) {
            if (request.getPrecio() == null) {
                throw new IllegalArgumentException("El precio no puede ser nulo.");
            }
            if (request.getPrecio().doubleValue() < 0.0) {
                throw new IllegalArgumentException("El precio no puede ser negativo.");
            }
            libro.setPrecio(request.getPrecio());
        }

        if (request.isSinopsisPresent()) {
            libro.setSinopsis(request.getSinopsis());
        }

        if (request.isIdEditorialPresent()) {

            Integer idEditorial = request.getIdEditorial();

            if (idEditorial == null) {
                libro.setEditorial(null);
            } else {
                Editorial editorial = editorialRepository.findById(idEditorial)
                        .orElseThrow(() ->
                                new IllegalArgumentException("Editorial no existe"));

                libro.setEditorial(editorial);
            }
        }

        if (request.isIdsAutoresPresent()) {
            libro.getAutores().clear();

            if (request.getIdsAutores() != null) {
                libro.getAutores().addAll(
                        loadAutores(request.getIdsAutores()).values()
                );
            }
        }

        if (request.isIdsCategoriasPresent()) {
            libro.getCategorias().clear();

            if (request.getIdsCategorias() != null) {
                libro.getCategorias().addAll(
                        loadCategorias(request.getIdsCategorias()).values()
                );
            }
        }

        if (request.isIdsEtiquetasPresent()) {
            libro.getEtiquetas().clear();

            if (request.getIdsEtiquetas() != null) {
                libro.getEtiquetas().addAll(
                        loadEtiquetas(request.getIdsEtiquetas()).values()
                );
            }
        }

        return libroMapper.toDetailedDto(libroRepository.save(libro));
    }

    @Transactional
    public void deleteLibro(Integer idLibro) {
        libroRepository.deleteById(idLibro);
    }

    @Transactional
    public LibroDetailedDto getLibroDetailed(Integer idLibro) {
        Libro libro = libroRepository.findDetailedByIdLibro(idLibro)
                .orElseThrow(() -> new IllegalArgumentException("Libro no existe"));
        return libroMapper.toDetailedDto(libro);
    }

    @Transactional
    public List<LibroDetailedDto> getLibrosDetailed() {
        return libroRepository.findDetailedBy()
                .stream()
                .map(libroMapper::toDetailedDto)
                .toList();
    }

    public List<LibroDetailedDto> getLibrosDetailedByAutor(Integer idAutor) {
        return libroRepository.findDistinctByAutores_IdAutor(idAutor)
                .stream()
                .map(libroMapper::toDetailedDto)
                .toList();
    }

    public List<LibroDetailedDto> getLibrosDetailedByCategoria(Integer idCategoria) {
        return libroRepository.findDistinctByCategorias_IdCategoria(idCategoria)
                .stream()
                .map(libroMapper::toDetailedDto)
                .toList();
    }

    public List<LibroDetailedDto> getLibrosDetailedByEtiqueta(Integer idEtiqueta) {
        return libroRepository.findDistinctByEtiquetas_IdEtiqueta(idEtiqueta)
                .stream()
                .map(libroMapper::toDetailedDto)
                .toList();
    }

    public List<LibroDetailedDto> getLibrosDetailedByEditorial(Integer idEditorial) {
        return libroRepository.findDistinctByEditorial_IdEditorial(idEditorial)
                .stream()
                .map(libroMapper::toDetailedDto)
                .toList();
    }

    public LibroDetailedDto getLibrosDetailedByIsbn(String isbn) {
        Libro libro = libroRepository.findByIsbn(isbn)
                .orElseThrow(() -> new IllegalArgumentException("Libro no existe"));
        return libroMapper.toDetailedDto(libro);
    }

    private <E> Map<Integer, E> loadEntities(List<Integer> ids, Function<Integer, E> loader) {
        Map<Integer, E> entities = new HashMap<>();
        for (Integer id : ids) {
            if (id == null) {
                continue;
            }
            if (entities.containsKey(id)) {
                continue;
            }
            var entity = loader.apply(id);
            entities.put(id, entity);
        }
        return entities;
    }

    private Map<Integer, Autor> loadAutores(List<Integer> ids) {
        return loadEntities(ids, id -> {
            var autor = autorRepository.findById(id);
            if (autor.isEmpty()) {
                throw new IllegalArgumentException("El autor con ID '" + id + "' no existe.");
            }
            return autor.get();
        });
    }

    private Map<Integer, Categoria> loadCategorias(List<Integer> ids) {
        return loadEntities(ids, id -> {
            var categoria = categoriaRepository.findById(id);
            if (categoria.isEmpty()) {
                throw new IllegalArgumentException("La categoría con ID '" + id + "' no existe.");
            }
            return categoria.get();
        });
    }

    private Map<Integer, Etiqueta> loadEtiquetas(List<Integer> ids) {
        return loadEntities(ids, id -> {
            var etiqueta = etiquetaRepository.findById(id);
            if (etiqueta.isEmpty()) {
                throw new IllegalArgumentException("La etiqueta con ID '" + id + "' no existe.");
            }
            return etiqueta.get();
        });
    }
}