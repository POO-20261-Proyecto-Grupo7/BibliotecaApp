package pe.uni.poo_v_g7.bibliotecaapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import pe.uni.poo_v_g7.bibliotecaapp.dto.LibroCategoriaDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.LibroDto;

import java.sql.PreparedStatement;

@Repository
public class LibroRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean checkLibroExists(int idLibro) {
        String sql = """
                SELECT COUNT(*)
                FROM Libro
                WHERE id_libro = ?;
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, idLibro);
        return count != null && count > 0;
    }

    public boolean checkLibroExistsByIsbn(String isbn) {
        String sql = """
                SELECT COUNT(*)
                FROM Libro
                WHERE isbn = ?;
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, isbn);
        return count != null && count > 0;
    }

    /**
     Obtiene un libro por su id en la base de datos.

     @param idLibro id del libro
     */
    public LibroDto getLibro(int idLibro) {

        String sql = """
                SELECT *
                FROM Libro
                WHERE id_libro = ?;
                """;

        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(LibroDto.class), idLibro);
    }

    /**
     Inserta un nuevo libro en la base de datos.

     @param titulo          título del libro
     @param autor           autor del libro
     @param isbn            ISBN del libro
     @param anioPublicacion año de publicación del libro
     @param stock           cantidad de stock disponible
     @param precio          precio del libro
     @param idCategoria     id de la categoría a la que pertenece el libro
     @return id del libro insertado
     */
    public LibroDto insertLibro(
            String titulo,
            String autor,
            String isbn,
            int anioPublicacion,
            int stock,
            double precio,
            int idCategoria
    ) {
        String sql = """
        INSERT INTO Libro (
            titulo,
            autor,
            isbn,
            anio_publicacion,
            stock,
            precio,
            id_categoria
        )
        OUTPUT
            INSERTED.id_libro,
            INSERTED.titulo,
            INSERTED.autor,
            INSERTED.isbn,
            INSERTED.anio_publicacion,
            INSERTED.stock,
            INSERTED.precio,
            INSERTED.id_categoria
        VALUES (?, ?, ?, ?, ?, ?, ?);
        """;

        return jdbcTemplate.queryForObject(
                sql,
                BeanPropertyRowMapper.newInstance(LibroDto.class),
                titulo,
                autor,
                isbn,
                anioPublicacion,
                stock,
                precio,
                idCategoria
        );
    }
//    public int insertLibro(
//            String titulo,
//            String autor,
//            String isbn,
//            int anioPublicacion,
//            int stock,
//            double precio,
//            int idCategoria
//    ) {
//        String sql = """
//            INSERT INTO Libro (
//                titulo,
//                autor,
//                isbn,
//                anio_publicacion,
//                stock,
//                precio,
//                id_categoria
//            )
//            VALUES (?, ?, ?, ?, ?, ?, ?);
//            """;
//
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//
//        jdbcTemplate.update(connection -> {
//            PreparedStatement ps = connection.prepareStatement(
//                    sql,
//                    new String[]{"id_libro"}
//            );
//
//            ps.setString(1, titulo);
//            ps.setString(2, autor);
//            ps.setString(3, isbn);
//            ps.setInt(4, anioPublicacion);
//            ps.setInt(5, stock);
//            ps.setDouble(6, precio);
//            ps.setInt(7, idCategoria);
//
//            return ps;
//        }, keyHolder);
//
//        return keyHolder.getKey().intValue();
//    }

    /**
     Obtiene un resumen básico de un libro y su categoría en la base de datos.

     @param idLibro id del libro
     */
    public LibroCategoriaDto getLibroCategoria(int idLibro) {

        String sql = """
                SELECT
                    l.id_libro,
                    l.titulo,
                    l.autor,
                    c.nombre AS categoria
                FROM Libro l
                INNER JOIN Categoria c
                    ON l.id_categoria = c.id_categoria
                WHERE l.id_libro = ?;
                """;

        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(LibroCategoriaDto.class), idLibro);
    }
}
