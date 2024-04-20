package com.example.apollo_davidroldan.ui.screens.addpremio

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.apollo_davidroldan.R
import com.example.apollo_davidroldan.common.Constantes
import com.example.apollo_davidroldan.domain.modelo.Pelicula

@Composable
fun AddPremioScreen(
    viewModel: AddPremioViewModel = hiltViewModel(),
    bottomNavigationBar: @Composable () -> Unit = {},
    onAddDone: () -> Unit
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    AddPremio(
        state.value,
        { viewModel.handleEvent(AddPremioEvent.ErrorVisto) },
        bottomNavigationBar,
        { viewModel.handleEvent(AddPremioEvent.AddPremio()) },
        { viewModel.handleEvent(AddPremioEvent.OnNombreChange(it)) },
        { viewModel.handleEvent(AddPremioEvent.OnCategoriaChange(it)) },
        { viewModel.handleEvent(AddPremioEvent.OnAnoChange(it)) },
        { viewModel.handleEvent(AddPremioEvent.OnPeliculaChange(it)) },
        onAddDone,
    )

}

@Composable
fun AddPremio(
    state: AddPremioState,
    errorVisto: () -> Unit,
    bottomNavigationBar: @Composable () -> Unit = {},
    onAddPremioClick: () -> Unit,
    onNombreChange: (String) -> Unit,
    onCategoriaChange: (String) -> Unit,
    onAnoChange: (Int) -> Unit,
    onPeliculaChange: (Pelicula) -> Unit,
    onAddDone: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.adddone){
        if (state.adddone){
            onAddDone()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = bottomNavigationBar,
    ) { innerPadding ->
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
            }
            Column(modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = dimensionResource(id = R.dimen.medium_padding))) {
                AddButton(onAddPremioClick)
            }
        }
    }
}

@Composable
fun AddButton(onAddPremioClick: () -> Unit) {
    FloatingActionButton(onClick = { onAddPremioClick() }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.small_padding))
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(id =R.string.add)
            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.smallmedium_padding)))
            Text(text = stringResource(id = R.string.addpremio))
        }

    }
}

@Composable
fun PeliculaDropdown(
    pelicula: Pelicula,
    onPeliculaChange: (Pelicula) -> Unit,
    peliculas: List<Pelicula>
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null
            )
            Text(text = pelicula.titulo.ifEmpty { stringResource(id = R.string.seleccionapelicula) }, modifier = Modifier.clickable { expanded = true })
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            peliculas.forEach { opcion ->
                DropdownMenuItem(
                    onClick = {
                        onPeliculaChange(opcion)
                        expanded = false
                    },
                    text = { Text(text = opcion.titulo) }
                )
            }
        }
    }
}

@Composable
fun AnyoDropdown(ano: Int, onAnoChange: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null
            )
            Text(text = ano.toString(), modifier = Modifier.clickable { expanded = true })
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            (1900..2024).forEach { opcion ->
                DropdownMenuItem(
                    onClick = {
                        onAnoChange(opcion)
                        expanded = false
                    },
                    text = { Text(text = opcion.toString()) }
                )
            }
        }
    }
}

@Composable
fun CategoriaField(categoria: String, onCategoriaChange: (String) -> Unit) {
    OutlinedTextField(
        value = categoria,
        onValueChange = onCategoriaChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(stringResource(id = R.string.categoria_)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        singleLine = true,
        maxLines = 1,
    )
}

@Composable
fun NameDropdown(
    nombre: String,
    onNombreChange: (String) -> Unit,
    actores: List<String>,
    directores: List<String>
) {
    var esDirector by remember { mutableStateOf(true) }
    val opciones = if (esDirector) directores else actores

    Column (/*modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)*/) {
        Row {
            RadioButton(
                selected = esDirector,
                onClick = { esDirector = true }
            )
            Text(text = "Director", modifier = Modifier.align(Alignment.CenterVertically))
            RadioButton(
                selected = !esDirector,
                onClick = { esDirector = false }
            )
            Text(text = "Actor", modifier = Modifier.align(Alignment.CenterVertically))
        }
        var expanded by remember { mutableStateOf(false) }
        Box {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null
                )
                Text(text = nombre.ifEmpty { stringResource(id = R.string.seleccionanombre) }, modifier = Modifier.clickable { expanded = true })
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                opciones.forEach { opcion ->
                    DropdownMenuItem(
                        onClick = {
                            onNombreChange(opcion)
                            expanded = false
                        },
                        text = { Text(text = opcion) }
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewAddPremio() {
    AddPremio(AddPremioState(), {}, {}, {}, {}, {}, {}, {}, {})
}