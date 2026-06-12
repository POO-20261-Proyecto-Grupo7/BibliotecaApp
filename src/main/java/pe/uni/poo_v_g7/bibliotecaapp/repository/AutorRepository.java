package pe.uni.poo_v_g7.bibliotecaapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.uni.poo_v_g7.bibliotecaapp.entity.Autor;

import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Integer> {

    Optional<Autor> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

    boolean existsByNombreAndIdAutorNot(String nombre, int idAutor);
}
