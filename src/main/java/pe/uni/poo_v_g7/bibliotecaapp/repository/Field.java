package pe.uni.poo_v_g7.bibliotecaapp.repository;

import lombok.Getter;

class Field<T> {

    @Getter
    private boolean present;

    private T value;

    public void set(T value) {
        this.present = true;
        this.value = value;
    }

    public T value() {
        return value;
    }

    public void unset() {
        this.present = false;
        this.value = null;
    }
}