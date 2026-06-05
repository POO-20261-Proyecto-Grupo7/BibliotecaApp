package pe.uni.poo_v_g7.bibliotecaapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Libro")
@Getter
@Setter
@NoArgsConstructor
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_libro")
    private Integer idLibro;

    private String titulo;

    private String autor;

    private String isbn;

    @Column(name = "anio_publicacion")
    private Integer anioPublicacion;

    private Integer stock;

    private Double precio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;
}
