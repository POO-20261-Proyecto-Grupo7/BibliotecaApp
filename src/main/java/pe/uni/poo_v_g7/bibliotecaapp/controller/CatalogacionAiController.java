package pe.uni.poo_v_g7.bibliotecaapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.uni.poo_v_g7.bibliotecaapp.dto.ErrorResponse;
import pe.uni.poo_v_g7.bibliotecaapp.dto.ai.CatalogacionAiRequest;
import pe.uni.poo_v_g7.bibliotecaapp.dto.ai.CatalogacionAiResponse;
import pe.uni.poo_v_g7.bibliotecaapp.service.ai.CatalogacionAiService;

import java.time.LocalDateTime;

@CrossOrigin("*")
@RestController
@RequestMapping("/bbapp/api/v1/ai/catalogacion")
public class CatalogacionAiController {

    private final CatalogacionAiService catalogacionAiService;

    public CatalogacionAiController(CatalogacionAiService catalogacionAiService) {
        this.catalogacionAiService = catalogacionAiService;
    }

    @PostMapping("/catalogar-libro")
    public ResponseEntity<?> catalogar(@Valid @RequestBody CatalogacionAiRequest request,
                                            HttpServletRequest httpServletRequest) {
        try {
            return ResponseEntity.ok(catalogacionAiService.catalogar(request));
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
