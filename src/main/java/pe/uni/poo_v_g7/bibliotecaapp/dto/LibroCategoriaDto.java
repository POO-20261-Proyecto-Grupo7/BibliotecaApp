package pe.uni.poo_v_g7.bibliotecaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class LibroCategoriaDto {

    @NonNull
    private String idLibro;
    @NonNull
    private String titulo;
    @NonNull
    private String autor;
    @NonNull
    private String categoria;
}
