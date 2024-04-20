package org.example.graphql_davidroldan.data.modelo;


import jakarta.persistence.*;
import lombok.*;
import org.example.graphql_davidroldan.common.Constantes;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = Constantes.ACTORES)
public class ActorEntity {
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

    @ManyToMany(mappedBy = Constantes.ACTORES, fetch = FetchType.EAGER)
    private List<PeliculaEntity> peliculasActuadas;
}
