package pe.uni.poo_v_g7.bibliotecaapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.uni.poo_v_g7.bibliotecaapp.entity.Autor;

public interface AutorJpaRepository extends JpaRepository<Autor, Integer> {}
