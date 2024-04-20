package org.example.graphql_davidroldan.domain.modelo.graphql;

public record PremioInput(
        String nombre, String categoria, Integer ano, Long peliculaGanadoraId
) {
}
