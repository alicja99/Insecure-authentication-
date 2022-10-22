package com.mvi.insecureauthentication.ui.screens.splash

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mvi.insecureauthentication.ui.components.Loader
import com.mvi.insecureauthentication.ui.navigation.graph.Graph
import org.orbitmvi.orbit.compose.collectSideEffect


private fun handleSideEffect(
    navController: NavController,
    effect: SplashEffect
) {
    when (effect) {
        is SplashEffect.NavigateToAuthentication -> navController.navigate(Graph.Auth.name)
        is SplashEffect.NavigateToHome -> navController.navigate(Graph.Home.name)
    }
}

@Composable
fun SplashScreen(navController: NavController, viewModel: SplashViewModel = hiltViewModel()) {
    viewModel.collectSideEffect { handleSideEffect(navController, it) }

    SplashScreenContent()
}

@Composable
fun SplashScreenContent() {
    Loader()
}