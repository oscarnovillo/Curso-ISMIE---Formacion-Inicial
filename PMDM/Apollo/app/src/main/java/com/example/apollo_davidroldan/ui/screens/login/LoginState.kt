package com.example.apollo_davidroldan.ui.screens.login

import com.example.apollo_davidroldan.domain.modelo.Credentials


data class LoginState(
    val error: String? = null,
    val login: Boolean = false,
    val credentials: Credentials = Credentials(),
    val loading: Boolean = false
)