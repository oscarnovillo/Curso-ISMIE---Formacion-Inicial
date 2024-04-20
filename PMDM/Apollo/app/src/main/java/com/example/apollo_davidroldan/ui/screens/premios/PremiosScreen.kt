package com.example.apollo_davidroldan.ui.screens.premios

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.apollo_davidroldan.R
import com.example.apollo_davidroldan.common.Constantes
import com.example.apollo_davidroldan.domain.modelo.Pelicula
import com.example.apollo_davidroldan.domain.modelo.Premio
import kotlinx.coroutines.delay

@Composable
fun PremiosScreen(
    viewModel: PremiosViewModel = hiltViewModel(),
    onPremioClick: (Int) -> Unit,
    addPremioClick: () -> Unit,
    bottomNavigationBar: @Composable () -> Unit = {},
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    Premios(
        state.value,
        { viewModel.handleEvent(PremiosEvent.ErrorVisto) },
        onPremioClick,
        addPremioClick,
        bottomNavigationBar,
        { viewModel.handleEvent(PremiosEvent.DeletePremio(it)) }
    )
}

@Composable
fun Premios(
    state: PremiosState,
    errorVisto: () -> Unit,
    onPremioClick: (Int) -> Unit,
    addPremioClick: () -> Unit,
    bottomNavigationBar: @Composable () -> Unit = {},
    onDelete: (Int) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = bottomNavigationBar,
        floatingActionButton = {
            FloatingActionButton(
                onClick = addPremioClick
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add)
                )
            }
        }
    ) { innerPadding ->
        LaunchedEffect(state.error) {
            state.error?.let {
                snackbarHostState.showSnackbar(
                    message = state.error.toString(),
                    actionLabel = Constantes.DISMISS,
                    duration = SnackbarDuration.Short
                )
                errorVisto()
            }

        }
        if (state.loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                LoadingAnimation(visible = state.loading)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                items(items = state.premios, key = { premio -> premio.id }) { premio ->
                    SwipeToDeleteContainer(
                        item = premio,
                        onDelete = { onDelete(premio.id) }
                    ) { premio ->
                        PremioItem(
                            premio = premio,
                            onViewDetalle = onPremioClick,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PremioItem(
    premio: Premio,
    onViewDetalle: (Int) -> Unit,
    modifier: Modifier = Modifier.background(MaterialTheme.colorScheme.background)
) {
    Card(modifier = modifier
        .fillMaxWidth()
        .padding(dimensionResource(id = R.dimen.smallmedium_padding))
        .clickable { onViewDetalle(premio.id) }) {
        Row(modifier = Modifier.padding(dimensionResource(id = R.dimen.smallmedium_padding))) {
            Text(
                text = premio.nombre,
                modifier = Modifier.weight(weight = 0.5F)
            )
            Text(
                text = premio.categoria,
                modifier = Modifier.weight(weight = 0.5F)
            )
        }
        Row(modifier = Modifier.padding(dimensionResource(id = R.dimen.smallmedium_padding))) {
            Text(
                text = premio.pelicula.titulo,
                modifier = Modifier.weight(weight = 0.8F)
            )


        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit
) {
    var isRemoved by remember { mutableStateOf(false) }
    val state = rememberDismissState(
        confirmValueChange = { value ->
            if (value == DismissValue.DismissedToStart) {
                isRemoved = true
                true
            } else {
                false
            }

        }
    )

    LaunchedEffect(key1 = isRemoved) {
        if (isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item)
        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(
                durationMillis = animationDuration
            ),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismiss(
            state = state,
            background = {
                DeleteBackground(swipeDismissState = state)
            },
            dismissContent = { content(item) },
            directions = setOf(DismissDirection.EndToStart),
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteBackground(
    swipeDismissState: DismissState
) {
    val color = if (swipeDismissState.dismissDirection == DismissDirection.EndToStart) {
        Color.Red
    } else {
        Color.Transparent
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(dimensionResource(id = R.dimen.medium_padding)),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = stringResource(id = R.string.delete),
            tint = Color.White,
        )
    }
}

@Composable
fun LoadingAnimation(visible: Boolean) {
    val enterExit =
        rememberInfiniteTransition(label = stringResource(id = R.string.loadinganimation))
    val alpha by enterExit.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = stringResource(id = R.string.loadinganimationalpha)
    )

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = alpha)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}


@Preview
@Composable
fun previewPersonaItem() {
    PremioItem(
        premio = Premio(
            1,
            "Peter Jackson",
            "Mejor guión original",
            2023,
            Pelicula(1, "El Señor de los Anillos: Las Dos Torres", 2023, 132)
        ),
        onViewDetalle = { }
    )
}
