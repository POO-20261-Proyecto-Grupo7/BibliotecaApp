package pe.uni.poo_v_g7.bibliotecaapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pe.uni.poo_v_g7.bibliotecaapp.dto.ActualizarLibroDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.LibroCategoriaDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.LibroDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.RegistrarLibroDto;
import pe.uni.poo_v_g7.bibliotecaapp.repository.LibroRepository;

import java.util.function.Predicate;
import java.util.regex.Pattern;

import static pe.uni.poo_v_g7.bibliotecaapp.util.ValidationUtils.*;

@Service
public class LibroService {

    private static final Pattern ISBN_PATTERN =
            Pattern.compile("\\d{10}|\\d{13}");

    @Autowired
    private LibroRepository libroRepository;

    public boolean checkLibroExists(int idLibro) {
        return libroRepository.checkLibroExists(idLibro);
    }

    public LibroDto getLibro(int idLibro) {
        return libroRepository.getLibro(idLibro);
    }

    public LibroCategoriaDto getLibroCategoria(int idLibro) {
        return libroRepository.getLibroCategoria(idLibro);
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public LibroDto registerLibro(
            RegistrarLibroDto request,
            Predicate<Integer> checkCategoriaExists
    ) {

        String titulo = requireNotBlank(
                requireNonNull(
                        request.getTitulo(),
                        "El título del libro no puede ser nulo."
                ),
                "El título del libro no puede estar vacío."
        );

        String autor = requireNotBlank(
                requireNonNull(
                        request.getAutor(),
                        "El autor del libro no puede ser nulo."
                ),
                "El autor del libro no puede estar vacío."
        );

        String isbn = requireNotBlank(
                requireNonNull(
                        request.getIsbn(),
                        "El ISBN del libro no puede ser nulo."
                ),
                "El ISBN del libro no puede estar vacío."
        );

        requireTrue(
                ISBN_PATTERN.matcher(isbn).matches(),
                "El ISBN del libro debe tener 10 o 13 dígitos."
        );

        requireFalse(
                libroRepository.checkLibroExistsByIsbn(isbn),
                "El ISBN del libro ya existe en el sistema."
        );

        Integer anioPublicacion = requireNonNull(
                request.getAnioPublicacion(),
                "El año de publicación no puede ser nulo."
        );

        Integer stockInicial = requireNonNegative(
                requireNonNull(
                        request.getStockInicial(),
                        "El stock inicial no puede ser nulo."
                ),
                "El stock inicial no puede ser negativo."
        );

        Double precio = requireNonNegative(
                requireNonNull(
                        request.getPrecio(),
                        "El precio no puede ser nulo."
                ),
                "El precio no puede ser negativo."
        );

        Integer idCategoria = requireNonNull(
                request.getIdCategoria(),
                "La categoría no puede ser nula."
        );

        requireTrue(
                checkCategoriaExists.test(idCategoria),
                "La categoría especificada no existe."
        );

        return libroRepository.insertLibro(
                titulo,
                autor,
                isbn,
                anioPublicacion,
                stockInicial,
                precio,
                idCategoria
        );
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public LibroDto updateLibro(
            int idLibro,
            ActualizarLibroDto request,
            Predicate<Integer> checkCategoriaExists
    ) {

        requireTrue(
                checkLibroExists(idLibro),
                "El libro con id " + idLibro + " no existe."
        );

        return libroRepository.updateLibro(
                idLibro,
                spec -> {

                    if (request.getTitulo() != null) {

                        String titulo = requireNotBlank(
                                requireNonNull(
                                        request.getTitulo().getValue(),
                                        "El título del libro no puede ser nulo."
                                ),
                                "El título del libro no puede estar vacío."
                        );

                        spec.setTitulo(titulo);
                    }

                    if (request.getAutor() != null) {

                        String autor = requireNotBlank(
                                requireNonNull(
                                        request.getAutor().getValue(),
                                        "El autor del libro no puede ser nulo."
                                ),
                                "El autor del libro no puede estar vacío."
                        );

                        spec.setAutor(autor);
                    }

                    if (request.getAnioPublicacion() != null) {

                        if (request.getAnioPublicacion().getValue() == null) {
                            spec.setAnioPublicacionNull();
                        } else {
                            spec.setAnioPublicacion(
                                    request.getAnioPublicacion().getValue()
                            );
                        }
                    }

                    if (request.getStock() != null) {

                        Integer stock = requireNonNegative(
                                requireNonNull(
                                        request.getStock().getValue(),
                                        "El stock no puede ser nulo."
                                ),
                                "El stock no puede ser negativo."
                        );

                        spec.setStock(stock);
                    }

                    if (request.getPrecio() != null) {

                        Double precio = requireNonNegative(
                                requireNonNull(
                                        request.getPrecio().getValue(),
                                        "El precio no puede ser nulo."
                                ),
                                "El precio no puede ser negativo."
                        );

                        spec.setPrecio(precio);
                    }

                    if (request.getIdCategoria() != null) {

                        Integer idCategoria = requireNonNull(
                                request.getIdCategoria().getValue(),
                                "La categoría no puede ser nula."
                        );

                        requireTrue(
                                checkCategoriaExists.test(idCategoria),
                                "La categoría especificada no existe."
                        );

                        spec.setIdCategoria(idCategoria);
                    }
                }
        );
    }
}