package pe.uni.poo_v_g7.bibliotecaapp.repository;

import org.springframework.stereotype.Repository;
import pe.uni.poo_v_g7.bibliotecaapp.dto.AutorDto;

import java.util.function.Consumer;

@Repository
public class AutorCommandRepository extends AbstractCommandRepository {

    public AutorDto insertAutor(String nombre) {
        return insertOne(
                """
                INSERT INTO Autor (nombre)
                OUTPUT INSERTED.id_autor, INSERTED.nombre
                VALUES (?)
                """,
                AutorDto.class,
                nombre
        );
    }

    public AutorDto updateAutor(
            int idAutor,
            Consumer<AutorUpdateSpec> configurator
    ) {
        AutorUpdateState state = new AutorUpdateState();
        configurator.accept(state);

        SqlUpdate update = new SqlUpdate();

        if (state.nombre.isPresent()) {
            update.set("nombre", state.nombre.value());
        }

        return updateOne(
                "Autor",
                "id_autor",
                idAutor,
                update,
                "INSERTED.id_autor, INSERTED.nombre",
                AutorDto.class
        );
    }

    private static final class AutorUpdateState implements AutorUpdateSpec {

        private final NonNullField<String> nombre = new NonNullField<>();

        @Override
        public AutorUpdateState setNombre(String nombre) {
            this.nombre.set(nombre);
            return this;
        }

        @Override
        public AutorUpdateState unsetNombre() {
            this.nombre.unset();
            return this;
        }
    }
}