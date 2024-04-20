package com.example.apollo_davidroldan.domain.usecases

import com.example.apollo_davidroldan.data.repositories.PeliculasRepository
import javax.inject.Inject


class GetPeliculasUseCase @Inject constructor(private var repository: PeliculasRepository){
    operator fun invoke() = repository.getPeliculas()
}