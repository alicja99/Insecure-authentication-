package com.mvi.insecureauthentication.ui.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mvi.insecureauthentication.ui.navigation.Route
import com.mvi.insecureauthentication.ui.screens.splash.SplashScreen

@Composable
fun RootGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.Splash.name,
        route = Graph.Root.name
    ) {
        composable(Route.Splash.name){
            SplashScreen(navController = navController)
        }
        authGraph(navController)
        homeGraph(navController)
    }
}