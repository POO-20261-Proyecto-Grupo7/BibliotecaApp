package pe.uni.poo_v_g7.bibliotecaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class RegistrarPrestamoPorEjemplarCodigoRequest {

    private Integer idCliente;

    private Set<String> ejemplares;
}
