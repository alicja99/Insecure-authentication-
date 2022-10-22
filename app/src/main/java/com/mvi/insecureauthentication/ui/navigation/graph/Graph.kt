package com.mvi.insecureauthentication.ui.navigation.graph

sealed class Graph (val name: String){
    object Root : Graph("graph_root")
    object Splash : Graph("splash_root")
    object Auth : Graph("graph_auth")
    object Home : Graph("graph_home")
}