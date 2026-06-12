package pe.uni.poo_v_g7.bibliotecaapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.uni.poo_v_g7.bibliotecaapp.dto.ErrorResponse;
import pe.uni.poo_v_g7.bibliotecaapp.dto.RegistrarPrestamoPorEjemplarCodigoRequest;
import pe.uni.poo_v_g7.bibliotecaapp.service.PrestamoService;

import java.time.LocalDateTime;

@CrossOrigin("*")
@RestController
@RequestMapping("/bbapp/api/v1/prestamo")
public class PrestamoController {

    private final PrestamoService service;

    public PrestamoController(
            PrestamoService service
    ) {
        this.service = service;
    }

    @PostMapping("/prestar-ejemplar")
    public ResponseEntity<?> prestarEjemplar(
            @Valid @RequestBody RegistrarPrestamoPorEjemplarCodigoRequest request,
            HttpServletRequest httpRequest
    ) {
        try {
            var dto = service.registerPrestamo(request);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(
                    e.getMessage(), LocalDateTime.now().toString(), httpRequest.getRequestURI(), e.getStackTrace()
            ));
        }
    }
}
