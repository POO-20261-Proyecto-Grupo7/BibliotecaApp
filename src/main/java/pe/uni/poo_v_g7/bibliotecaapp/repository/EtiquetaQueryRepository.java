package pe.uni.poo_v_g7.bibliotecaapp.repository;

import org.springframework.stereotype.Repository;
import pe.uni.poo_v_g7.bibliotecaapp.dto.EtiquetaDto;

@Repository
public class EtiquetaQueryRepository extends AbstractQueryRepository {

    public boolean checkEtiquetaExists(int idEtiqueta) {
        return exists(
                """
                SELECT COUNT(*)
                FROM Etiqueta
                WHERE id_etiqueta = ?
                """,
                idEtiqueta
        );
    }

    public boolean checkEtiquetaExistsByNombre(String nombre) {
        return exists(
                """
                SELECT COUNT(*)
                FROM Etiqueta
                WHERE nombre = ?
                """,
                nombre
        );
    }

    public EtiquetaDto getEtiqueta(int idEtiqueta) {
        return queryOne(
                """
                SELECT *
                FROM Etiqueta
                WHERE id_etiqueta = ?
                """,
                EtiquetaDto.class,
                idEtiqueta
        );
    }
}