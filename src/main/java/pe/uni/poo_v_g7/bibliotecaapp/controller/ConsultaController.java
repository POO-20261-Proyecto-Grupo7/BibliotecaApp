package pe.uni.poo_v_g7.bibliotecaapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.uni.poo_v_g7.bibliotecaapp.dto.LibroCategoriaDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.LibroDto;
import pe.uni.poo_v_g7.bibliotecaapp.service.LibroService;

@CrossOrigin("*")
@RestController
@RequestMapping("/bbapp/api/v1/consulta")
public class ConsultaController {

    @Autowired
    private LibroService libroService;

    @GetMapping("/libro/{idLibro}")
    public ResponseEntity<LibroDto> getLibro(@PathVariable(name = "idLibro") int idLibro) {
        try {
            LibroDto dto = libroService.getLibro(idLibro);
            return ResponseEntity.ok(dto);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/librocategoria/{idLibro}")
    public ResponseEntity<LibroCategoriaDto> getLibroCategoria(@PathVariable(name = "idLibro") int idLibro) {
        try {
            LibroCategoriaDto dto = libroService.getLibroCategoria(idLibro);
            return ResponseEntity.ok(dto);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
