package pe.uni.poo_v_g7.bibliotecaapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.uni.poo_v_g7.bibliotecaapp.entity.Ubicacion;

import java.util.Optional;

public interface UbicacionRepository extends JpaRepository<Ubicacion, Integer> {

    Optional<Ubicacion> findBySedeAndPasilloAndEstanteAndNivel(
            String sede,
            String pasillo,
            String estante,
            String nivel
    );
}
