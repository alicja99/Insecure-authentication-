package com.mvi.insecureauthentication.ui.screens.login

import android.content.Context
import androidx.compose.foundation.layout.*
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
import com.mvi.insecureauthentication.ui.navigation.graph.Graph
import org.orbitmvi.orbit.compose.collectSideEffect

private fun handleSideEffect(
    navController: NavController,
    effect: LoginEffect,
    context: Context
) {
    when (effect) {
        is LoginEffect.LoginSuccess -> navController.navigate(Graph.Home.name)
        is LoginEffect.InvalidData -> context.showToast(context.getString(R.string.invalid_data))
        is LoginEffect.NavigateToRegistration -> navController.navigate(Route.AuthNavigation.Register.name)
    }
}


@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {
    val context = LocalContext.current

    val state by viewModel.container.stateFlow.collectAsState()

    viewModel.collectSideEffect { handleSideEffect(navController, it, context) }

    LoginScreenContent(
        email = state.email,
        onEmailChange = viewModel::onEmailChange,
        password = state.password,
        onPasswordChange = viewModel::onPasswordChange,
        onLoginClick = viewModel::login,
        onNavigateToRegistrationClick = viewModel::onNavigateToRegistration
    )
}

@Composable
fun LoginScreenContent(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onNavigateToRegistrationClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = stringResource(id = R.string.login))
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
        TextButton(onClick = onLoginClick, enabled = email.isNotEmpty() && password.isNotEmpty()) {
            Text(text = stringResource(id = R.string.login))
        }
        TextButton(onClick = onNavigateToRegistrationClick) {
            Text(text = stringResource(id = R.string.go_to_registration))
        }
    }
}