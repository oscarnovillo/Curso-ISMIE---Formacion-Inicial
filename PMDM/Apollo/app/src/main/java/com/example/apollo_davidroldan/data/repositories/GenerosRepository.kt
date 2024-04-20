package com.example.apollo_davidroldan.data.repositories

import com.example.apollo_davidroldan.data.sources.remote.GenerosRemoteDataSource
import com.example.apollo_davidroldan.domain.modelo.Genero
import com.example.apollo_davidroldan.utils.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GenerosRepository @Inject constructor(
    private val remoteDataSource: GenerosRemoteDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    fun getGenerosPorPelicula(idPelicula: Int): Flow<NetworkResult<List<Genero>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = remoteDataSource.getGenerosPelicula(idPelicula)
            emit(result)
        }.flowOn(dispatcher)
    }
}