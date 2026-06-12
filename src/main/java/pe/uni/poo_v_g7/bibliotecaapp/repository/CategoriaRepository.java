package pe.uni.poo_v_g7.bibliotecaapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.uni.poo_v_g7.bibliotecaapp.entity.Categoria;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    Optional<Categoria> findByNombre(String nombre);
}
