package org.example.graphql_davidroldan.data.modelo;

import jakarta.persistence.*;
import lombok.*;
import org.example.graphql_davidroldan.common.Constantes;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = Constantes.PELICULAS)
public class PeliculaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private int anoLanzamiento;

    @Column(nullable = false)
    private int duracion;

    @ManyToOne
    @JoinColumn(name = Constantes.DIRECTOR_ID, nullable = false)
    private DirectorEntity director;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<ActorEntity> actores;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<GeneroEntity> generos;

    @OneToMany(mappedBy = Constantes.PELICULA_GANADORA, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PremioEntity> premios;
}
