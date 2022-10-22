package com.mvi.insecureauthentication.ui.navigation.graph

import androidx.activity.compose.BackHandler
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.mvi.insecureauthentication.ui.navigation.Route
import com.mvi.insecureauthentication.ui.screens.login.LoginScreen
import com.mvi.insecureauthentication.ui.screens.register.RegisterScreen

fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation(route = Graph.Auth.name, startDestination = Route.AuthNavigation.Login.name) {
        composable(Route.AuthNavigation.Login.name) {
            BackHandler(enabled = true, onBack = {})
            LoginScreen(navController = navController)
        }
        composable(Route.AuthNavigation.Register.name) {
            RegisterScreen(navController = navController)
        }
    }
}