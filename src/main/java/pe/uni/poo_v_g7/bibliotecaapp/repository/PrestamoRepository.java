package pe.uni.poo_v_g7.bibliotecaapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.uni.poo_v_g7.bibliotecaapp.entity.Prestamo;

import java.time.LocalDateTime;
import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {

    @Query("""
        SELECT p
        FROM Prestamo p
        WHERE p.estado = 'ACTIVO'
          AND p.fechaLimite BETWEEN :inicio AND :fin
    """)
    List<Prestamo> findPrestamosPorVencer(
            LocalDateTime inicio,
            LocalDateTime fin
    );
}
