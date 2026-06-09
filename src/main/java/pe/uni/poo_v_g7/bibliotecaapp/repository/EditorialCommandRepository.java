package pe.uni.poo_v_g7.bibliotecaapp.repository;

import org.springframework.stereotype.Repository;
import pe.uni.poo_v_g7.bibliotecaapp.dto.EditorialDto;

import java.util.function.Consumer;

@Repository
public class EditorialCommandRepository extends AbstractCommandRepository {

    public EditorialDto insertEditorial(String nombre) {
        return insertOne(
                """
                INSERT INTO Editorial (nombre)
                OUTPUT INSERTED.id_editorial, INSERTED.nombre
                VALUES (?)
                """,
                EditorialDto.class,
                nombre
        );
    }

    public EditorialDto updateEditorial(
            int idEditorial,
            Consumer<EditorialUpdateSpec> configurator
    ) {
        EditorialUpdateState state = new EditorialUpdateState();
        configurator.accept(state);

        SqlUpdate update = new SqlUpdate();

        if (state.nombre.isPresent()) {
            update.set("nombre", state.nombre.value());
        }

        return updateOne(
                "Editorial",
                "id_editorial",
                idEditorial,
                update,
                "INSERTED.id_editorial, INSERTED.nombre",
                EditorialDto.class
        );
    }

    private static final class EditorialUpdateState implements EditorialUpdateSpec {

        private final NonNullField<String> nombre = new NonNullField<>();

        @Override
        public EditorialUpdateState setNombre(String nombre) {
            this.nombre.set(nombre);
            return this;
        }

        @Override
        public EditorialUpdateState unsetNombre() {
            this.nombre.unset();
            return this;
        }
    }
}