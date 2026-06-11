package pe.uni.poo_v_g7.bibliotecaapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.uni.poo_v_g7.bibliotecaapp.dto.ErrorResponse;
import pe.uni.poo_v_g7.bibliotecaapp.dto.RegistrarLibroRequest;
import pe.uni.poo_v_g7.bibliotecaapp.service.CatalogacionAutomaticaService;

import java.time.LocalDateTime;

@CrossOrigin("*")
@RestController
@RequestMapping("/bbapp/api/v1/catalogacion-automatica")
public class CatalogacionAutomaticaController {

    private final CatalogacionAutomaticaService service;

    public CatalogacionAutomaticaController(
            CatalogacionAutomaticaService service
    ) {
        this.service = service;
    }

    @PostMapping("/catalogar-libro")
    public ResponseEntity<?> registrarAndCatalogarAutomaticamente(@Valid @RequestBody RegistrarLibroRequest request,
                                                                  HttpServletRequest httpServletRequest) {
        try {
            return ResponseEntity.ok(service.catalogarNuevoLibro(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorResponse(
                            e.getMessage(),
                            LocalDateTime.now().toString(),
                            httpServletRequest.getRequestURI(),
                            e.getStackTrace()
                    )
            );
        }
    }
}
