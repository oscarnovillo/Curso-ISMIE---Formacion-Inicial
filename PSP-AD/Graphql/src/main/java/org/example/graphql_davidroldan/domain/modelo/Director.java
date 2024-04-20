package org.example.graphql_davidroldan.domain.modelo;

import java.time.LocalDate;

public record Director(Long id, String nombre, String nacionalidad, LocalDate fechaNacimiento){
}
