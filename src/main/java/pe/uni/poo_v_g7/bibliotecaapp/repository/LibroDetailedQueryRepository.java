package pe.uni.poo_v_g7.bibliotecaapp.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import pe.uni.poo_v_g7.bibliotecaapp.dto.*;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LibroDetailedQueryRepository extends AbstractQueryRepository {

    public LibroDetailedDto getLibroDetailed(int idLibro) {
        List<LibroDetailedDto> result = findLibroDetailed(
                """
                WHERE l.id_libro = ?
                """,
                idLibro
        );

        if (result.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        }

        return result.get(0);
    }

    public LibroDetailedDto getLibroDetailedByIsbn(String isbn) {
        List<LibroDetailedDto> result = findLibroDetailed(
                """
                WHERE l.isbn = ?
                """,
                isbn
        );

        if (result.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        }

        return result.get(0);
    }

    public List<LibroDetailedDto> getLibrosDetailed() {
        return findLibroDetailed("");
    }

    public List<LibroDetailedDto> getLibrosDetailedByCategoria(int idCategoria) {
        return findLibroDetailed(
                """
                WHERE EXISTS (
                    SELECT 1
                    FROM LibroCategoria lc2
                    WHERE lc2.id_libro = l.id_libro
                      AND lc2.id_categoria = ?
                )
                """,
                idCategoria
        );
    }

    public List<LibroDetailedDto> getLibrosDetailedByEditorial(int idEditorial) {
        return findLibroDetailed(
                """
                WHERE l.id_editorial = ?
                """,
                idEditorial
        );
    }

    public List<LibroDetailedDto> getLibrosDetailedByAutor(int idAutor) {
        return findLibroDetailed(
                """
                WHERE EXISTS (
                    SELECT 1
                    FROM LibroAutor la2
                    WHERE la2.id_libro = l.id_libro
                      AND la2.id_autor = ?
                )
                """,
                idAutor
        );
    }

    public List<LibroDetailedDto> getLibrosDetailedByEtiqueta(int idEtiqueta) {
        return findLibroDetailed(
                """
                WHERE EXISTS (
                    SELECT 1
                    FROM LibroEtiqueta le2
                    WHERE le2.id_libro = l.id_libro
                      AND le2.id_etiqueta = ?
                )
                """,
                idEtiqueta
        );
    }

    private List<LibroDetailedDto> findLibroDetailed(
            String whereClause,
            Object... args
    ) {

        String sql = """
                SELECT
                    l.id_libro,
                    l.titulo,
                    l.isbn,
                    l.anio_publicacion,
                    l.stock,
                    l.precio,
                    l.sinopsis,
                    l.id_editorial,

                    c.id_categoria,
                    c.nombre AS categoria_nombre,
                    c.descripcion AS categoria_descripcion,

                    e.nombre AS editorial_nombre,

                    a.id_autor,
                    a.nombre AS autor_nombre,

                    et.id_etiqueta,
                    et.nombre AS etiqueta_nombre
                FROM Libro l
                LEFT JOIN LibroCategoria lc
                    ON l.id_libro = lc.id_libro
                LEFT JOIN Categoria c
                    ON lc.id_categoria = c.id_categoria
                LEFT JOIN Editorial e
                    ON l.id_editorial = e.id_editorial
                LEFT JOIN LibroAutor la
                    ON l.id_libro = la.id_libro
                LEFT JOIN Autor a
                    ON la.id_autor = a.id_autor
                LEFT JOIN LibroEtiqueta le
                    ON l.id_libro = le.id_libro
                LEFT JOIN Etiqueta et
                    ON le.id_etiqueta = et.id_etiqueta
                %s
                ORDER BY l.id_libro, a.nombre, et.nombre
                """.formatted(whereClause);

        List<LibroDetailedRow> rows = queryMany(sql, this::mapRow, args);

        Map<Integer, LibroDetailedDto> result = new LinkedHashMap<>();

        for (LibroDetailedRow row : rows) {
            LibroDetailedDto dto = result.computeIfAbsent(
                    row.idLibro(),
                    ignored -> buildBaseDto(row)
            );

            if (row.idAutor() != null) {
                AutorDto autorDto = new AutorDto(row.idAutor(), row.autorNombre());
                if (dto.getAutores() == null) {
                    dto.setAutores(new ArrayList<>());
                }
                if (!dto.getAutores().contains(autorDto)) {
                    dto.getAutores().add(autorDto);
                }
            }

            if (row.idEtiqueta() != null) {
                EtiquetaDto etiquetaDto = new EtiquetaDto(
                        row.idEtiqueta(),
                        row.etiquetaNombre()
                );
                if (dto.getEtiquetas() == null) {
                    dto.setEtiquetas(new ArrayList<>());
                }
                if (!dto.getEtiquetas().contains(etiquetaDto)) {
                    dto.getEtiquetas().add(etiquetaDto);
                }
            }

            if (row.idCategoria() != null) {
                CategoriaDto categoriaDto = new CategoriaDto(
                        row.idCategoria(),
                        row.categoriaNombre(),
                        row.categoriaDescripcion()
                );
                if (dto.getCategorias() == null) {
                    dto.setCategorias(new ArrayList<>());
                }
                if (!dto.getCategorias().contains(categoriaDto)) {
                    dto.getCategorias().add(categoriaDto);
                }
            }
        }

        return new ArrayList<>(result.values());
    }

    private LibroDetailedDto buildBaseDto(LibroDetailedRow row) {
        LibroDetailedDto dto = new LibroDetailedDto();

        dto.setIdLibro(row.idLibro());
        dto.setTitulo(row.titulo());
        dto.setIsbn(row.isbn());
        dto.setAnioPublicacion(row.anioPublicacion());
        dto.setStock(row.stock());
        dto.setPrecio(row.precio());
        dto.setSinopsis(row.sinopsis());
        dto.setIdEditorial(row.idEditorial());

        if (row.editorialNombre() != null) {
            dto.setEditorial(
                    new EditorialDto(
                            row.idEditorial(),
                            row.editorialNombre()
                    )
            );
        } else {
            dto.setEditorial(null);
        }

        dto.setAutores(new ArrayList<>());
        dto.setEtiquetas(new ArrayList<>());
        dto.setCategorias(new ArrayList<>());

        return dto;
    }

    private LibroDetailedRow mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new LibroDetailedRow(
                rs.getInt("id_libro"),
                rs.getString("titulo"),
                rs.getString("isbn"),
                rs.getObject("anio_publicacion", Integer.class),
                rs.getInt("stock"),
                rs.getBigDecimal("precio"),
                rs.getString("sinopsis"),
                rs.getObject("id_editorial", Integer.class),

                rs.getObject("id_categoria", Integer.class),
                rs.getString("categoria_nombre"),
                rs.getString("categoria_descripcion"),

                rs.getString("editorial_nombre"),

                rs.getObject("id_autor", Integer.class),
                rs.getString("autor_nombre"),

                rs.getObject("id_etiqueta", Integer.class),
                rs.getString("etiqueta_nombre")
        );
    }

    private record LibroDetailedRow(
            int idLibro,
            String titulo,
            String isbn,
            Integer anioPublicacion,
            int stock,
            BigDecimal precio,
            String sinopsis,
            Integer idEditorial,

            Integer idCategoria,
            String categoriaNombre,
            String categoriaDescripcion,

            String editorialNombre,

            Integer idAutor,
            String autorNombre,

            Integer idEtiqueta,
            String etiquetaNombre
    ) {}
}