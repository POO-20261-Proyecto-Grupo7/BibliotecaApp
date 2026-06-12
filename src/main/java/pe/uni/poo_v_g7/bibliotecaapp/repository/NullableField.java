package pe.uni.poo_v_g7.bibliotecaapp.repository;

@Deprecated
final class NullableField<T> extends Field<T> {

    private boolean nullValue;

    public void setNull() {
        super.set(null);
        this.nullValue = true;
    }

    public boolean isNull() {
        return isPresent() && nullValue;
    }
}
