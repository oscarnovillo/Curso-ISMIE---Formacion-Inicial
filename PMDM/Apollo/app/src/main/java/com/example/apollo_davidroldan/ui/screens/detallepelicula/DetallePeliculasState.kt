package com.example.apollo_davidroldan.ui.screens.detallepelicula

import com.example.apollo_davidroldan.domain.modelo.Actor
import com.example.apollo_davidroldan.domain.modelo.Director
import com.example.apollo_davidroldan.domain.modelo.Genero


data class DetallePeliculasState(
    val director: Director? = null,
    val actores: List<Actor> = emptyList(),
    val generos: List<Genero> = emptyList(),
    val error: String? = null,
    val loading: Boolean = false,
)
