package pe.uni.poo_v_g7.bibliotecaapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pe.uni.poo_v_g7.bibliotecaapp.dto.CategoriaDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.RegistrarCategoriaDto;
import pe.uni.poo_v_g7.bibliotecaapp.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public boolean checkCategoriaExists(int idCategoria) {
        return categoriaRepository.checkCategoriaExists(idCategoria);
    }

    public CategoriaDto getCategoria(int idCategoria) {
        if (!checkCategoriaExists(idCategoria)) {
            throw new IllegalArgumentException("La categoría con id " + idCategoria + " no existe.");
        }
        return categoriaRepository.getCategoria(idCategoria);
    }

    @Transactional(
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class
    )
    public CategoriaDto registrarCategoria(RegistrarCategoriaDto request) {
        String nombre = request.getNombre();
        String descripcion = request.getDescripcion();

        if (nombre == null) {
            throw new IllegalArgumentException("El nombre de la categoría no puede ser nulo.");
        }
        if (nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío.");
        }
        if (descripcion == null) {
            throw new IllegalArgumentException("La descripción de la categoría no puede ser nula.");
        }
        if (descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción de la categoría no puede estar vacía.");
        }
        return categoriaRepository.insertCategoria(nombre, descripcion);
    }
}
