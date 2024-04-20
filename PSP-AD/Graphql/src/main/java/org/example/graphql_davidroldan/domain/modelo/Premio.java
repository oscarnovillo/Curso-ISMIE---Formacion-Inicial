package org.example.graphql_davidroldan.domain.modelo;


public record Premio(Long id, String nombre, String categoria, Integer ano, Pelicula peliculaGanadora) {
}
