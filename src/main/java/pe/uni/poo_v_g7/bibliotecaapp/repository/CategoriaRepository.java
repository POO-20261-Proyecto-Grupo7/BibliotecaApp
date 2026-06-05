package pe.uni.poo_v_g7.bibliotecaapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pe.uni.poo_v_g7.bibliotecaapp.dto.CategoriaDto;

@Repository
public class CategoriaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean checkCategoriaExists(int idCategoria) {

        String sql = """
                SELECT COUNT(*)
                FROM Categoria
                WHERE id_categoria = ?
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, idCategoria);
        return count != null && count > 0;
    }

    public CategoriaDto getCategoria(int idCategoria) {

        String sql = """
                SELECT *
                FROM Categoria
                WHERE id_categoria = ?
                """;

        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(CategoriaDto.class), idCategoria);
    }
}
