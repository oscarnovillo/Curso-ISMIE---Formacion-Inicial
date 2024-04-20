package com.example.apollo_davidroldan.data.repositories

import com.example.apollo_davidroldan.data.sources.remote.PeliculasRemoteDataSource
import com.example.apollo_davidroldan.domain.modelo.Pelicula
import com.example.apollo_davidroldan.utils.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PeliculasRepository @Inject constructor(
    private val remoteDataSource: PeliculasRemoteDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun getPeliculas(): Flow<NetworkResult<List<Pelicula>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = remoteDataSource.getPeliculas()
            emit(result)
        }.flowOn(dispatcher)
    }
}