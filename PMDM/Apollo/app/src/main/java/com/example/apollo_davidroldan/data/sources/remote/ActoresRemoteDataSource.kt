package com.example.apollo_davidroldan.data.sources.remote

import com.apollographql.apollo3.ApolloClient
import com.example.apollo_davidroldan.common.Constantes
import com.example.apollo_davidroldan.data.mappers.toActor
import com.example.apollo_davidroldan.data.mappers.toActorName
import com.example.apollo_davidroldan.domain.modelo.Actor
import com.example.apollo_davidroldan.utils.NetworkResult
import com.serverschema.ActoresQuery
import com.serverschema.GetActoresPeliculaQuery
import javax.inject.Inject


class ActoresRemoteDataSource @Inject constructor(
    private val apolloClient: ApolloClient,
) {
    suspend fun getActoresPelicula(idPelicula: Int): NetworkResult<List<Actor>> {
        return try {
            val response =
                apolloClient.query(GetActoresPeliculaQuery(idPelicula.toString())).execute()
            if (response.hasErrors()) {
                NetworkResult.Error(
                    response.errors?.first()?.message ?: Constantes.ERROR_DESCONOCIDO
                )
            } else {
                val actores = response.data?.getActoresPelicula?.map { it.toActor() } ?: emptyList()
                if (actores.isNotEmpty()) {
                    NetworkResult.Success(actores)
                } else {
                    NetworkResult.Error(Constantes.ERROR_DESCONOCIDO)
                }
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }

    suspend fun getActores(): NetworkResult<List<String>> {
        return try {
            val response = apolloClient.query(ActoresQuery()).execute()
            if (response.hasErrors()) {
                NetworkResult.Error(
                    response.errors?.first()?.message ?: Constantes.ERROR_DESCONOCIDO
                )
            } else {
                val actores = response.data?.getActores?.map { it.toActorName() } ?: emptyList()
                if (actores.isNotEmpty()) {
                    NetworkResult.Success(actores)
                } else {
                    NetworkResult.Error(Constantes.NOACTORES)
                }
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }
}

