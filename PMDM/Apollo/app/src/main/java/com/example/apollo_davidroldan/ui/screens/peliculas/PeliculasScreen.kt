package com.example.apollo_davidroldan.ui.screens.peliculas

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.apollo_davidroldan.R
import com.example.apollo_davidroldan.common.Constantes
import com.example.apollo_davidroldan.domain.modelo.Pelicula
import com.example.apollo_davidroldan.ui.screens.premios.LoadingAnimation

@Composable
fun PeliculasScreen(
    viewModel: PeliculasViewModel = hiltViewModel(),
    onViewDetalle: (Int) -> Unit,
    bottomNavigationBar : @Composable () -> Unit = {},
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    Peliculas(
        state.value,
        { viewModel.handleEvent(PeliculasEvent.ErrorVisto) },
        onViewDetalle,
        bottomNavigationBar
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Peliculas(
    state: PeliculasState,
    errorVisto: () -> Unit,
    onViewDetalle: (Int) -> Unit,
    bottomNavigationBar : @Composable () -> Unit = {},
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = bottomNavigationBar
    ) { innerPadding ->
        LaunchedEffect(state.error) {
            state.error?.let {
                snackbarHostState.showSnackbar(
                    message = state.error.toString(),
                    actionLabel = Constantes.DISMISS,
                    duration = SnackbarDuration.Short,
                )
                errorVisto()
            }
        }
        if (state.loading){
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                CircularProgressIndicator()
                LoadingAnimation(state.loading)
            }
        } else{
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                items(items = state.peliculas, key = { pelicula -> pelicula.id }) { pelicula ->
                    PeliculaItem(
                        pelicula = pelicula,
                        onViewDetalle = onViewDetalle,
                        modifier = Modifier.animateItemPlacement(
                            animationSpec = tween(1000)
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun PeliculaItem(
    pelicula: Pelicula,
    onViewDetalle: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier
        .fillMaxWidth()
        .padding(dimensionResource(id = R.dimen.smallmedium_padding))
        .clickable { onViewDetalle(pelicula.id) }) {
        Row(modifier = Modifier.padding(dimensionResource(id = R.dimen.smallmedium_padding))) {
            Text(
                text = pelicula.titulo,
                modifier = Modifier.weight(weight = 0.8F)
            )
            Text(
                text = pelicula.anoLanzamiento.toString(),
                modifier = Modifier.weight(weight = 0.2F)
            )
            Text(
                text = pelicula.duracion.toString(),
                modifier = Modifier.weight(weight = 0.1F)
            )
        }

    }
}

@Preview
@Composable
fun PeliculasPreview() {
    val peliculas = listOf(
        Pelicula(
            id = 1,
            titulo = "Pelicula 1",
            anoLanzamiento = 2021,
            duracion = 120
        ),
        Pelicula(
            id = 2,
            titulo = "Pelicula 2",
            anoLanzamiento = 2022,
            duracion = 130
        ),
        Pelicula(
            id = 3,
            titulo = "Pelicula 3",
            anoLanzamiento = 2023,
            duracion = 140
        )
    )
    Peliculas(
        state = PeliculasState(peliculas = peliculas),
        errorVisto = {},
        onViewDetalle = {}
    )
}