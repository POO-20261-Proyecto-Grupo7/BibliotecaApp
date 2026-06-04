package pe.uni.poo_v_g7.bibliotecaapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pe.uni.poo_v_g7.bibliotecaapp.dto.LibroCategoriaDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.LibroDto;

@Repository
public class LibroRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
