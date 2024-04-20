package com.example.apollo_davidroldan.domain.usecases

import com.example.apollo_davidroldan.data.repositories.ActoresRepository
import javax.inject.Inject


class GetActoresPeliculaUseCase @Inject constructor(private var repository: ActoresRepository){
    operator fun invoke(idPelicula: Int) = repository.getActoresPorPelicula(idPelicula)
}