package pe.uni.poo_v_g7.bibliotecaapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pe.uni.poo_v_g7.bibliotecaapp.dto.LibroCategoriaDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.LibroDto;
import pe.uni.poo_v_g7.bibliotecaapp.repository.LibroRepository;

@Service
public class ConsultasService {

    @Autowired
    private LibroRepository libroRepository;

    /**
     Obtiene un resumen básico de un libro en la base de datos.

     @param idLibro id del libro
     */
    public LibroDto getLibro(int idLibro) {
        return libroRepository.getLibro(idLibro);
    }

    /**
     Obtiene un resumen básico de un libro y su categoría en la base de datos.

     @param idLibro id del libro
     */
    public LibroCategoriaDto getLibroCategoria(int idLibro) {
        return libroRepository.getLibroCategoria(idLibro);
    }
}
