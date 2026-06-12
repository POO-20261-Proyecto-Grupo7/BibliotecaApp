package pe.uni.poo_v_g7.bibliotecaapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "RenovacionPrestamo")
@Getter
@Setter
@NoArgsConstructor
public class RenovacionPrestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_renovacion")
    private Integer idRenovacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prestamo", nullable = false)
    private Prestamo prestamo;

    @Column(nullable = false)
    private LocalDateTime fechaRenovacion;

    @Column(nullable = false)
    private LocalDateTime fechaLimiteAnterior;

    @Column(nullable = false)
    private LocalDateTime fechaLimiteNueva;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Boolean utilizado;

    private LocalDateTime fechaUtilizacion;
}
