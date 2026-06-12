package pe.uni.poo_v_g7.bibliotecaapp.repository;

import org.springframework.stereotype.Repository;
import pe.uni.poo_v_g7.bibliotecaapp.dto.EditorialDto;

@Deprecated
@Repository
public class EditorialQueryRepository extends AbstractQueryRepository {

    public boolean checkEditorialExists(int idEditorial) {
        return exists(
                """
                SELECT COUNT(*)
                FROM Editorial
                WHERE id_editorial = ?
                """,
                idEditorial
        );
    }

    public boolean checkEditorialExistsByNombre(String nombre) {
        return exists(
                """
                SELECT COUNT(*)
                FROM Editorial
                WHERE nombre = ?
                """,
                nombre
        );
    }

    public EditorialDto getEditorial(int idEditorial) {
        return queryOne(
                """
                SELECT *
                FROM Editorial
                WHERE id_editorial = ?
                """,
                EditorialDto.class,
                idEditorial
        );
    }
}