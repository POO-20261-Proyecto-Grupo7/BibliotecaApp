package pe.uni.poo_v_g7.bibliotecaapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.uni.poo_v_g7.bibliotecaapp.entity.Etiqueta;

import java.util.Optional;

public interface EtiquetaRepository extends JpaRepository<Etiqueta, Integer> {

    Optional<Etiqueta> findByNombre(String nombre);
}
