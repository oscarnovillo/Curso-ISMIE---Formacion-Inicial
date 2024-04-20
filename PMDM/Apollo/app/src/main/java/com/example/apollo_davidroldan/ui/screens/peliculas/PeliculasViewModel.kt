package com.example.apollo_davidroldan.ui.screens.peliculas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apollo_davidroldan.domain.usecases.GetPeliculasUseCase
import com.example.apollo_davidroldan.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeliculasViewModel @Inject constructor(
    private val getPeliculasUseCase: GetPeliculasUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<PeliculasState> by lazy {
        MutableStateFlow(PeliculasState())
    }

    val uiState: StateFlow<PeliculasState> = _uiState

    init {
        _uiState.value = PeliculasState(
            peliculas = emptyList(),
            error = null,
            loading = false
        )
        getPeliculas()
    }

    fun handleEvent(event: PeliculasEvent) {
        when (event) {
            is PeliculasEvent.GetPeliculas -> {
                getPeliculas()
            }

            PeliculasEvent.ErrorVisto -> _uiState.update { it.copy(error = null) }
        }
    }

    private fun getPeliculas() {
        _uiState.update { it.copy(loading = true) }
        viewModelScope.launch {
            getPeliculasUseCase()
                .catch(action = { cause ->
                    _uiState.update {
                        it.copy(
                            error = cause.message,
                            loading = false
                        )
                    }
                })
                .collect { result ->
                    when (result) {
                        is NetworkResult.Error -> {
                            _uiState.update {
                                it.copy(
                                    error = result.message,
                                    loading = false
                                )
                            }
                        }
                        is NetworkResult.Loading -> _uiState.update { it.copy(loading = true) }
                        is NetworkResult.Success -> {
                            result.data?.let { peliculas ->
                                _uiState.update {
                                    it.copy(
                                        peliculas = peliculas,
                                        loading = false
                                    )
                                }
                            }
                        }
                    }
                }
        }
    }

}