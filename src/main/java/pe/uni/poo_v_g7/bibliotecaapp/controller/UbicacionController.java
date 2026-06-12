package pe.uni.poo_v_g7.bibliotecaapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.uni.poo_v_g7.bibliotecaapp.dto.AsignarUbicacionByArgsRequest;
import pe.uni.poo_v_g7.bibliotecaapp.dto.ErrorResponse;
import pe.uni.poo_v_g7.bibliotecaapp.dto.UbicacionInfoDto;
import pe.uni.poo_v_g7.bibliotecaapp.service.UbicacionService;

import java.time.LocalDateTime;

@CrossOrigin("*")
@RestController
@RequestMapping("/bbapp/api/v1/ubicacion")
public class UbicacionController {

    private final UbicacionService service;

    public UbicacionController(
            UbicacionService service) {
        this.service = service;
    }

    @PostMapping("/asignar-ubicacion-ejemplar")
    public ResponseEntity<?> asignarUbicacionEjemplar(
            @Valid @RequestBody AsignarUbicacionByArgsRequest request,
            HttpServletRequest httpRequest
    ) {
        try {
            var ubicacion = new UbicacionInfoDto();
            ubicacion.setSede(request.getSede());
            ubicacion.setPasillo(request.getPasillo());
            ubicacion.setEstante(request.getEstante());
            ubicacion.setNivel(request.getNivel());
            var dto = service.asignarUbicacionAndGetEjemplar(request.getCodigoEjemplar(), ubicacion, true);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(
                    e.getMessage(), LocalDateTime.now().toString(), httpRequest.getRequestURI(), e.getStackTrace()
            ));
        }
    }
}
