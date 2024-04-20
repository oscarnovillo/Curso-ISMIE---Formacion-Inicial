package com.example.apollo_davidroldan.domain.usecases

import com.example.apollo_davidroldan.data.modelo.LoginTokens
import com.example.apollo_davidroldan.data.repositories.LoginRepository
import com.example.apollo_davidroldan.data.sources.remote.TokenManager
import com.example.apollo_davidroldan.domain.modelo.Credentials
import com.example.apollo_davidroldan.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    var repository: LoginRepository,
    private val tokenManager: TokenManager,
){
    suspend operator fun invoke(credentials : Credentials): Flow<NetworkResult<LoginTokens>> {
        val loginResult = repository.login(credentials)

        loginResult.collect{ result ->
            if (result is NetworkResult.Success){
                result.data?.accessToken?.let { tokenManager.saveAccessToken(it) }
                result.data?.refreshToken?.let { tokenManager.saveRefreshToken(it) }
            }
        }

        return loginResult
    }
}
