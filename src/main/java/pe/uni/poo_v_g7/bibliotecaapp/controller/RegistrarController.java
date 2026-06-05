package pe.uni.poo_v_g7.bibliotecaapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.uni.poo_v_g7.bibliotecaapp.dto.ErrorResponse;
import pe.uni.poo_v_g7.bibliotecaapp.dto.LibroDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.RegistrarLibroDto;
import pe.uni.poo_v_g7.bibliotecaapp.service.RegistrarService;

import java.time.LocalDateTime;

@CrossOrigin("*")
@RestController
@RequestMapping("/bbapp/api/v1/registrar")
public class RegistrarController {

    @Autowired
    private RegistrarService registrarService;

    @PostMapping("/libro")
    public ResponseEntity<?> registrarLibro(@RequestBody RegistrarLibroDto bean, HttpServletRequest request) {
        try {
            LibroDto dto = registrarService.registrarLibro(bean);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(
                    e.getMessage(), LocalDateTime.now().toString(), request.getRequestURI()
            ));
        }
    }
}
