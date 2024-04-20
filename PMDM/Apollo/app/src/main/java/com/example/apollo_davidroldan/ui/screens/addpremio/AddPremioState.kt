package com.example.apollo_davidroldan.ui.screens.addpremio

import com.example.apollo_davidroldan.domain.modelo.Pelicula
import com.example.apollo_davidroldan.domain.modelo.Premio

data class AddPremioState(
    val premio: Premio = Premio(),
    val actores: List<String> = emptyList(),
    val directores: List<String> = emptyList(),
    val peliculas: List<Pelicula> = emptyList(),
    val adddone: Boolean = false,
    val error: String? = null,
    val loading: Boolean = false,
)
