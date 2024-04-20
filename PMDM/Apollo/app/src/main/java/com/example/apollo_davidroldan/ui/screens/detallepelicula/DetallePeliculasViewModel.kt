package com.example.apollo_davidroldan.ui.screens.detallepelicula

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apollo_davidroldan.domain.usecases.GetActoresPeliculaUseCase
import com.example.apollo_davidroldan.domain.usecases.GetDirectorPeliculaUseCase
import com.example.apollo_davidroldan.domain.usecases.GetGenerosPeliculaUseCase
import com.example.apollo_davidroldan.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetallePeliculasViewModel @Inject constructor(
    private val getActoresPeliculaUseCase: GetActoresPeliculaUseCase,
    private val getDirectorPeliculaUseCase: GetDirectorPeliculaUseCase,
    private val getGenerosPeliculaUseCase: GetGenerosPeliculaUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<DetallePeliculasState> by lazy {
        MutableStateFlow(DetallePeliculasState())
    }

    val uiState: StateFlow<DetallePeliculasState> = _uiState

    init {
        _uiState.value = DetallePeliculasState(
            actores = emptyList(),
            director = null,
            error = null,
            loading = false
        )
    }

    fun handleEvent(event: DetallePeliculasEvent) {
        when (event) {
            is DetallePeliculasEvent.GetDetalle -> {
                getDirector(event.id)
                getActores(event.id)
                getGeneros(event.id)
            }
            DetallePeliculasEvent.ErrorVisto -> _uiState.update { it.copy(error = null) }
        }
    }

    private fun getDirector(id: Int) {
        _uiState.update { it.copy(loading = true) }
        viewModelScope.launch {
            getDirectorPeliculaUseCase(id)
                .catch(action = { cause ->
                    _uiState.update {
                        it.copy(
                            error = cause.message,
                            loading = false
                        )
                    }
                })
                .collect { director ->
                    when (director) {
                        is NetworkResult.Error -> {
                            _uiState.update {
                                it.copy(
                                    error = director.message,
                                    loading = false
                                )
                            }
                        }
                        is NetworkResult.Loading -> _uiState.update { it.copy(loading = true) }
                        is NetworkResult.Success -> {
                            director.data?.let { director ->
                                _uiState.update { it.copy(director = director, loading = false) }
                            }
                        }
                    }
                }
        }
    }

    private fun getActores(id: Int) {
        _uiState.update { it.copy(loading = true) }
        viewModelScope.launch {
            getActoresPeliculaUseCase(id)
                .catch(action = { cause ->
                    _uiState.update {
                        it.copy(
                            error = cause.message,
                            loading = false
                        )
                    }
                })
                .collect { actores ->
                    when (actores) {
                        is NetworkResult.Error -> {
                            _uiState.update {
                                it.copy(
                                    error = actores.message,
                                    loading = false
                                )
                            }
                        }
                        is NetworkResult.Loading -> _uiState.update { it.copy(loading = true) }
                        is NetworkResult.Success -> {
                            actores.data?.let { actores ->
                                _uiState.update { it.copy(actores = actores, loading = false) }
                            }
                        }
                    }
                }
        }
    }

    private fun getGeneros(id: Int) {
        _uiState.update { it.copy(loading = true) }
        viewModelScope.launch {
            getGenerosPeliculaUseCase(id)
                .catch(action = { cause ->
                    _uiState.update {
                        it.copy(
                            error = cause.message,
                            loading = false
                        )
                    }
                })
                .collect { generos ->
                    when (generos) {
                        is NetworkResult.Error -> {
                            _uiState.update {
                                it.copy(
                                    error = generos.message,
                                    loading = false
                                )
                            }
                        }
                        is NetworkResult.Loading -> _uiState.update { it.copy(loading = true) }
                        is NetworkResult.Success -> {
                            generos.data?.let { generos ->
                                _uiState.update { it.copy(generos = generos, loading = false) }
                            }
                        }
                    }
                }
        }
    }


}