package pe.uni.poo_v_g7.bibliotecaapp.repository;

import java.util.Objects;

@Deprecated
final class NonNullField<T> extends Field<T> {

    public void set(T value) {
        super.set(
                Objects.requireNonNull(
                        value,
                        "El valor no puede ser nulo."
                )
        );
    }
}