package pe.uni.poo_v_g7.bibliotecaapp.repository;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public abstract class AbstractCommandRepository extends AbstractQueryRepository {

    protected final <T> T insertOne(
            String sql,
            Class<T> type,
            Object... args
    ) {
        return queryOne(sql, type, args);
    }

    protected final <T> T updateOne(
            String tableName,
            String idColumn,
            int idValue,
            SqlUpdate update,
            String outputColumns,
            Class<T> type
    ) {
        if (update.isEmpty()) {
            throw new IllegalArgumentException(
                    "No se especificó ningún campo para actualizar."
            );
        }

        String sql = """
                UPDATE %s
                SET %s
                OUTPUT %s
                WHERE %s = ?
                """.formatted(
                tableName,
                String.join(", ", update.clauses()),
                outputColumns,
                idColumn
        );

        List<Object> parameters = new ArrayList<>(update.parameters());
        parameters.add(idValue);

        return queryOne(sql, type, parameters.toArray());
    }

    protected static final class SqlUpdate {

        private final List<String> clauses = new ArrayList<>();
        private final List<Object> parameters = new ArrayList<>();

        public SqlUpdate set(String column, Object value) {
            clauses.add(column + " = ?");
            parameters.add(value);
            return this;
        }

        public SqlUpdate setNull(String column) {
            clauses.add(column + " = NULL");
            return this;
        }

        public boolean isEmpty() {
            return clauses.isEmpty();
        }

        public List<String> clauses() {
            return clauses;
        }

        public List<Object> parameters() {
            return parameters;
        }
    }
}