package com.example.apollo_davidroldan.ui.screens.addpremio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apollo_davidroldan.domain.usecases.AddPremioUseCase
import com.example.apollo_davidroldan.domain.usecases.GetNamesActoresUseCase
import com.example.apollo_davidroldan.domain.usecases.GetNamesDirectoresUseCase
import com.example.apollo_davidroldan.domain.usecases.GetPeliculasUseCase
import com.example.apollo_davidroldan.domain.usecases.GetPremioUseCase
import com.example.apollo_davidroldan.ui.common.ConstantesPantallas
import com.example.apollo_davidroldan.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddPremioViewModel @Inject constructor(
    private val getPremioUseCase: GetPremioUseCase,
    private val getPeliculasUseCase: GetPeliculasUseCase,
    private val addPremioUseCase: AddPremioUseCase,
    private val getNamesActoresUseCase: GetNamesActoresUseCase,
    private val getNamesDIrectoresUseCase: GetNamesDirectoresUseCase

) : ViewModel() {

    private val _uiState: MutableStateFlow<AddPremioState> by lazy {
        MutableStateFlow(AddPremioState())
    }

    val uiState: StateFlow<AddPremioState> = _uiState

    init {
        _uiState.value = AddPremioState(
            error = null,
            loading = false,
            adddone = false,
        )
        getPeliculas()
        getActores()
        getDirectores()
    }

    fun handleEvent(event: AddPremioEvent) {
        when (event) {
            is AddPremioEvent.AddPremio -> {
                addPremio()
            }

            is AddPremioEvent.GetPremio -> {
                getPremio(event.id)
            }

            is AddPremioEvent.OnNombreChange -> {
                _uiState.update { it.copy(premio = it.premio.copy(nombre = event.nombre)) }
            }

            is AddPremioEvent.OnCategoriaChange -> {
                _uiState.update { it.copy(premio = it.premio.copy(categoria = event.categoria)) }
            }

            is AddPremioEvent.OnAnoChange -> {
                _uiState.update { it.copy(premio = it.premio.copy(ano = event.ano)) }
            }

            is AddPremioEvent.OnPeliculaChange -> {
                _uiState.update { it.copy(premio = it.premio.copy(pelicula = event.pelicula)) }
            }

            AddPremioEvent.ErrorVisto -> _uiState.update { it.copy(error = null) }
        }
    }

    private fun getPremio(id: Int) {
        _uiState.update { it.copy(loading = true) }
        viewModelScope.launch {
            getPremioUseCase(id)
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
                            result.data?.let { premio ->
                                _uiState.update { it.copy(premio = premio, loading = false) }
                            }
                        }
                    }
                }
        }
    }

    private fun addPremio() {
        if (_uiState.value.premio.nombre.isEmpty() || _uiState.value.premio.categoria.isEmpty() || _uiState.value.premio.ano == 0 || _uiState.value.premio.pelicula.titulo.isEmpty()) {
            _uiState.update { it.copy(error = ConstantesPantallas.ERRORCAMPOSVACIOS) }
            return
        } else{
            viewModelScope.launch {
                addPremioUseCase(_uiState.value.premio)
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
                                _uiState.update { it.copy(error = ConstantesPantallas.PREMIOANYADIDO) }
                                _uiState.update { it.copy(adddone = true) }
                            }
                        }
                    }
            }
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


    private fun getDirectores() {
        viewModelScope.launch {
            getNamesDIrectoresUseCase()
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
                            result.data?.let { directores ->
                                _uiState.update { it.copy(directores = directores) }
                            }
                        }
                    }
                }

        }
    }

    private fun getActores() {
        viewModelScope.launch {
            getNamesActoresUseCase()
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
                            result.data?.let { actores ->
                                _uiState.update { it.copy(actores = actores) }
                            }
                        }
                    }
                }
        }
    }
}
