package com.example.apollo_davidroldan.ui.screens.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.apollo_davidroldan.R
import com.example.apollo_davidroldan.common.Constantes

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginDone: () -> Unit
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    Login(
        state.value,
        onLoginDone,
        { viewModel.handleEvent(LoginEvent.Register()) },
        { viewModel.handleEvent(LoginEvent.Login()) },
        { viewModel.handleEvent(LoginEvent.OnUserNameChange(it)) },
        { viewModel.handleEvent(LoginEvent.OnPasswordChange(it)) },
        { viewModel.handleEvent(LoginEvent.ErrorVisto) })

}

@Composable
fun Login(
    state: LoginState,
    onLoginDone: () -> Unit,
    clickregister: () -> Unit,
    clicklogin: () -> Unit,
    onUserNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    errorVisto: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.login) {
        if (state.login) {
            onLoginDone()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
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
            Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.medium_padding))) {
                UserField(
                    state.credentials.username, onUserNameChange
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.medium_padding)))
                PasswordField(
                    state.credentials.password, onPasswordChange
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.medium_padding)))
                LoginButton(clicklogin)
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.medium_padding)))
                RegisterButton(clickregister)
            }
            Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                Text(text = stringResource(id = R.string.roleadmin))
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.medium_padding)))
                Text(text = stringResource(id = R.string.roleuser))
            }
        }
    }
}

@Composable
fun RegisterButton(clickregister: () -> Unit) {
    Button(
        onClick = clickregister,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(stringResource(id = R.string.register))
    }
}

@Composable
fun LoginButton(clicklogin: () -> Unit) {
    Button(
        onClick = clicklogin,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(stringResource(id = R.string.login))
    }
}

@Composable
fun PasswordField(password: String, onPasswordChange: (String) -> Unit) {
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(stringResource(id = R.string.password)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation(),
        singleLine = true,
        maxLines = 1,
    )
}

@Composable
fun UserField(username: String, onUserNameChange: (String) -> Unit) {
    OutlinedTextField(
        value = username,
        onValueChange = onUserNameChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(stringResource(id = R.string.username)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        singleLine = true,
        maxLines = 1,
    )
}

@Preview
@Preview
@Composable
fun LoginScreenPreview() {
    Login(
        LoginState(),
        {},
        {},
        {},
        {},
        {},
        {}
    )
}
