package pe.uni.poo_v_g7.bibliotecaapp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.uni.poo_v_g7.bibliotecaapp.dto.LibroCategoriaDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.LibroDto;
import pe.uni.poo_v_g7.bibliotecaapp.service.ConsultasService;

@CrossOrigin("*")
@RestController
@RequestMapping("/bbapp")
public class ConsultasRest {

    @Autowired
    private ConsultasService consultasService;

    @GetMapping("/libro/{idLibro}")
    public ResponseEntity<LibroDto> getLibro(@PathVariable(name = "idLibro") int idLibro) {
        try {
            LibroDto dto = consultasService.getLibro(idLibro);
            return ResponseEntity.ok(dto);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/libro/categoria/{idLibro}")
    public ResponseEntity<LibroCategoriaDto> getLibroCategoria(@PathVariable(name = "idLibro") int idLibro) {
        try {
            LibroCategoriaDto dto = consultasService.getLibroCategoria(idLibro);
            return ResponseEntity.ok(dto);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
