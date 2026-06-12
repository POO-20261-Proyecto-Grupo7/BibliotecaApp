package pe.uni.poo_v_g7.bibliotecaapp.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import pe.uni.poo_v_g7.bibliotecaapp.entity.Libro;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LibroRepository extends JpaRepository<Libro, Integer> {

    Optional<Libro> findByIsbn(String isbn);

    boolean existsByIsbn(String isbn);

    @EntityGraph(attributePaths = {"autores", "categorias", "etiquetas", "editorial"})
    Optional<Libro> findDetailedByIdLibro(Integer idLibro);

    @EntityGraph(attributePaths = {"autores", "categorias", "etiquetas", "editorial"})
    List<Libro> findDetailedBy();

    List<Libro> findDistinctByAutores_IdAutor(Integer idAutor);

    List<Libro> findDistinctByCategorias_IdCategoria(Integer idCategoria);

    List<Libro> findDistinctByEtiquetas_IdEtiqueta(Integer idEtiqueta);

    List<Libro> findDistinctByEditorial_IdEditorial(Integer idEditorial);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @EntityGraph(attributePaths = {"autores", "categorias", "etiquetas", "editorial"})
    List<Libro> findAllDetailedForUpdateByIdLibroIn(Set<Integer> ids);
}