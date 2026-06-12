package pe.uni.poo_v_g7.bibliotecaapp.service;

import org.springframework.stereotype.Service;
import pe.uni.poo_v_g7.bibliotecaapp.dto.SocioDto;
import pe.uni.poo_v_g7.bibliotecaapp.entity.Socio;
import pe.uni.poo_v_g7.bibliotecaapp.mapper.SocioMapper;
import pe.uni.poo_v_g7.bibliotecaapp.repository.SocioRepository;

@Service
public class SocioService {

    private final SocioRepository socioRepository;

    private final SocioMapper socioMapper;

    public SocioService(
            SocioRepository socioRepository,
            SocioMapper socioMapper
    ) {
        this.socioRepository = socioRepository;
        this.socioMapper = socioMapper;
    }

    Socio getSocioEntity(int idSocio) {
        return socioRepository.findById(idSocio).orElseThrow(
                () -> new IllegalArgumentException("El socio con ID '" + idSocio + "' no existe.")
        );
    }

    public SocioDto getSocio(int idSocio) {
        return socioMapper.toDto(getSocioEntity(idSocio));
    }
}
