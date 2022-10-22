package com.mvi.insecureauthentication.ui.screens.register

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mvi.insecureauthentication.R
import com.mvi.insecureauthentication.ui.ext.showToast
import com.mvi.insecureauthentication.ui.navigation.Route
import org.orbitmvi.orbit.compose.collectSideEffect

private fun handleSideEffect(
    navController: NavController,
    effect: RegisterEffect,
    context: Context
) {
    when (effect) {
        is RegisterEffect.RegisterSuccess -> navController.navigate(Route.AuthNavigation.Login.name)
        is RegisterEffect.RegisterError -> context.showToast(context.getString(R.string.general_error))
    }
}

@Composable
fun RegisterScreen(navController: NavController, viewModel: RegisterViewModel = hiltViewModel()) {
    val context = LocalContext.current

    val state by viewModel.container.stateFlow.collectAsState()

    viewModel.collectSideEffect {
        handleSideEffect(
            navController,
            it,
            context
        )
    }

    RegisterScreenContent(
        email = state.email,
        onEmailChange = viewModel::onEmailChange,
        password = state.password,
        onPasswordChange = viewModel::onPasswordChange,
        name = state.name,
        onNameChange = viewModel::onNameChange,
        onRegisterClick = viewModel::register,
    )
}


@Composable
fun RegisterScreenContent(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    name: String,
    onNameChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = stringResource(id = R.string.register))
        Spacer(modifier = Modifier.padding(48.dp))
        TextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text(stringResource(id = R.string.email)) }
        )
        Spacer(modifier = Modifier.padding(24.dp))
        TextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text(stringResource(id = R.string.password)) }
        )
        TextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text(stringResource(id = R.string.name)) }
        )

        TextButton(
            onClick = onRegisterClick,
            enabled = name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()
        ) {
            Text(text = stringResource(id = R.string.register))
        }
    }
}