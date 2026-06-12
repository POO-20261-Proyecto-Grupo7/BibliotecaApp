package pe.uni.poo_v_g7.bibliotecaapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pe.uni.poo_v_g7.bibliotecaapp.dto.LibroDto;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Deprecated
@Repository
public class LibroRepositoryDep {

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

    public LibroDto getLibro(int idLibro) {
        String sql = """
                SELECT *
                FROM Libro
                WHERE id_libro = ?;
                """;

        return jdbcTemplate.queryForObject(
                sql,
                BeanPropertyRowMapper.newInstance(LibroDto.class),
                idLibro
        );
    }

//    public LibroCategoriaDto getLibroCategoria(int idLibro) {
//        String sql = """
//                SELECT
//                    l.id_libro,
//                    l.titulo,
//                    l.autor,
//                    c.nombre AS categoria
//                FROM Libro l
//                INNER JOIN Categoria c
//                    ON l.id_categoria = c.id_categoria
//                WHERE l.id_libro = ?;
//                """;
//
//        return jdbcTemplate.queryForObject(
//                sql,
//                BeanPropertyRowMapper.newInstance(LibroCategoriaDto.class),
//                idLibro
//        );
//    }

    public LibroDto insertLibro(
            String titulo,
            String isbn,
            int anioPublicacion,
            int stock,
            double precio,
            int idCategoria,
            String sinopsis,
            Integer idEditorial
    ) {
        String sql = """
                INSERT INTO Libro (
                    titulo,
                    isbn,
                    anio_publicacion,
                    stock,
                    precio,
                    id_categoria,
                    sinopsis,
                    id_editorial
                )
                OUTPUT
                    INSERTED.id_libro,
                    INSERTED.titulo,
                    INSERTED.isbn,
                    INSERTED.anio_publicacion,
                    INSERTED.stock,
                    INSERTED.precio,
                    INSERTED.id_categoria,
                    INSERTED.sinopsis,
                    INSERTED.id_editorial
                VALUES (?, ?, ?, ?, ?, ?, ?, ?);
                """;

        return jdbcTemplate.queryForObject(
                sql,
                BeanPropertyRowMapper.newInstance(LibroDto.class),
                titulo,
                isbn,
                anioPublicacion,
                stock,
                precio,
                idCategoria,
                sinopsis,
                idEditorial
        );
    }

    public LibroDto updateLibro(
            int idLibro,
            Consumer<LibroUpdateSpec> configurator
    ) {

        LibroUpdateState state = new LibroUpdateState();

        configurator.accept(state);

        List<String> updates = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();

        if (state.titulo.isPresent()) {
            updates.add("titulo = ?");
            parameters.add(state.titulo.value());
        }

        if (state.isbn.isPresent()) {
            updates.add("isbn = ?");
            parameters.add(state.isbn.value());
        }

        if (state.anioPublicacion.isNull()) {
            updates.add("anio_publicacion = NULL");
        } else if (state.anioPublicacion.isPresent()) {
            updates.add("anio_publicacion = ?");
            parameters.add(state.anioPublicacion.value());
        }

        if (state.stock.isPresent()) {
            updates.add("stock = ?");
            parameters.add(state.stock.value());
        }

        if (state.precio.isPresent()) {
            updates.add("precio = ?");
            parameters.add(state.precio.value());
        }

        if (state.idCategoria.isPresent()) {
            updates.add("id_categoria = ?");
            parameters.add(state.idCategoria.value());
        }

        if (state.sinopsis.isNull()) {
            updates.add("sinopsis = NULL");
        } else if (state.sinopsis.isPresent()) {
            updates.add("sinopsis = ?");
            parameters.add(state.sinopsis.value());
        }

        if (state.idEditorial.isNull()) {
            updates.add("id_editorial = NULL");
        } else if (state.idEditorial.isPresent()) {
            updates.add("id_editorial = ?");
            parameters.add(state.idEditorial.value());
        }

        if (updates.isEmpty()) {
            throw new IllegalArgumentException(
                    "No se especificó ningún campo para actualizar."
            );
        }

        String sql = """
            UPDATE Libro
            SET %s
            OUTPUT
                INSERTED.id_libro,
                INSERTED.titulo,
                INSERTED.isbn,
                INSERTED.anio_publicacion,
                INSERTED.stock,
                INSERTED.precio,
                INSERTED.id_categoria,
                INSERTED.sinopsis,
                INSERTED.id_editorial
            WHERE id_libro = ?
            """.formatted(String.join(", ", updates));

        parameters.add(idLibro);

        return jdbcTemplate.queryForObject(
                sql,
                BeanPropertyRowMapper.newInstance(LibroDto.class),
                parameters.toArray()
        );
    }

    private static final class LibroUpdateState implements LibroUpdateSpec {

        private final NonNullField<String> titulo = new NonNullField<>();

        private final NonNullField<String> isbn = new NonNullField<>();

        private final NullableField<Integer> anioPublicacion = new NullableField<>();

        private final NonNullField<Integer> stock = new NonNullField<>();

        private final NonNullField<Double> precio = new NonNullField<>();

        private final NonNullField<Integer> idCategoria = new NonNullField<>();

        private final NullableField<String> sinopsis = new NullableField<>();

        private final NullableField<Integer> idEditorial = new NullableField<>();

        @Override
        public LibroUpdateState setTitulo(String titulo) {
            this.titulo.set(titulo);
            return this;
        }

        @Override
        public LibroUpdateState unsetTitulo() {
            this.titulo.unset();
            return this;
        }

        @Override
        public LibroUpdateState setIsbn(String isbn) {
            this.isbn.set(isbn);
            return this;
        }

        @Override
        public LibroUpdateState unsetIsbn() {
            this.isbn.unset();
            return this;
        }

        @Override
        public LibroUpdateState setAnioPublicacion(
                Integer anioPublicacion
        ) {
            this.anioPublicacion.set(anioPublicacion);
            return this;
        }

        @Override
        public LibroUpdateState setAnioPublicacionNull() {
            this.anioPublicacion.setNull();
            return this;
        }

        @Override
        public LibroUpdateState unsetAnioPublicacion() {
            this.anioPublicacion.unset();
            return this;
        }

        @Override
        public LibroUpdateState setStock(int stock) {
            this.stock.set(stock);
            return this;
        }

        @Override
        public LibroUpdateState unsetStock() {
            this.stock.unset();
            return this;
        }

        @Override
        public LibroUpdateState setPrecio(double precio) {
            this.precio.set(precio);
            return this;
        }

        @Override
        public LibroUpdateState unsetPrecio() {
            this.precio.unset();
            return this;
        }

        @Override
        public LibroUpdateState setIdCategoria(int idCategoria) {
            this.idCategoria.set(idCategoria);
            return this;
        }

        @Override
        public LibroUpdateState unsetIdCategoria() {
            this.idCategoria.unset();
            return this;
        }

        @Override
        public LibroUpdateSpec setSinopsis(String sinopsis) {
            this.sinopsis.set(sinopsis);
            return this;
        }

        @Override
        public LibroUpdateSpec setSinopsisNull() {
            this.sinopsis.setNull();
            return this;
        }

        @Override
        public LibroUpdateSpec unsetSinopsis() {
            this.sinopsis.unset();
            return this;
        }

        @Override
        public LibroUpdateSpec setIdEditorial(Integer idEditorial) {
            this.idEditorial.set(idEditorial);
            return this;
        }

        @Override
        public LibroUpdateSpec setIdEditorialNull() {
            this.idEditorial.setNull();
            return this;
        }

        @Override
        public LibroUpdateSpec unsetIdEditorial() {
            this.idEditorial.unset();
            return this;
        }
    }
}