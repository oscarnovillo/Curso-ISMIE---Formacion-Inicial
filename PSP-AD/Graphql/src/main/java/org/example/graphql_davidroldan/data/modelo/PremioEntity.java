package org.example.graphql_davidroldan.data.modelo;

import jakarta.persistence.*;
import lombok.*;
import org.example.graphql_davidroldan.common.Constantes;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = Constantes.PREMIOS)
public class PremioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false)
    private int ano;

    @ManyToOne
    @JoinColumn(name = Constantes.PELICULA_ID)
    private PeliculaEntity peliculaGanadora;
}
