package com.example.apollo_davidroldan.ui.screens.peliculas

import com.example.apollo_davidroldan.domain.modelo.Pelicula


data class PeliculasState(
    val peliculas: List<Pelicula> = emptyList(),
    val error: String? = null,
    val loading: Boolean = false,
)
