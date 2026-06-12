package pe.uni.poo_v_g7.bibliotecaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public sealed class UbicacionInfoDto permits UbicacionDto {

    private String sede;
    private String pasillo;
    private String estante;
    private String nivel;
}
