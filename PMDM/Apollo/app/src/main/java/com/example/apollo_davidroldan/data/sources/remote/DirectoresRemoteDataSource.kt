package com.example.apollo_davidroldan.data.sources.remote

import com.apollographql.apollo3.ApolloClient
import com.example.apollo_davidroldan.common.Constantes
import com.example.apollo_davidroldan.data.mappers.toDirector
import com.example.apollo_davidroldan.data.mappers.toDirectorName
import com.example.apollo_davidroldan.domain.modelo.Director
import com.example.apollo_davidroldan.utils.NetworkResult
import com.serverschema.DirectoresQuery
import com.serverschema.GetDirectorPorPeliculaQuery
import javax.inject.Inject


class DirectoresRemoteDataSource @Inject constructor(
    private val apolloClient: ApolloClient,
) {
    suspend fun getDirectorPelicula(idPelicula: Int): NetworkResult<Director> {
        return try {
            val response =
                apolloClient.query(GetDirectorPorPeliculaQuery(idPelicula.toString())).execute()
            if (response.hasErrors()) {
                NetworkResult.Error(
                    response.errors?.first()?.message ?: Constantes.ERROR_DESCONOCIDO
                )
            } else {
                val director = response.data?.getDirectorPorPelicula?.toDirector()
                if (director != null) {
                    NetworkResult.Success(director)
                } else {
                    NetworkResult.Error(Constantes.ERROR_DESCONOCIDO)
                }
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }

    suspend fun getDirectores(): NetworkResult<List<String>> {
        return try {
            val response = apolloClient.query(DirectoresQuery()).execute()
            if (response.hasErrors()) {
                NetworkResult.Error(
                    response.errors?.first()?.message ?: Constantes.ERROR_DESCONOCIDO
                )
            } else {
                val directores =
                    response.data?.getDirectores?.map { it.toDirectorName() } ?: emptyList()
                if (directores.isNotEmpty()) {
                    NetworkResult.Success(directores)
                } else {
                    NetworkResult.Error(Constantes.ERROR_DESCONOCIDO)
                }
            }

        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }


}

