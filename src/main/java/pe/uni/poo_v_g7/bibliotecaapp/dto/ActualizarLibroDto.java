package pe.uni.poo_v_g7.bibliotecaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class ActualizarLibroDto {

    private ValueHolder<String> titulo;
    private ValueHolder<String> autor;
    private ValueHolder<Integer> anioPublicacion;
    private ValueHolder<Integer> stock;
    private ValueHolder<Double> precio;
    private ValueHolder<Integer> idCategoria;
}