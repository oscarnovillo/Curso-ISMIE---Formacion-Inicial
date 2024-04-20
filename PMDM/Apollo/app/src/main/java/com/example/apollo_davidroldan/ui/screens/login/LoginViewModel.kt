package com.example.apollo_davidroldan.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apollo_davidroldan.domain.modelo.Credentials
import com.example.apollo_davidroldan.domain.usecases.LoginUseCase
import com.example.apollo_davidroldan.domain.usecases.RegisterUseCase
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
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {
    private val _uiState: MutableStateFlow<LoginState> by lazy {
        MutableStateFlow(LoginState())
    }
    val uiState: StateFlow<LoginState> = _uiState


    init {
        _uiState.value = LoginState(
            error = null,
            credentials = Credentials(),
        )
    }

    fun handleEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> {
                login()
            }

            is LoginEvent.Register -> {
                register()
            }

            is LoginEvent.OnUserNameChange -> _uiState.update {
                it.copy(
                    credentials = it.credentials.copy(
                        username = event.username
                    )
                )
            }

            is LoginEvent.OnPasswordChange -> _uiState.update {
                it.copy(
                    credentials = it.credentials.copy(
                        password = event.password
                    )
                )
            }

            LoginEvent.ErrorVisto -> _uiState.update { it.copy(error = null) }
        }
    }

    private fun login() {
        if (_uiState.value.credentials.username.isEmpty() || _uiState.value.credentials.password.isEmpty() || _uiState.value.credentials.username.isBlank() || _uiState.value.credentials.password.isBlank()) {
            _uiState.update { it.copy(error = ConstantesPantallas.USER_OR_PASS_EMPTY) }
        } else {
            viewModelScope.launch {
                loginUseCase(_uiState.value.credentials)
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
                            is NetworkResult.Success -> _uiState.update {
                                it.copy(
                                    login = true,
                                    loading = false
                                )
                            }

                        }
                    }
            }
        }
    }

    private fun register() {
        if (_uiState.value.credentials.username.isEmpty() || _uiState.value.credentials.password.isEmpty() || _uiState.value.credentials.username.isBlank() || _uiState.value.credentials.password.isBlank()) {
            _uiState.update { it.copy(error = ConstantesPantallas.USER_OR_PASS_EMPTY) }
        } else {
            viewModelScope.launch {
                registerUseCase(_uiState.value.credentials)
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
                            is NetworkResult.Success -> _uiState.update {
                                it.copy(
                                    error = ConstantesPantallas.USER_REGISTRADO,
                                    loading = false
                                )
                            }

                        }
                    }
            }
        }

    }
}