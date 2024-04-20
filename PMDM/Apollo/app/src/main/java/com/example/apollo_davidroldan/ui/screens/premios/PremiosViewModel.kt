package com.example.apollo_davidroldan.ui.screens.premios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apollo_davidroldan.domain.usecases.DeletePremioUseCase
import com.example.apollo_davidroldan.domain.usecases.GetPremiosUseCase
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
class PremiosViewModel @Inject constructor(
    private val getPremiosUseCase: GetPremiosUseCase,
    private val deletePremioUseCase: DeletePremioUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<PremiosState> by lazy {
        MutableStateFlow(PremiosState())
    }

    val uiState: StateFlow<PremiosState> = _uiState

    init {
        _uiState.value = PremiosState(
            premios = emptyList(),
            error = null,
            loading = false
        )
        getPremios()
    }

    fun handleEvent(event: PremiosEvent) {
        when (event) {
            is PremiosEvent.DeletePremio -> {
                deletePremio(event.id)
            }

            PremiosEvent.ErrorVisto -> _uiState.update { it.copy(error = null) }
        }
    }

    private fun deletePremio(id: Int) {
        viewModelScope.launch {
            deletePremioUseCase(id)
                .catch(action = { cause ->
                    _uiState.update {
                        it.copy(
                            error = cause.message,
                            loading = false
                        )
                    }
                })
                .collect{result ->
                    when(result){
                        is NetworkResult.Error -> {
                            _uiState.update { it.copy(error = result.message) }
                        }
                        is NetworkResult.Loading -> {
                            _uiState.update { it.copy(loading = true) }
                        }
                        is NetworkResult.Success -> {
                            _uiState.update { it.copy(error = ConstantesPantallas.ELIMINADO) }
                            getPremios()
                        }
                    }
                }
        }
    }

    private fun getPremios() {
        _uiState.update { it.copy(loading = true) }
        viewModelScope.launch {
            getPremiosUseCase()
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
                            result.data?.let { premios ->
                                _uiState.update {
                                    it.copy(
                                        premios = premios,
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