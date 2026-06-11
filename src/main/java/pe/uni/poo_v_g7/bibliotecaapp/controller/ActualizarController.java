package pe.uni.poo_v_g7.bibliotecaapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.uni.poo_v_g7.bibliotecaapp.dto.ActualizarCategoriaRequest;
import pe.uni.poo_v_g7.bibliotecaapp.dto.ErrorResponse;
import pe.uni.poo_v_g7.bibliotecaapp.service.CategoriaService;

import java.time.LocalDateTime;

@CrossOrigin("*")
@RestController
@RequestMapping("/bbapp/api/v1/actualizar")
public class ActualizarController {

    @Autowired
    private CategoriaService categoriaService;

    @PatchMapping("/categoria/{idCategoria}")
    public ResponseEntity<?> actualizarCategoria(
            @PathVariable("idCategoria") int idCategoria,
            @RequestBody ActualizarCategoriaRequest bean,
            HttpServletRequest request
    ) {
        try {
            return ResponseEntity.ok(
                    categoriaService.updateCategoria(
                            idCategoria,
                            bean
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorResponse(
                            e.getMessage(),
                            LocalDateTime.now().toString(),
                            request.getRequestURI(),
                            e.getStackTrace()
                    )
            );
        }
    }
}
