package pe.uni.poo_v_g7.bibliotecaapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pe.uni.poo_v_g7.bibliotecaapp.dto.CategoriaDto;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Deprecated
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

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                idCategoria
        );

        return count != null && count > 0;
    }

    public CategoriaDto getCategoria(int idCategoria) {

        String sql = """
                SELECT *
                FROM Categoria
                WHERE id_categoria = ?
                """;

        return jdbcTemplate.queryForObject(
                sql,
                BeanPropertyRowMapper.newInstance(CategoriaDto.class),
                idCategoria
        );
    }

    public CategoriaDto insertCategoria(
            String nombre,
            String descripcion
    ) {

        String sql = """
                INSERT INTO Categoria (
                    nombre,
                    descripcion
                )
                OUTPUT
                    INSERTED.id_categoria,
                    INSERTED.nombre,
                    INSERTED.descripcion
                VALUES (?, ?)
                """;

        return jdbcTemplate.queryForObject(
                sql,
                BeanPropertyRowMapper.newInstance(CategoriaDto.class),
                nombre,
                descripcion
        );
    }

    public CategoriaDto updateCategoria(
            int idCategoria,
            Consumer<CategoriaUpdateSpec> configurator
    ) {

        CategoriaUpdateState state = new CategoriaUpdateState();

        configurator.accept(state);

        List<String> updates = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();

        if (state.nombre.isPresent()) {
            updates.add("nombre = ?");
            parameters.add(state.nombre.value());
        }

        if (state.descripcion.isNull()) {
            updates.add("descripcion = NULL");
        } else if (state.descripcion.isPresent()) {
            updates.add("descripcion = ?");
            parameters.add(state.descripcion.value());
        }

        if (updates.isEmpty()) {
            throw new IllegalArgumentException(
                    "No se especificó ningún campo para actualizar."
            );
        }

        String sql = """
                UPDATE Categoria
                SET %s
                OUTPUT
                    INSERTED.id_categoria,
                    INSERTED.nombre,
                    INSERTED.descripcion
                WHERE id_categoria = ?
                """.formatted(String.join(", ", updates));

        parameters.add(idCategoria);

        return jdbcTemplate.queryForObject(
                sql,
                BeanPropertyRowMapper.newInstance(CategoriaDto.class),
                parameters.toArray()
        );
    }

    private static final class CategoriaUpdateState implements CategoriaUpdateSpec {

        private final NonNullField<String> nombre = new NonNullField<>();
        private final NullableField<String> descripcion = new NullableField<>();

        @Override
        public CategoriaUpdateState setNombre(String nombre) {
            this.nombre.set(nombre);
            return this;
        }

        @Override
        public CategoriaUpdateState unsetNombre() {
            this.nombre.unset();
            return this;
        }

        @Override
        public CategoriaUpdateState setDescripcion(String descripcion) {
            this.descripcion.set(descripcion);
            return this;
        }

        @Override
        public CategoriaUpdateState setDescripcionNull() {
            this.descripcion.setNull();
            return this;
        }

        @Override
        public CategoriaUpdateState unsetDescripcion() {
            this.descripcion.unset();
            return this;
        }
    }

}