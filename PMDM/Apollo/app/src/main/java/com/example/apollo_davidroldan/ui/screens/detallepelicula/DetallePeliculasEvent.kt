package com.example.apollo_davidroldan.ui.screens.detallepelicula

sealed class DetallePeliculasEvent {
    data class GetDetalle(val id: Int) : DetallePeliculasEvent()

    object ErrorVisto : DetallePeliculasEvent()
}