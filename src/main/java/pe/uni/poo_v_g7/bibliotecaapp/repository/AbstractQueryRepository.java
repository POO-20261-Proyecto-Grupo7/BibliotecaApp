package pe.uni.poo_v_g7.bibliotecaapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public abstract class AbstractQueryRepository {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    protected <T> T queryOne(
            String sql,
            Class<T> type,
            Object... args
    ) {
        return jdbcTemplate.queryForObject(
                sql,
                BeanPropertyRowMapper.newInstance(type),
                args
        );
    }

    protected <T> List<T> queryMany(
            String sql,
            RowMapper<T> rowMapper,
            Object... args
    ) {
        return jdbcTemplate.query(sql, rowMapper, args);
    }

    protected int count(
            String sql,
            Object... args
    ) {
        Integer result = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                args
        );

        return result == null ? 0 : result;
    }

    protected boolean exists(
            String sql,
            Object... args
    ) {
        return count(sql, args) > 0;
    }
}