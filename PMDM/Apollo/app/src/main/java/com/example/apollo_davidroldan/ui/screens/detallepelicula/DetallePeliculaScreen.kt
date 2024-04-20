package com.example.apollo_davidroldan.ui.screens.detallepelicula

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.apollo_davidroldan.R
import com.example.apollo_davidroldan.domain.modelo.Actor
import com.example.apollo_davidroldan.domain.modelo.Director
import com.example.apollo_davidroldan.domain.modelo.Genero
import com.example.apollo_davidroldan.ui.screens.premios.LoadingAnimation
import java.time.LocalDate

@Composable
fun DetallePeliculaScreen(
    viewModel: DetallePeliculasViewModel = hiltViewModel(),
    peliculaId: Int,
    bottomNavigationBar: @Composable () -> Unit = {},
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()

    if (state.value.director == null){
        viewModel.handleEvent(DetallePeliculasEvent.GetDetalle(peliculaId))
    }

    DetallePelicula(
        state.value,
        { viewModel.handleEvent(DetallePeliculasEvent.ErrorVisto) },
        bottomNavigationBar
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetallePelicula(
    state: DetallePeliculasState,
    errorVisto: () -> Unit,
    bottomNavigationBar: @Composable () -> Unit = {},
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
                    duration = SnackbarDuration.Short,
                )
                errorVisto()
            }
        }
        if (state.loading){
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                LoadingAnimation(state.loading)
            }
        } else{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.medium_padding))
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.medium_padding)))
            Text(
                text = stringResource(id = R.string.director),
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.medium_padding))
            )
            DirectorCard(
                director = state.director,
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.medium_padding)))
            Text(
                text = stringResource(id = R.string.actores),
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.medium_padding))
            )
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.medium_padding))
                    .fillMaxWidth()
            ) {
                items(state.actores) { actor ->
                    ActorItem(
                        actor = actor,
                        modifier = Modifier.animateItemPlacement(
                            animationSpec = tween(1000)
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.medium_padding)))
            Text(
                text = stringResource(id = R.string.generospelicula),
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.medium_padding))
            )
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.medium_padding))
                    .fillMaxWidth()
            ) {
                items(state.generos) { genero ->
                    GeneroItem(
                        genero = genero,
                        modifier = Modifier.animateItemPlacement(
                            animationSpec = tween(1000)
                        )
                    )
                }
            }
        }
        }

    }
}


@Composable
fun GeneroItem(genero: Genero, modifier: Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.smallmedium_padding))
    ) {
        Box(modifier = Modifier.padding(dimensionResource(id = R.dimen.smallmedium_padding)), contentAlignment = Alignment.Center) {
            Text(
                text = genero.nombre,
            )
        }
    }
}


@Composable
fun ActorItem(actor: Actor, modifier: Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.smallmedium_padding))
    ) {
        Row(modifier = Modifier.padding(dimensionResource(id = R.dimen.smallmedium_padding))) {
            Text(
                text = actor.nombre,
                modifier = Modifier.weight(weight = 0.3F)
            )
            Text(
                text = actor.nacionalidad,
                modifier = Modifier.weight(weight = 0.3F)
            )
            Text(
                text = actor.fechaNacimiento.toString(),
                modifier = Modifier.weight(weight = 0.2F)
            )

        }
    }
}


@Composable
fun DirectorCard(
    director: Director?
) {
    Box(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.medium_padding)),
    ) {
        Row(modifier = Modifier.padding(dimensionResource(id = R.dimen.smallmedium_padding))) {
            Text(
                text = director?.nombre ?: stringResource(id = R.string.nodirector),
                modifier = Modifier.weight(weight = 0.3F)
            )
            Text(
                text = director?.nacionalidad ?: stringResource(id = R.string.nonacionalidad),
                modifier = Modifier.weight(weight = 0.3F)
            )
            Text(
                text = director?.fechaNacimiento.toString(),
                modifier = Modifier.weight(weight = 0.2F)
            )
        }
    }
}

@Preview
@Composable
fun DirectorCardPreview() {
    val actores = listOf(
        Actor(
            id = 1,
            nombre = "Leonardo DiCaprio",
            nacionalidad = "Estadounidense",
            fechaNacimiento = LocalDate.of(1990, 1, 1)
        ),
        Actor(
            id = 2,
            nombre = "Cillian Murphy",
            nacionalidad = "Irlandes",
            fechaNacimiento = LocalDate.of(1990, 1, 1)
        )
    )
    val generos = listOf(
        Genero(
            id = 1,
            nombre = "Ciencia Ficcion"
        ),
        Genero(
            id = 2,
            nombre = "Accion"
        )
    )
    DetallePelicula(DetallePeliculasState(
        director = Director(
            id = 1,
            nombre = "Christopher Nolan",
            nacionalidad = "Estadounidense",
            fechaNacimiento = LocalDate.of(1973, 10, 21)
        ),
        actores = actores,
        generos = generos
    ),
        {}
    )
}
