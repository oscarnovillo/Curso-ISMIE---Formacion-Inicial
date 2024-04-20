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
@Table(name = Constantes.GENEROS)
public class GeneroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @ManyToMany(mappedBy = Constantes.GENEROS, fetch = FetchType.EAGER)
    private List<PeliculaEntity> peliculas;
}
