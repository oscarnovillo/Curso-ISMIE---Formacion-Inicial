package org.example.graphql_davidroldan.data.modelo;

import jakarta.persistence.*;
import lombok.*;
import org.example.graphql_davidroldan.common.Constantes;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = Constantes.DIRECTORES)
public class DirectorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String nacionalidad;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate fechaNacimiento;

    @OneToMany(mappedBy = Constantes.DIRECTOR)
    private List<PeliculaEntity> peliculasDirigidas;
}
