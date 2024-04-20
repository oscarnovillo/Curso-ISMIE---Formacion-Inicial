package com.example.apollo_davidroldan.data.sources.remote

import com.apollographql.apollo3.ApolloClient
import com.example.apollo_davidroldan.common.Constantes
import com.example.apollo_davidroldan.data.mappers.toGenero
import com.example.apollo_davidroldan.domain.modelo.Genero
import com.example.apollo_davidroldan.utils.NetworkResult
import com.serverschema.GetGenerosPeliculaQuery
import javax.inject.Inject


class GenerosRemoteDataSource @Inject constructor(
    private val apolloClient: ApolloClient,
) {
    suspend fun getGenerosPelicula(idPelicula: Int): NetworkResult<List<Genero>> {
        return try {
            val response =
                apolloClient.query(GetGenerosPeliculaQuery(idPelicula.toString())).execute()
            if (response.hasErrors()) {
                NetworkResult.Error(
                    response.errors?.first()?.message ?: Constantes.ERROR_DESCONOCIDO
                )
            } else {
                val generos =
                    response.data?.getGenerosPelicula?.map { it.toGenero() } ?: emptyList()
                if (generos.isNotEmpty()) {
                    NetworkResult.Success(generos)
                } else {
                    NetworkResult.Error(Constantes.NOGENEROS)
                }
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }

}

