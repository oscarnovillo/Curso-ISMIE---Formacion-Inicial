package com.example.apollo_davidroldan.ui.screens.premios

sealed class PremiosEvent {
    class DeletePremio(val id: Int) : PremiosEvent()

    object ErrorVisto : PremiosEvent()
}
