package com.example.apollo_davidroldan.domain.usecases

import com.example.apollo_davidroldan.data.repositories.ActoresRepository
import javax.inject.Inject


class GetNamesActoresUseCase @Inject constructor(private var repository: ActoresRepository){
    operator fun invoke() = repository.getActores()
}