package pe.uni.poo_v_g7.bibliotecaapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pe.uni.poo_v_g7.bibliotecaapp.dto.LibroCategoriaDto;

@Service
public class ConsultasService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public LibroCategoriaDto libroCategoria(int idLibro) {

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
