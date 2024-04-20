package org.example.graphql_davidroldan.domain.modelo;

import java.time.LocalDate;

public record Actor(Long id, String nombre, String nacionalidad, LocalDate fechaNacimiento) {
}
