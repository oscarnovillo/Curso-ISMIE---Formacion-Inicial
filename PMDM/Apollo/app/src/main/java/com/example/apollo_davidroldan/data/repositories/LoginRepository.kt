package com.example.apollo_davidroldan.data.repositories

import com.example.apollo_davidroldan.data.modelo.LoginTokens
import com.example.apollo_davidroldan.data.sources.remote.LoginRemoteDataSource
import com.example.apollo_davidroldan.domain.modelo.Credentials
import com.example.apollo_davidroldan.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class LoginRepository @Inject constructor(
    private val remoteDataSource: LoginRemoteDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    fun register(credentials: Credentials): Flow<NetworkResult<Unit>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = remoteDataSource.register(credentials)
            emit(result)
        }.flowOn(dispatcher)
    }

    fun login(credentials: Credentials): Flow<NetworkResult<LoginTokens>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = remoteDataSource.login(credentials)
            emit(result)
        }.flowOn(dispatcher)
    }


}