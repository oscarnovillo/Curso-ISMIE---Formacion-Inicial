package com.example.apollo_davidroldan.data.sources.remote

import com.apollographql.apollo3.ApolloClient
import com.example.apollo_davidroldan.common.Constantes
import com.example.apollo_davidroldan.data.mappers.toPelicula
import com.example.apollo_davidroldan.domain.modelo.Pelicula
import com.example.apollo_davidroldan.utils.NetworkResult
import com.serverschema.PeliculasQuery
import javax.inject.Inject


class PeliculasRemoteDataSource @Inject constructor(
    private val loginService: LoginService,
    private val tokenManager: TokenManager,
    private val apolloClient: ApolloClient,
) {
    suspend fun getPeliculas(): NetworkResult<List<Pelicula>> {
        return try {
            val response = apolloClient.query(PeliculasQuery()).execute()
            if (response.hasErrors()) {
                NetworkResult.Error(
                    response.errors?.first()?.message ?: Constantes.ERROR_DESCONOCIDO
                )
            } else {
                val peliculas = response.data?.getPeliculas?.map { it.toPelicula() } ?: emptyList()
                if (peliculas.isNotEmpty()) {
                    NetworkResult.Success(peliculas)
                } else {
                    NetworkResult.Error(Constantes.NOPELICULAS)
                }
            }

        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }

}

