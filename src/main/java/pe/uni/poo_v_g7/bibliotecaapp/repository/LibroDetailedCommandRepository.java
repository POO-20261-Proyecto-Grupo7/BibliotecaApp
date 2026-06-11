package pe.uni.poo_v_g7.bibliotecaapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pe.uni.poo_v_g7.bibliotecaapp.dto.LibroDetailedDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.LibroDto;

import java.util.List;
import java.util.function.Consumer;

@Repository
public class LibroDetailedCommandRepository extends AbstractCommandRepository {

    @Autowired
    private LibroCommandRepository libroCommandRepository;

    @Autowired
    private LibroDetailedQueryRepository libroDetailedQueryRepository;

    public LibroDetailedDto insertLibroDetailed(
            String titulo,
            String isbn,
            int anioPublicacion,
            int stock,
            double precio,
            String sinopsis,
            Integer idEditorial,
            List<Integer> idsAutores,
            List<Integer> idsEtiquetas,
            List<Integer> idsCategorias
    ) {

        LibroDto libro = libroCommandRepository.insertLibro(
                titulo,
                isbn,
                anioPublicacion,
                stock,
                precio,
                sinopsis,
                idEditorial
        );

        int idLibro = libro.getIdLibro();

        if (idsAutores != null) {
            replaceAutores(idLibro, idsAutores);
        }

        if (idsEtiquetas != null) {
            replaceEtiquetas(idLibro, idsEtiquetas);
        }

        return libroDetailedQueryRepository.getLibroDetailed(idLibro);
    }

    public LibroDetailedDto updateLibroDetailed(
            int idLibro,
            Consumer<LibroUpdateSpec> configurator,
            List<Integer> idsAutores,
            List<Integer> idsEtiquetas
    ) {

        LibroDto libro = libroCommandRepository.updateLibro(idLibro, configurator);

        if (idsAutores != null) {
            replaceAutores(idLibro, idsAutores);
        }

        if (idsEtiquetas != null) {
            replaceEtiquetas(idLibro, idsEtiquetas);
        }

        return libroDetailedQueryRepository.getLibroDetailed(libro.getIdLibro());
    }

    public void replaceAutores(
            int idLibro,
            List<Integer> idsAutores
    ) {

        jdbcTemplate.update(
                """
                DELETE FROM LibroAutor
                WHERE id_libro = ?
                """,
                idLibro
        );

        if (idsAutores == null || idsAutores.isEmpty()) {
            return;
        }

        for (Integer idAutor : idsAutores) {
            if (idAutor == null) {
                continue;
            }

            jdbcTemplate.update(
                    """
                    INSERT INTO LibroAutor (
                        id_libro,
                        id_autor
                    )
                    SELECT ?, ?
                    WHERE NOT EXISTS (
                        SELECT 1
                        FROM LibroAutor
                        WHERE id_libro = ?
                          AND id_autor = ?
                    )
                    """,
                    idLibro,
                    idAutor,
                    idLibro,
                    idAutor
            );
        }
    }

    public void replaceEtiquetas(
            int idLibro,
            List<Integer> idsEtiquetas
    ) {

        jdbcTemplate.update(
                """
                DELETE FROM LibroEtiqueta
                WHERE id_libro = ?
                """,
                idLibro
        );

        if (idsEtiquetas == null || idsEtiquetas.isEmpty()) {
            return;
        }

        for (Integer idEtiqueta : idsEtiquetas) {
            if (idEtiqueta == null) {
                continue;
            }

            jdbcTemplate.update(
                    """
                    INSERT INTO LibroEtiqueta (
                        id_libro,
                        id_etiqueta
                    )
                    SELECT ?, ?
                    WHERE NOT EXISTS (
                        SELECT 1
                        FROM LibroEtiqueta
                        WHERE id_libro = ?
                          AND id_etiqueta = ?
                    )
                    """,
                    idLibro,
                    idEtiqueta,
                    idLibro,
                    idEtiqueta
            );
        }
    }

    public void addAutor(
            int idLibro,
            int idAutor
    ) {
        jdbcTemplate.update(
                """
                INSERT INTO LibroAutor (
                    id_libro,
                    id_autor
                )
                SELECT ?, ?
                WHERE NOT EXISTS (
                    SELECT 1
                    FROM LibroAutor
                    WHERE id_libro = ?
                      AND id_autor = ?
                )
                """,
                idLibro,
                idAutor,
                idLibro,
                idAutor
        );
    }

    public void removeAutor(
            int idLibro,
            int idAutor
    ) {
        jdbcTemplate.update(
                """
                DELETE FROM LibroAutor
                WHERE id_libro = ?
                  AND id_autor = ?
                """,
                idLibro,
                idAutor
        );
    }

    public void addEtiqueta(
            int idLibro,
            int idEtiqueta
    ) {
        jdbcTemplate.update(
                """
                INSERT INTO LibroEtiqueta (
                    id_libro,
                    id_etiqueta
                )
                SELECT ?, ?
                WHERE NOT EXISTS (
                    SELECT 1
                    FROM LibroEtiqueta
                    WHERE id_libro = ?
                      AND id_etiqueta = ?
                )
                """,
                idLibro,
                idEtiqueta,
                idLibro,
                idEtiqueta
        );
    }

    public void removeEtiqueta(
            int idLibro,
            int idEtiqueta
    ) {
        jdbcTemplate.update(
                """
                DELETE FROM LibroEtiqueta
                WHERE id_libro = ?
                  AND id_etiqueta = ?
                """,
                idLibro,
                idEtiqueta
        );
    }
}