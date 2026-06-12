package pe.uni.poo_v_g7.bibliotecaapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.uni.poo_v_g7.bibliotecaapp.dto.SocioDto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidad de la tabla Socio. Transferible mediante {@link SocioDto}.
 */
@Entity
@Table(name = "Socio")
@Getter
@Setter
@NoArgsConstructor
public class Socio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_socio")
    private Integer idSocio;

    @Column(nullable = false, length = 100)
    private String nombres;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(nullable = false, unique = true, length = 15)
    private String dni;

    @Column(length = 20)
    private String telefono;

    @Column(unique = true, length = 150)
    private String correo;

    @Column(length = 255)
    private String direccion;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @Column(nullable = false)
    private Boolean habilitado;

    @OneToMany(mappedBy = "socio")
    private Set<Prestamo> prestamos = new HashSet<>();

    @OneToMany(mappedBy = "socio")
    private Set<Venta> ventas = new HashSet<>();
}
