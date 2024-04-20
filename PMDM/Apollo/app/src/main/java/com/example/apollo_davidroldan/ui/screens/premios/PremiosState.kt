package com.example.apollo_davidroldan.ui.screens.premios

import com.example.apollo_davidroldan.domain.modelo.Premio

data class PremiosState(
    val premios: List<Premio> = emptyList(),
    val error: String? = null,
    val loading: Boolean = false
)
