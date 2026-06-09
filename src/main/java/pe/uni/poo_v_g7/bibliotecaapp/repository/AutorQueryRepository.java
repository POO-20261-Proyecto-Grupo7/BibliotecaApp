package pe.uni.poo_v_g7.bibliotecaapp.repository;

import org.springframework.stereotype.Repository;
import pe.uni.poo_v_g7.bibliotecaapp.dto.AutorDto;

@Repository
public class AutorQueryRepository extends AbstractQueryRepository {

    public boolean checkAutorExists(int idAutor) {
        return exists(
                """
                SELECT COUNT(*)
                FROM Autor
                WHERE id_autor = ?
                """,
                idAutor
        );
    }

    public boolean checkAutorExistsByNombre(String nombre) {
        return exists(
                """
                SELECT COUNT(*)
                FROM Autor
                WHERE nombre = ?
                """,
                nombre
        );
    }

    public AutorDto getAutor(int idAutor) {
        return queryOne(
                """
                SELECT *
                FROM Autor
                WHERE id_autor = ?
                """,
                AutorDto.class,
                idAutor
        );
    }
}