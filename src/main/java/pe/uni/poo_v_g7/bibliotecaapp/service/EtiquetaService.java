package pe.uni.poo_v_g7.bibliotecaapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pe.uni.poo_v_g7.bibliotecaapp.dto.ActualizarEtiquetaDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.EtiquetaDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.RegistrarEtiquetaDto;
import pe.uni.poo_v_g7.bibliotecaapp.repository.EtiquetaCommandRepository;
import pe.uni.poo_v_g7.bibliotecaapp.repository.EtiquetaQueryRepository;

import static pe.uni.poo_v_g7.bibliotecaapp.util.ValidationUtils.*;

@Service
public class EtiquetaService {

    @Autowired
    private EtiquetaQueryRepository etiquetaQueryRepository;

    @Autowired
    private EtiquetaCommandRepository etiquetaCommandRepository;

    public boolean checkEtiquetaExists(int idEtiqueta) {
        return etiquetaQueryRepository.checkEtiquetaExists(idEtiqueta);
    }

    public EtiquetaDto getEtiqueta(int idEtiqueta) {

        if (!checkEtiquetaExists(idEtiqueta)) {
            throw new IllegalArgumentException(
                    "La etiqueta con id " + idEtiqueta + " no existe."
            );
        }

        return etiquetaQueryRepository.getEtiqueta(idEtiqueta);
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public EtiquetaDto registerEtiqueta(RegistrarEtiquetaDto request) {

        String nombre = requireNotBlank(
                requireNonNull(
                        request.getNombre(),
                        "El nombre de la etiqueta no puede ser nulo."
                ),
                "El nombre de la etiqueta no puede estar vacío."
        );

        if (etiquetaQueryRepository.checkEtiquetaExistsByNombre(nombre)) {
            throw new IllegalArgumentException(
                    "El nombre de la etiqueta ya existe en el sistema."
            );
        }

        return etiquetaCommandRepository.insertEtiqueta(nombre);
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public EtiquetaDto updateEtiqueta(
            int idEtiqueta,
            ActualizarEtiquetaDto request
    ) {

        requireTrue(
                checkEtiquetaExists(idEtiqueta),
                "La etiqueta con id " + idEtiqueta + " no existe."
        );

        return etiquetaCommandRepository.updateEtiqueta(
                idEtiqueta,
                spec -> {

                    if (request.getNombre() != null) {

                        String nombre = requireNotBlank(
                                requireNonNull(
                                        request.getNombre().getValue(),
                                        "El nombre de la etiqueta no puede ser nulo."
                                ),
                                "El nombre de la etiqueta no puede estar vacío."
                        );

                        if (etiquetaQueryRepository.checkEtiquetaExistsByNombre(nombre)) {
                            throw new IllegalArgumentException(
                                    "El nombre de la etiqueta ya existe en el sistema."
                            );
                        }

                        spec.setNombre(nombre);
                    }
                }
        );
    }
}