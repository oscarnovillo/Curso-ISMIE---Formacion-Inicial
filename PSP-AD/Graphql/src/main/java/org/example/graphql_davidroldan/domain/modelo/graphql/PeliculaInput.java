package org.example.graphql_davidroldan.domain.modelo.graphql;

public record PeliculaInput(
        String titulo, String anoLanzamiento, String duracion, Long idDirector
) {
}
