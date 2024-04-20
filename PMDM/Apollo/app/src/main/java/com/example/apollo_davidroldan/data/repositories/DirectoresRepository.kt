package com.example.apollo_davidroldan.data.repositories

import com.example.apollo_davidroldan.data.sources.remote.DirectoresRemoteDataSource
import com.example.apollo_davidroldan.domain.modelo.Director
import com.example.apollo_davidroldan.utils.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DirectoresRepository @Inject constructor(
    private val remoteDataSource: DirectoresRemoteDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    fun getDirectorPorPelicula(idPelicula: Int): Flow<NetworkResult<Director>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = remoteDataSource.getDirectorPelicula(idPelicula)
            emit(result)
        }.flowOn(dispatcher)
    }

    fun getDirectores(): Flow<NetworkResult<List<String>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = remoteDataSource.getDirectores()
            emit(result)
        }.flowOn(dispatcher)
    }
}