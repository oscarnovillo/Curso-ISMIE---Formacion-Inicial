package com.example.apollo_davidroldan.data.sources.remote

import com.apollographql.apollo3.ApolloClient
import com.example.apollo_davidroldan.common.Constantes
import com.example.apollo_davidroldan.data.mappers.toPremio
import com.example.apollo_davidroldan.domain.modelo.Premio
import com.example.apollo_davidroldan.utils.NetworkResult
import com.serverschema.AddPremioMutation
import com.serverschema.DeletePremioMutation
import com.serverschema.GetPremioPorIdQuery
import com.serverschema.PremiosQuery
import com.serverschema.UpdatePremioMutation
import javax.inject.Inject


class PremiosRemoteDataSource @Inject constructor(
    private val apolloClient: ApolloClient,
) {
    suspend fun getPremios(): NetworkResult<List<Premio>> {
        return try {
            val response = apolloClient.query(PremiosQuery()).execute()
            if (response.hasErrors()) {
                NetworkResult.Error(
                    response.errors?.first()?.message ?: Constantes.ERROR_DESCONOCIDO
                )
            } else {
                val premios = response.data?.getPremios?.map { it.toPremio() } ?: emptyList()
                if (premios.isNotEmpty()) {
                    NetworkResult.Success(premios)
                } else {
                    NetworkResult.Error(Constantes.ERROR_DESCONOCIDO)
                }
            }

        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }

    suspend fun deletePremio(id: Int): NetworkResult<Unit> {
        return try {
            val response = apolloClient.mutation(DeletePremioMutation(id.toString())).execute()
            if (response.hasErrors()) {
                val error = response.errors?.first()
                if (error?.message == Constantes.FORBIDDEN) {
                    NetworkResult.Error(Constantes.NO_PERMISSION)
                } else {
                    NetworkResult.Error(
                        error?.message ?: Constantes.ERROR_DESCONOCIDO
                    )
                }
            } else {
                NetworkResult.Success(Unit)
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }

    suspend fun updatePremio(premio: Premio): NetworkResult<Premio> {
        return try {
            val response = apolloClient.mutation(
                UpdatePremioMutation(
                    premio.id.toString(),
                    premio.nombre,
                    premio.categoria,
                    premio.ano,
                    premio.pelicula.id.toString()
                )
            ).execute()
            if (response.hasErrors()) {
                val error = response.errors?.first()
                if (error?.message == Constantes.FORBIDDEN) {
                    NetworkResult.Error(Constantes.NO_PERMISSION)
                } else {
                    NetworkResult.Error(
                        error?.message ?: Constantes.ERROR_DESCONOCIDO
                    )
                }
            } else {
                val premio = response.data?.updatePremio?.toPremio()
                if (premio != null) {
                    NetworkResult.Success(premio)
                } else {
                    NetworkResult.Error(Constantes.ERROR_DESCONOCIDO)
                }
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }

    suspend fun getPremio(id: Int): NetworkResult<Premio> {
        return try {
            val response = apolloClient.query(GetPremioPorIdQuery(id.toString())).execute()
            if (response.hasErrors()) {
                NetworkResult.Error(
                    response.errors?.first()?.message ?: Constantes.ERROR_DESCONOCIDO
                )
            } else {
                val premio = response.data?.getPremioPorId?.toPremio()
                if (premio != null) {
                    NetworkResult.Success(premio)
                } else {
                    NetworkResult.Error(Constantes.ERROR_DESCONOCIDO)
                }
            }

        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }

    suspend fun addPremio(premio: Premio): NetworkResult<Premio> {
        return try {
            val response = apolloClient.mutation(
                AddPremioMutation(
                    premio.nombre,
                    premio.categoria,
                    premio.ano,
                    premio.pelicula.id.toString()
                )
            ).execute()
            if (response.hasErrors()) {
                val error = response.errors?.first()
                if (error?.message == Constantes.FORBIDDEN) {
                    NetworkResult.Error(Constantes.NO_PERMISSION)
                } else {
                    NetworkResult.Error(
                        error?.message ?: Constantes.ERROR_DESCONOCIDO
                    )
                }
            } else {
                val premio = response.data?.addPremio?.toPremio()
                if (premio != null) {
                    NetworkResult.Success(premio)
                } else {
                    NetworkResult.Error(Constantes.ERROR_DESCONOCIDO)
                }
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }
}

