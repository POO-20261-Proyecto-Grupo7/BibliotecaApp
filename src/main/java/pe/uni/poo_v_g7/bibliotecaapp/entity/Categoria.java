package pe.uni.poo_v_g7.bibliotecaapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Categoria")
@Getter
@Setter
@NoArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Integer idCategoria;

    private String nombre;

    private String descripcion;
}