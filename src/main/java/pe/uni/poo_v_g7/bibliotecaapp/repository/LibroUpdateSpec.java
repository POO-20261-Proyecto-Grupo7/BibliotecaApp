package pe.uni.poo_v_g7.bibliotecaapp.repository;

public interface LibroUpdateSpec {

    LibroUpdateSpec setTitulo(String titulo);
    LibroUpdateSpec unsetTitulo();

    LibroUpdateSpec setIsbn(String isbn);
    LibroUpdateSpec unsetIsbn();

    LibroUpdateSpec setAnioPublicacion(Integer anio);
    LibroUpdateSpec setAnioPublicacionNull();
    LibroUpdateSpec unsetAnioPublicacion();

    LibroUpdateSpec setStock(int stock);
    LibroUpdateSpec unsetStock();

    LibroUpdateSpec setPrecio(double precio);
    LibroUpdateSpec unsetPrecio();

    @Deprecated
    LibroUpdateSpec setIdCategoria(int idCategoria);
    @Deprecated
    LibroUpdateSpec unsetIdCategoria();

    LibroUpdateSpec setSinopsis(String sinopsis);
    LibroUpdateSpec setSinopsisNull();
    LibroUpdateSpec unsetSinopsis();

    LibroUpdateSpec setIdEditorial(Integer idEditorial);
    LibroUpdateSpec setIdEditorialNull();
    LibroUpdateSpec unsetIdEditorial();
}