package pe.uni.poo_v_g7.bibliotecaapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.uni.poo_v_g7.bibliotecaapp.dto.*;
import pe.uni.poo_v_g7.bibliotecaapp.service.CategoriaService;
import pe.uni.poo_v_g7.bibliotecaapp.service.EditorialService;
import pe.uni.poo_v_g7.bibliotecaapp.service.LibroService;

import java.time.LocalDateTime;

@CrossOrigin("*")
@RestController
@RequestMapping("/bbapp/api/v1/registrar")
public class RegistrarController {

    @Autowired
    private LibroService libroService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private EditorialService editorialService;

    @PostMapping("/libro")
    public ResponseEntity<?> registrarLibro(@RequestBody RegistrarLibroRequest request, HttpServletRequest httpRequest) {
        try {
            LibroDetailedDto dto = libroService.registerLibro(request);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(
                    e.getMessage(), LocalDateTime.now().toString(), httpRequest.getRequestURI(), e.getStackTrace()
            ));
        }
    }

    @PostMapping("/categoria")
    public ResponseEntity<?> registrarCategoria(@RequestBody RegistrarCategoriaRequest request, HttpServletRequest httpRequest) {
        try {
            return ResponseEntity.ok(categoriaService.registerCategoria(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(
                    e.getMessage(), LocalDateTime.now().toString(), httpRequest.getRequestURI(), e.getStackTrace()
            ));
        }
    }
}
