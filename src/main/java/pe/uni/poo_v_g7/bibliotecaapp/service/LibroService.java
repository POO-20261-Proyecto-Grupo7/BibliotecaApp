package pe.uni.poo_v_g7.bibliotecaapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pe.uni.poo_v_g7.bibliotecaapp.dto.LibroCategoriaDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.LibroDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.RegistrarLibroDto;
import pe.uni.poo_v_g7.bibliotecaapp.repository.LibroRepository;

import java.util.function.Predicate;
import java.util.regex.Pattern;

@Service
public class LibroService {

    private static final Pattern ISBN_PATTERN = Pattern.compile("\\d{10}|\\d{13}");

    @Autowired
    private LibroRepository libroRepository;

    public boolean checkLibroExists(int idLibro) {
        return libroRepository.checkLibroExists(idLibro);
    }

    /**
     Obtiene un libro en la base de datos.

     @param idLibro id del libro
     @return un objeto LibroDto que representa el libro obtenido
     */
    public LibroDto getLibro(int idLibro) {
        return libroRepository.getLibro(idLibro);
    }

    /**
     Obtiene un resumen básico de un libro y su categoría en la base de datos.

     @param idLibro id del libro
     */
    public LibroCategoriaDto getLibroCategoria(int idLibro) {
        return libroRepository.getLibroCategoria(idLibro);
    }

    /**
     * RF-01.1.1 Registrar Libro
     * <p>
     * Registra un nuevo libro en el sistema.
     *
     * @param request Un objeto RegistrarLibroDto que contiene los datos del libro a registrar.
     * @param checkCategoriaExists Un Predicate que verifica si una categoría existe dado su id.
     * @return Un objeto LibroDto que representa el libro registrado.
     */
    @Transactional(
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class
    )
    public LibroDto registerLibro(
            RegistrarLibroDto request,
            Predicate<Integer> checkCategoriaExists
    ) {

        String titulo = request.getTitulo();
        String autor = request.getAutor();
        String isbn = request.getIsbn();
        Integer anioPublicacion = request.getAnioPublicacion();
        Integer stockInicial = request.getStockInicial();
        Double precio = request.getPrecio();
        Integer idCategoria = request.getIdCategoria();

        if (titulo == null) {
            throw new IllegalArgumentException("El título del libro no puede ser nulo");
        }

        if (titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título del libro no puede estar vacío");
        }

        if (autor == null) {
            throw new IllegalArgumentException("El autor del libro no puede ser nulo");
        }

        if (autor.trim().isEmpty()) {
            throw new IllegalArgumentException("El autor del libro no puede estar vacío");
        }

        if (isbn == null) {
            throw new IllegalArgumentException("El ISBN del libro no puede ser nulo");
        }

        if (isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("El ISBN del libro no puede estar vacío");
        }

        if (!ISBN_PATTERN.matcher(isbn).matches()) {
            throw new IllegalArgumentException("El ISBN del libro debe tener 10 o 13 dígitos");
        }

        if (libroRepository.checkLibroExistsByIsbn(isbn)) {
            throw new IllegalArgumentException("El ISBN del libro ya existe en el sistema");
        }

        if (anioPublicacion == null) {
            throw new IllegalArgumentException("El año de publicación no puede ser nulo");
        }

        if (stockInicial == null) {
            throw new IllegalArgumentException("El stock inicial no puede ser nulo");
        }

        if (stockInicial < 0) {
            throw new IllegalArgumentException("El stock inicial no puede ser negativo");
        }

        if (precio == null) {
            throw new IllegalArgumentException("El precio no puede ser nulo");
        }

        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }

        if (idCategoria == null) {
            throw new IllegalArgumentException("La categoría no puede ser nula");
        }

        if (!checkCategoriaExists.test(idCategoria)) {
            throw new IllegalArgumentException("La categoría especificada no es válida");
        }

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
}
