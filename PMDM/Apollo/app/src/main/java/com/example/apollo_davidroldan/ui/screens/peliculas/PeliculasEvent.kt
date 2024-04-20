package com.example.apollo_davidroldan.ui.screens.peliculas

sealed class PeliculasEvent {
    class GetPeliculas() : PeliculasEvent()

    object ErrorVisto : PeliculasEvent()
}