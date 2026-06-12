package pe.uni.poo_v_g7.bibliotecaapp.repository;

import org.springframework.stereotype.Repository;
import pe.uni.poo_v_g7.bibliotecaapp.dto.LibroDto;

@Deprecated
@Repository
public class LibroQueryRepository extends AbstractQueryRepository {

    public boolean checkLibroExists(int idLibro) {

        return exists(
                """
                SELECT COUNT(*)
                FROM Libro
                WHERE id_libro = ?
                """,
                idLibro
        );
    }

    public boolean checkLibroExistsByIsbn(String isbn) {

        return exists(
                """
                SELECT COUNT(*)
                FROM Libro
                WHERE isbn = ?
                """,
                isbn
        );
    }

    public LibroDto getLibro(int idLibro) {

        return queryOne(
                """
                SELECT *
                FROM Libro
                WHERE id_libro = ?
                """,
                LibroDto.class,
                idLibro
        );
    }
}
