package com.mvi.insecureauthentication.ui.screens.home

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
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
import com.mvi.insecureauthentication.repository.model.User
import com.mvi.insecureauthentication.ui.ext.showToast
import com.mvi.insecureauthentication.ui.navigation.graph.Graph
import org.orbitmvi.orbit.compose.collectSideEffect

private fun handleSideEffect(
    navController: NavController,
    effect: HomeEffect,
    context: Context
) {
    when (effect) {
        is HomeEffect.ShowError -> context.showToast(context.getString(R.string.general_error))
        is HomeEffect.NavigateToAuthentication -> navController.navigate(Graph.Auth.name)
    }
}


@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val context = LocalContext.current

    val state by viewModel.container.stateFlow.collectAsState()

    viewModel.collectSideEffect {
        handleSideEffect(
            navController,
            it,
            context
        )
    }

    HomeScreenContent(
        user = state.user,
        onLogoutClick = viewModel::logout,
    )
}

@Composable
fun HomeScreenContent(user: User?, onLogoutClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = stringResource(id = R.string.hello))
        Text(text = user?.name ?: "")
        Spacer(modifier = Modifier.padding(48.dp))

        TextButton(onClick = onLogoutClick) {
            Text(stringResource(id = R.string.logout))
        }
    }
}