package pe.uni.poo_v_g7.bibliotecaapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "Ubicacion",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {
                        "sede",
                        "pasillo",
                        "estante",
                        "nivel"
                }
        )
)
@Getter
@Setter
@NoArgsConstructor
public class Ubicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ubicacion")
    private Integer idUbicacion;

    @Column(nullable = false, length = 100)
    private String sede;

    @Column(nullable = false, length = 50)
    private String pasillo;

    @Column(nullable = false, length = 50)
    private String estante;

    @Column(nullable = false, length = 50)
    private String nivel;

    @OneToMany(mappedBy = "ubicacion")
    private Set<Ejemplar> ejemplares = new HashSet<>();
}
