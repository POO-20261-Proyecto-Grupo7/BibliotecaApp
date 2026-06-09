package pe.uni.poo_v_g7.bibliotecaapp.repository;

import org.springframework.stereotype.Repository;
import pe.uni.poo_v_g7.bibliotecaapp.dto.EtiquetaDto;

import java.util.function.Consumer;

@Repository
public class EtiquetaCommandRepository extends AbstractCommandRepository {

    public EtiquetaDto insertEtiqueta(String nombre) {
        return insertOne(
                """
                INSERT INTO Etiqueta (nombre)
                OUTPUT INSERTED.id_etiqueta, INSERTED.nombre
                VALUES (?)
                """,
                EtiquetaDto.class,
                nombre
        );
    }

    public EtiquetaDto updateEtiqueta(
            int idEtiqueta,
            Consumer<EtiquetaUpdateSpec> configurator
    ) {
        EtiquetaUpdateState state = new EtiquetaUpdateState();
        configurator.accept(state);

        SqlUpdate update = new SqlUpdate();

        if (state.nombre.isPresent()) {
            update.set("nombre", state.nombre.value());
        }

        return updateOne(
                "Etiqueta",
                "id_etiqueta",
                idEtiqueta,
                update,
                "INSERTED.id_etiqueta, INSERTED.nombre",
                EtiquetaDto.class
        );
    }

    private static final class EtiquetaUpdateState implements EtiquetaUpdateSpec {

        private final NonNullField<String> nombre = new NonNullField<>();

        @Override
        public EtiquetaUpdateState setNombre(String nombre) {
            this.nombre.set(nombre);
            return this;
        }

        @Override
        public EtiquetaUpdateState unsetNombre() {
            this.nombre.unset();
            return this;
        }
    }
}