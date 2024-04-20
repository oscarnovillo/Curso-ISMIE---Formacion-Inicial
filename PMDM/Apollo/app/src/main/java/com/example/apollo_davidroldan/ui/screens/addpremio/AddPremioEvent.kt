package com.example.apollo_davidroldan.ui.screens.addpremio

import com.example.apollo_davidroldan.domain.modelo.Pelicula

sealed class AddPremioEvent {
    class AddPremio() : AddPremioEvent()
    class GetPremio(val id: Int) : AddPremioEvent()

    class OnNombreChange(val nombre: String) : AddPremioEvent()
    class OnCategoriaChange(val categoria: String) : AddPremioEvent()
    class OnAnoChange(val ano: Int) : AddPremioEvent()
    class OnPeliculaChange(val pelicula: Pelicula) : AddPremioEvent()

    object ErrorVisto : AddPremioEvent()
}
