package pe.uni.poo_v_g7.bibliotecaapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidad para la tabla Prestamo. Transferible mediante {@link pe.uni.poo_v_g7.bibliotecaapp.dto.PrestamoDto}
 */
@Entity
@Table(name = "Prestamo")
@Getter
@Setter
@NoArgsConstructor
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prestamo")
    private Integer idPrestamo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_socio", nullable = false)
    private Socio socio;

    @Column(name = "fecha_prestamo", nullable = false)
    private LocalDateTime fechaPrestamo;

    @Column(name = "fecha_limite", nullable = false)
    private LocalDateTime fechaLimite;

    @Column(name = "fecha_devolucion")
    private LocalDateTime fechaDevolucion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPrestamo estado;

    @ManyToMany
    @JoinTable(
            name = "Prestamo_Ejemplar",
            joinColumns = @JoinColumn(name = "id_prestamo"),
            inverseJoinColumns = @JoinColumn(name = "id_ejemplar")
    )
    private Set<Ejemplar> ejemplares = new HashSet<>();
}
