package com.example.apollo_davidroldan.data.repositories

import com.example.apollo_davidroldan.data.sources.remote.ActoresRemoteDataSource
import com.example.apollo_davidroldan.domain.modelo.Actor
import com.example.apollo_davidroldan.utils.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ActoresRepository @Inject constructor(
    private val remoteDataSource: ActoresRemoteDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun getActoresPorPelicula(idPelicula: Int): Flow<NetworkResult<List<Actor>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = remoteDataSource.getActoresPelicula(idPelicula)
            emit(result)
        }.flowOn(dispatcher)
    }

    fun getActores(): Flow<NetworkResult<List<String>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = remoteDataSource.getActores()
            emit(result)
        }.flowOn(dispatcher)
    }


}