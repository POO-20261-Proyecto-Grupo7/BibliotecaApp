package pe.uni.poo_v_g7.bibliotecaapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Entidad para la tabla Ejemplar. Transferible mediante {@link pe.uni.poo_v_g7.bibliotecaapp.dto.EjemplarDto}
 */
@Entity
@Table(name = "Ejemplar")
@Getter
@Setter
@NoArgsConstructor
public class Ejemplar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ejemplar")
    private Integer idEjemplar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_libro", nullable = false)
    private Libro libro;

    @Column(nullable = false, unique = true, length = 50)
    private String codigo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoEjemplar estado;

    @ManyToMany(mappedBy = "ejemplares")
    private Set<Prestamo> prestamos = new HashSet<>();
}
