package com.example.apollo_davidroldan.domain.usecases

import com.example.apollo_davidroldan.data.repositories.PremiosRepository
import javax.inject.Inject

class GetPremiosUseCase @Inject constructor(private var repository: PremiosRepository){
    operator fun invoke() = repository.getPremios()
}