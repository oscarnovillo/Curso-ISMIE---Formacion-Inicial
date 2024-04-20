package org.example.graphql_davidroldan.domain.modelo.graphql;

import java.time.LocalDate;

public record UpdateDirectorInput(
        Long id, String nombre, String nacionalidad, LocalDate fechaNacimiento
) {
}
