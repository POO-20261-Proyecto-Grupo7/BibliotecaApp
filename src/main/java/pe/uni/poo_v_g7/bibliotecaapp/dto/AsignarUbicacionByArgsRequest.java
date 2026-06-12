package pe.uni.poo_v_g7.bibliotecaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class AsignarUbicacionByArgsRequest {

    private String codigoEjemplar;

    private String sede;
    private String pasillo;
    private String estante;
    private String nivel;
}
