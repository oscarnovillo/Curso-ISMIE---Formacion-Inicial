package com.example.apollo_davidroldan.domain.usecases

import com.example.apollo_davidroldan.data.repositories.GenerosRepository
import javax.inject.Inject


class GetGenerosPeliculaUseCase @Inject constructor(private var repository: GenerosRepository){
    operator fun invoke(idPelicula: Int) = repository.getGenerosPorPelicula(idPelicula)
}