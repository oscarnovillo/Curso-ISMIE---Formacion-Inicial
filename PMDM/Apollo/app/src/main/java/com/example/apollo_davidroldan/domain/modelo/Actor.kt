package com.example.apollo_davidroldan.domain.modelo

import java.time.LocalDate

data class Actor(
    val id: Int,
    val nombre: String,
    val nacionalidad: String,
    val fechaNacimiento: LocalDate,
)