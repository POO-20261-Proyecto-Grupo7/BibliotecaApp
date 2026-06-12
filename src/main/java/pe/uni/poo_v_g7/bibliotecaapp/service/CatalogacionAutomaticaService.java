package pe.uni.poo_v_g7.bibliotecaapp.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pe.uni.poo_v_g7.bibliotecaapp.dto.LibroDetailedDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.RegistrarLibroRequest;
import pe.uni.poo_v_g7.bibliotecaapp.dto.CatalogacionAiRequest;
import pe.uni.poo_v_g7.bibliotecaapp.entity.Categoria;
import pe.uni.poo_v_g7.bibliotecaapp.entity.Etiqueta;
import pe.uni.poo_v_g7.bibliotecaapp.mapper.LibroMapper;
import pe.uni.poo_v_g7.bibliotecaapp.repository.*;

@Service
public class CatalogacionAutomaticaService {

    private final CatalogacionAiService catalogacionAiService;

    private final LibroRepository libroRepository;

    private final LibroMapper libroMapper;

    private final LibroService libroService;

    private final CategoriaRepository categoriaRepository;

    private final EtiquetaRepository etiquetaRepository;

    public CatalogacionAutomaticaService(
            ChatClient.Builder chatClientBuilder,
            LibroRepository libroRepository,
            LibroMapper libroMapper,
            EditorialRepository editorialRepository,
            AutorRepository autorRepository,
            CategoriaRepository categoriaRepository,
            EtiquetaRepository etiquetaRepository
    ) {
        this.catalogacionAiService = new CatalogacionAiService(chatClientBuilder);
        this.libroRepository = libroRepository;
        this.libroMapper = libroMapper;
        this.libroService = new LibroService(libroRepository, libroMapper, editorialRepository, autorRepository, categoriaRepository, etiquetaRepository);
        this.categoriaRepository = categoriaRepository;
        this.etiquetaRepository = etiquetaRepository;
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public LibroDetailedDto catalogarNuevoLibro(RegistrarLibroRequest request) {
        var libro = libroService.registerLibroEntity(request);
        var ai = catalogacionAiService.catalogar(new CatalogacionAiRequest(libro.getIsbn(), libro.getTitulo(), libro.getSinopsis()));

        if (ai.categorias() != null) {
            for (var categoriaInfoDto : ai.categorias()) {
                var foundCategoria = categoriaRepository.findByNombre(categoriaInfoDto.getNombre());
                Categoria categoria;
                if (foundCategoria.isPresent()) {
                    categoria = foundCategoria.get();
                } else {
                    Categoria newCategoria = new Categoria();
                    newCategoria.setNombre(categoriaInfoDto.getNombre());
                    newCategoria.setDescripcion(categoriaInfoDto.getDescripcion());
                    categoria = categoriaRepository.save(newCategoria);
                }
                libro.getCategorias().add(categoria);
            }
        }

        if (ai.etiquetas() != null) {
            for (var etiquetaInfoDto : ai.etiquetas()) {
                var foundEtiqueta = etiquetaRepository.findByNombre(etiquetaInfoDto.getNombre());
                Etiqueta etiqueta;
                if (foundEtiqueta.isPresent()) {
                    etiqueta = foundEtiqueta.get();
                } else {
                    Etiqueta newEtiqueta = new Etiqueta();
                    newEtiqueta.setNombre(etiquetaInfoDto.getNombre());
                    etiqueta = etiquetaRepository.save(newEtiqueta);
                }
                libro.getEtiquetas().add(etiqueta);
            }
        }

        if (ai.sinopsis() != null) {
            libro.setSinopsis(ai.sinopsis());
        }

        libroRepository.save(libro);

        return libroMapper.toDetailedDto(libro);
    }
}
