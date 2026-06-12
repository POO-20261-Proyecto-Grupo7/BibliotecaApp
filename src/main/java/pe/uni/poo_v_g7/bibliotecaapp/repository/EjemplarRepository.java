package pe.uni.poo_v_g7.bibliotecaapp.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.uni.poo_v_g7.bibliotecaapp.entity.Ejemplar;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EjemplarRepository
        extends JpaRepository<Ejemplar, Integer> {

    Optional<Ejemplar> findByCodigo(String codigo);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT e
        FROM Ejemplar e
        WHERE e.id IN :ids
    """)
    List<Ejemplar> findAllForUpdate(@Param("ids") Collection<Integer> ids);

    List<Ejemplar> findAllByCodigoIn(Collection<String> codigos);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    @Query("""
//        SELECT e
//        FROM Ejemplar e
//        WHERE e.codigo IN :codigos
//    """)
    List<Ejemplar> findAllForUpdateByCodigoIn(
            @Param("codigos") Set<String> codigos
    );
}
