package com.example.apollo_davidroldan.domain.modelo

data class Premio(
    val id: Int = 0,
    val nombre: String = "",
    val categoria: String = "",
    val ano: Int = 0,
    val pelicula: Pelicula = Pelicula()
)