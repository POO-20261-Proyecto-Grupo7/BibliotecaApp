package pe.uni.poo_v_g7.bibliotecaapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Empleado")
@Getter
@Setter
@NoArgsConstructor
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Integer idEmpleado;

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

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salario;

    @OneToOne(
            mappedBy = "empleado",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Administrador administrador;

    @OneToMany(mappedBy = "empleado")
    private List<Venta> ventas = new ArrayList<>();
}
