package pe.uni.poo_v_g7.bibliotecaapp.repository;

public interface LibroUpdateSpec {

    LibroUpdateSpec setTitulo(String titulo);
    LibroUpdateSpec unsetTitulo();

    LibroUpdateSpec setAutor(String autor);
    LibroUpdateSpec unsetAutor();

    LibroUpdateSpec setIsbn(String isbn);
    LibroUpdateSpec unsetIsbn();

    LibroUpdateSpec setAnioPublicacion(Integer anio);
    LibroUpdateSpec setAnioPublicacionNull();
    LibroUpdateSpec unsetAnioPublicacion();

    LibroUpdateSpec setStock(int stock);
    LibroUpdateSpec unsetStock();

    LibroUpdateSpec setPrecio(double precio);
    LibroUpdateSpec unsetPrecio();

    LibroUpdateSpec setIdCategoria(int idCategoria);
    LibroUpdateSpec unsetIdCategoria();
}