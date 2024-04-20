package com.example.apollo_davidroldan.ui.screens.updatepremio

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.example.apollo_davidroldan.common.Constantes
import com.example.apollo_davidroldan.domain.modelo.Pelicula
import com.example.apollo_davidroldan.ui.screens.addpremio.AnyoDropdown
import com.example.apollo_davidroldan.ui.screens.addpremio.CategoriaField
import com.example.apollo_davidroldan.ui.screens.addpremio.NameDropdown
import com.example.apollo_davidroldan.ui.screens.addpremio.PeliculaDropdown

@Composable
fun UpdatePremioScreen(
    viewModel: UpdatePremioViewModel = hiltViewModel(),
    onPremioUpdated: () -> Unit,
    premioId: Int,
    bottomNavigationBar : @Composable () -> Unit = {}
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    if (state.value.premio.id == 0){
        viewModel.handleEvent(UpdatePremioEvent.GetPremio(premioId))
    }

    UpdatePremio(
        state.value,
        { viewModel.handleEvent(UpdatePremioEvent.ErrorVisto) },
        bottomNavigationBar,
        { viewModel.handleEvent(UpdatePremioEvent.UpdatePremio()) },
        { viewModel.handleEvent(UpdatePremioEvent.OnNombreChange(it)) },
        { viewModel.handleEvent(UpdatePremioEvent.OnCategoriaChange(it)) },
        { viewModel.handleEvent(UpdatePremioEvent.OnAnoChange(it)) },
        { viewModel.handleEvent(UpdatePremioEvent.OnPeliculaChange(it)) },
        onPremioUpdated
    )
}

@Composable
fun UpdatePremio(
    state: UpdatePremioState,
    errorVisto: () -> Unit,
    bottomNavigationBar: @Composable () -> Unit,
    onUpdatePremioClick: () -> Unit,
    onNombreChange: (String) -> Unit,
    onCategoriaChange: (String) -> Unit,
    onAnoChange: (Int) -> Unit,
    onPeliculaChange: (Pelicula) -> Unit,
    onPremioUpdated: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.updatedone){
        if (state.updatedone){
            onPremioUpdated()
        }
    }

    Scaffold (
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = bottomNavigationBar
    ) {innerPadding ->
        LaunchedEffect(state.error) {
            state.error?.let {
                snackbarHostState.showSnackbar(
                    message = state.error,
                    actionLabel = Constantes.DISMISS,
                    duration = SnackbarDuration.Short
                )
                errorVisto()
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column (modifier = Modifier.padding(dimensionResource(id = R.dimen.medium_padding))) {
                Text(text = stringResource(id = R.string.ganador))
                NameDropdown(state.premio.nombre, onNombreChange, state.actores, state.directores)
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.medium_padding)))
                Text(text = stringResource(id =R.string.categoria))
                CategoriaField(state.premio.categoria, onCategoriaChange)
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.medium_padding)))
                Text(text = stringResource(id =R.string.anyo))
                AnyoDropdown(state.premio.ano, onAnoChange)
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.medium_padding)))
                Text(text = stringResource(id = R.string.pelicula))
                PeliculaDropdown(state.premio.pelicula, onPeliculaChange, state.peliculas)
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.medium_padding)))
            }
            Column(modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = dimensionResource(id = R.dimen.medium_padding))) {
                UpdateButton(onUpdatePremioClick)
            }
        }
    }
}

@Composable
fun UpdateButton(onUpdatePremioClick: () -> Unit) {
    FloatingActionButton(onClick = { onUpdatePremioClick()}) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.small_padding))
        ){
            Icon(
                imageVector = Icons.Default.Update,
                contentDescription = stringResource(id = R.string.update)
            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.small_padding)))
            Text(text = stringResource(id = R.string.update))
        }
    }
}


@Preview
@Composable
fun PreviewAddPremio() {
    UpdatePremio(UpdatePremioState(), {}, {}, {}, {}, {}, {}, {}, {})
}