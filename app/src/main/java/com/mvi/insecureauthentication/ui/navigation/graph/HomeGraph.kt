package com.mvi.insecureauthentication.ui.navigation.graph

import androidx.activity.compose.BackHandler
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.mvi.insecureauthentication.ui.navigation.Route
import com.mvi.insecureauthentication.ui.screens.home.HomeScreen

fun NavGraphBuilder.homeGraph(navController: NavHostController) {
    navigation(
        route = Graph.Home.name,
        startDestination = Route.HomeNavigation.Home.name
    ) {
        composable(Route.HomeNavigation.Home.name) {
            BackHandler(enabled = true, onBack = {})
            HomeScreen(navController = navController)
        }
    }
}