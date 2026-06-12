package pe.uni.poo_v_g7.bibliotecaapp.repository;

import org.springframework.stereotype.Repository;
import pe.uni.poo_v_g7.bibliotecaapp.dto.CategoriaDto;

import java.util.function.Consumer;

@Deprecated
@Repository
public class CategoriaCommandRepository extends AbstractCommandRepository {

    public CategoriaDto insertCategoria(
            String nombre,
            String descripcion
    ) {
        return insertOne(
                """
                INSERT INTO Categoria (
                    nombre,
                    descripcion
                )
                OUTPUT
                    INSERTED.id_categoria,
                    INSERTED.nombre,
                    INSERTED.descripcion
                VALUES (?, ?)
                """,
                CategoriaDto.class,
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

        SqlUpdate update = new SqlUpdate();

        if (state.nombre.isPresent()) {
            update.set("nombre", state.nombre.value());
        }

        if (state.descripcion.isNull()) {
            update.setNull("descripcion");
        } else if (state.descripcion.isPresent()) {
            update.set("descripcion", state.descripcion.value());
        }

        return updateOne(
                "Categoria",
                "id_categoria",
                idCategoria,
                update,
                """
                INSERTED.id_categoria,
                INSERTED.nombre,
                INSERTED.descripcion
                """,
                CategoriaDto.class
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