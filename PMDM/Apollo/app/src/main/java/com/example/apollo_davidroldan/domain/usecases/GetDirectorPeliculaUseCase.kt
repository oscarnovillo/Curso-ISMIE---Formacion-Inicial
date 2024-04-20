package com.example.apollo_davidroldan.domain.usecases

import com.example.apollo_davidroldan.data.repositories.DirectoresRepository
import javax.inject.Inject


class GetDirectorPeliculaUseCase @Inject constructor(private var repository: DirectoresRepository){
    operator fun invoke(idPelicula: Int) = repository.getDirectorPorPelicula(idPelicula)
}