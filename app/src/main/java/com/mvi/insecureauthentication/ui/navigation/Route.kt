package com.mvi.insecureauthentication.ui.navigation

sealed class Route(val name: String) {
    object Splash : Route("splash")

    object AuthNavigation: Route("auth_navigation") {
        object Login: Route("${super.name}/login")
        object Register: Route("${super.name}/register")
    }

    object HomeNavigation: Route("home_navigation") {
        object Home : Route("${super.name}/home")
    }
}