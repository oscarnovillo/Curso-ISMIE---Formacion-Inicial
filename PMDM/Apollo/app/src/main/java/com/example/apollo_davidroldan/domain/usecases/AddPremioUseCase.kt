package com.example.apollo_davidroldan.domain.usecases

import com.example.apollo_davidroldan.data.repositories.PremiosRepository
import com.example.apollo_davidroldan.domain.modelo.Premio
import javax.inject.Inject

class AddPremioUseCase @Inject constructor(var repository: PremiosRepository){
    operator fun invoke(premio: Premio) = repository.addPremio(premio)
}