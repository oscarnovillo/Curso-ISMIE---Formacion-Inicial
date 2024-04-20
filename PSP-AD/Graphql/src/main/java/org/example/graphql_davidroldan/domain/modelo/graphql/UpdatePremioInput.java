package org.example.graphql_davidroldan.domain.modelo.graphql;

public record UpdatePremioInput(
        Long id, String nombre, String categoria, Integer ano, Long peliculaGanadoraId
) {
}
