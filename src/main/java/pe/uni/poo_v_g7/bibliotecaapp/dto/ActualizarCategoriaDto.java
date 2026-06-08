package pe.uni.poo_v_g7.bibliotecaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class ActualizarCategoriaDto {

    private ValueHolder<String> nombre;
    private ValueHolder<String> descripcion;
}