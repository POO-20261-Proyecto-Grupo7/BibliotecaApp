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
    public ResponseEntity<?> registrarLibro(@RequestBody RegistrarLibroDto bean, HttpServletRequest request) {
        try {
            LibroDto dto = libroService.registerLibro(bean,
                    categoriaService::checkCategoriaExists,
                    editorialService::checkEditorialExists
            );
            LibroDetailedDto detailedDto = libroService.getLibroDetailed(dto.getIdLibro());
            return ResponseEntity.ok(detailedDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(
                    e.getMessage(), LocalDateTime.now().toString(), request.getRequestURI(), e.getStackTrace()
            ));
        }
    }

    @PostMapping("/categoria")
    public ResponseEntity<?> registrarCategoria(@RequestBody RegistrarCategoriaDto bean, HttpServletRequest request) {
        try {
            return ResponseEntity.ok(categoriaService.registerCategoria(bean));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(
                    e.getMessage(), LocalDateTime.now().toString(), request.getRequestURI(), e.getStackTrace()
            ));
        }
    }
}
