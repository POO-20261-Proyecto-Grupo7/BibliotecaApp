package pe.uni.poo_v_g7.bibliotecaapp.repository;

import org.springframework.stereotype.Repository;
import pe.uni.poo_v_g7.bibliotecaapp.dto.LibroDto;

import java.util.function.Consumer;

@Deprecated
@Repository
public class LibroCommandRepository extends AbstractCommandRepository {

    public LibroDto insertLibro(
            String titulo,
            String isbn,
            int anioPublicacion,
            int stock,
            double precio,
            String sinopsis,
            Integer idEditorial
    ) {
        return insertOne(
                """
                INSERT INTO Libro (
                    titulo,
                    isbn,
                    anio_publicacion,
                    stock,
                    precio,
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
                    INSERTED.sinopsis,
                    INSERTED.id_editorial
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """,
                LibroDto.class,
                titulo,
                isbn,
                anioPublicacion,
                stock,
                precio,
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

        SqlUpdate update = new SqlUpdate();

        if (state.titulo.isPresent()) {
            update.set("titulo", state.titulo.value());
        }

        if (state.isbn.isPresent()) {
            update.set("isbn", state.isbn.value());
        }

        if (state.anioPublicacion.isNull()) {
            update.setNull("anio_publicacion");
        } else if (state.anioPublicacion.isPresent()) {
            update.set("anio_publicacion", state.anioPublicacion.value());
        }

        if (state.stock.isPresent()) {
            update.set("stock", state.stock.value());
        }

        if (state.precio.isPresent()) {
            update.set("precio", state.precio.value());
        }

//        if (state.idCategoria.isPresent()) {
//            update.set("id_categoria", state.idCategoria.value());
//        }

        if (state.sinopsis.isNull()) {
            update.setNull("sinopsis");
        } else if (state.sinopsis.isPresent()) {
            update.set("sinopsis", state.sinopsis.value());
        }

        if (state.idEditorial.isNull()) {
            update.setNull("id_editorial");
        } else if (state.idEditorial.isPresent()) {
            update.set("id_editorial", state.idEditorial.value());
        }

        return updateOne(
                "Libro",
                "id_libro",
                idLibro,
                update,
                """
                INSERTED.id_libro,
                INSERTED.titulo,
                INSERTED.isbn,
                INSERTED.anio_publicacion,
                INSERTED.stock,
                INSERTED.precio,
                INSERTED.sinopsis,
                INSERTED.id_editorial
                """,
                LibroDto.class
        );
    }

    private static final class LibroUpdateState implements LibroUpdateSpec {

        private final NonNullField<String> titulo = new NonNullField<>();
        private final NonNullField<String> isbn = new NonNullField<>();
        private final NullableField<Integer> anioPublicacion = new NullableField<>();
        private final NonNullField<Integer> stock = new NonNullField<>();
        private final NonNullField<Double> precio = new NonNullField<>();
//        private final NonNullField<Integer> idCategoria = new NonNullField<>();
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
        public LibroUpdateState setAnioPublicacion(Integer anioPublicacion) {
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
//            this.idCategoria.set(idCategoria);
            return this;
        }

        @Override
        public LibroUpdateState unsetIdCategoria() {
//            this.idCategoria.unset();
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