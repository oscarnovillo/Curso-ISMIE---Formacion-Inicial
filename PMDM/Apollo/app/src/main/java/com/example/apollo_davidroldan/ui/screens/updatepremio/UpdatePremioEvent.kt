package com.example.apollo_davidroldan.ui.screens.updatepremio

import com.example.apollo_davidroldan.domain.modelo.Pelicula

sealed class UpdatePremioEvent {
    class UpdatePremio() : UpdatePremioEvent()
    class GetPremio(val id: Int) : UpdatePremioEvent()

    class OnNombreChange(val nombre: String) : UpdatePremioEvent()
    class OnCategoriaChange(val categoria: String) : UpdatePremioEvent()
    class OnAnoChange(val ano: Int) : UpdatePremioEvent()
    class OnPeliculaChange(val pelicula: Pelicula) : UpdatePremioEvent()

    object ErrorVisto : UpdatePremioEvent()
}
