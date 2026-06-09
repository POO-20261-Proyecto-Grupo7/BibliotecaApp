package pe.uni.poo_v_g7.bibliotecaapp.repository;

import org.springframework.stereotype.Repository;
import pe.uni.poo_v_g7.bibliotecaapp.dto.CategoriaDto;

@Repository
public class CategoriaQueryRepository extends AbstractQueryRepository {

    public boolean checkCategoriaExists(int idCategoria) {

        return exists(
                """
                SELECT COUNT(*)
                FROM Categoria
                WHERE id_categoria = ?
                """,
                idCategoria
        );
    }

    public CategoriaDto getCategoria(int idCategoria) {

        return queryOne(
                """
                SELECT *
                FROM Categoria
                WHERE id_categoria = ?
                """,
                CategoriaDto.class,
                idCategoria
        );
    }
}
