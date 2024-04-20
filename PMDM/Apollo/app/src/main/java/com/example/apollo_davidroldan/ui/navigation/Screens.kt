package com.example.apollo_davidroldan.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Movie
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.apollo_davidroldan.ui.common.ConstantesPantallas


val screensBottomBar = listOf(
    Screens(ConstantesPantallas.PELICULAS, Icons.Filled.Movie),
    Screens(ConstantesPantallas.PREMIOS, Icons.Filled.Celebration),
)

data class Screens(val route: String, val icon: ImageVector) {

}
