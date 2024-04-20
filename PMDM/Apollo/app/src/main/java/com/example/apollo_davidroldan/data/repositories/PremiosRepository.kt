package com.example.apollo_davidroldan.data.repositories

import com.example.apollo_davidroldan.data.sources.remote.PremiosRemoteDataSource
import com.example.apollo_davidroldan.domain.modelo.Premio
import com.example.apollo_davidroldan.utils.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PremiosRepository @Inject constructor(
    private val remoteDataSource: PremiosRemoteDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    fun getPremios(): Flow<NetworkResult<List<Premio>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = remoteDataSource.getPremios()
            emit(result)
        }.flowOn(dispatcher)
    }

    fun getPremio(id: Int): Flow<NetworkResult<Premio>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = remoteDataSource.getPremio(id)
            emit(result)
        }.flowOn(dispatcher)
    }

    fun deletePremio(id: Int): Flow<NetworkResult<Unit>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = remoteDataSource.deletePremio(id)
            emit(result)
        }.flowOn(dispatcher)
    }

    fun updatePremio(premio: Premio): Flow<NetworkResult<Premio>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = remoteDataSource.updatePremio(premio)
            emit(result)
        }.flowOn(dispatcher)
    }

    fun addPremio(premio: Premio): Flow<NetworkResult<Premio>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = remoteDataSource.addPremio(premio)
            emit(result)
        }.flowOn(dispatcher)
    }

}