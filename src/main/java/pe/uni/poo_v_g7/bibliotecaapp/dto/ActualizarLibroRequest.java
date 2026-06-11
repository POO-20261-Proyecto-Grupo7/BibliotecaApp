package pe.uni.poo_v_g7.bibliotecaapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
public final class ActualizarLibroRequest {

    private String titulo = null;

    @JsonIgnore
    private boolean tituloPresent = false;

    public void setTitulo(String titulo) {
        this.titulo = titulo;
        this.tituloPresent = true;
    }

    public void unsetTitulo() {
        this.titulo = null;
        this.tituloPresent = false;
    }

    private Integer anioPublicacion = null;

    @JsonIgnore
    private boolean anioPublicacionPresent = false;

    public void setAnioPublicacion(Integer anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
        this.anioPublicacionPresent = true;
    }

    public void unsetAnioPublicacion() {
        this.anioPublicacion = null;
        this.anioPublicacionPresent = false;
    }

    private Integer stock = null;

    @JsonIgnore
    private boolean stockPresent = false;

    public void setStock(Integer stock) {
        this.stock = stock;
        this.stockPresent = true;
    }

    public void unsetStock() {
        this.stock = null;
        this.stockPresent = false;
    }

    private BigDecimal precio = null;

    @JsonIgnore
    private boolean precioPresent = false;

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
        this.precioPresent = true;
    }

    public void unsetPrecio() {
        this.precio = null;
        this.precioPresent = false;
    }

    private String sinopsis = null;

    @JsonIgnore
    private boolean sinopsisPresent = false;

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
        this.sinopsisPresent = true;
    }

    public void unsetSinopsis() {
        this.sinopsis = null;
        this.sinopsisPresent = false;
    }

    private Integer idEditorial = null;

    @JsonIgnore
    private boolean idEditorialPresent = false;

    public void setIdEditorial(Integer idEditorial) {
        this.idEditorial = idEditorial;
        this.idEditorialPresent = true;
    }

    public void unsetIdEditorial() {
        this.idEditorial = null;
        this.idEditorialPresent = false;
    }

    private List<Integer> idsAutores = null;

    @JsonIgnore
    private boolean idsAutoresPresent = false;

    public void setIdsAutores(List<Integer> idsAutores) {
        this.idsAutores = idsAutores;
        this.idsAutoresPresent = true;
    }

    public void unsetIdsAutores() {
        this.idsAutores = null;
        this.idsAutoresPresent = false;
    }

    private List<Integer> idsCategorias = null;

    @JsonIgnore
    private boolean idsCategoriasPresent = false;

    public void setIdsCategorias(List<Integer> idsCategorias) {
        this.idsCategorias = idsCategorias;
        this.idsCategoriasPresent = true;
    }

    public void unsetIdsCategorias() {
        this.idsCategorias = null;
        this.idsCategoriasPresent = false;
    }

    private List<Integer> idsEtiquetas = null;

    @JsonIgnore
    private boolean idsEtiquetasPresent = false;

    public void setIdsEtiquetas(List<Integer> idsEtiquetas) {
        this.idsEtiquetas = idsEtiquetas;
        this.idsEtiquetasPresent = true;
    }

    public void unsetIdsEtiquetas() {
        this.idsEtiquetas = null;
        this.idsEtiquetasPresent = false;
    }
}