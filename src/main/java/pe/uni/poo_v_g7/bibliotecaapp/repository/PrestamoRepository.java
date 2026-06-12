package pe.uni.poo_v_g7.bibliotecaapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.uni.poo_v_g7.bibliotecaapp.entity.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {
}
