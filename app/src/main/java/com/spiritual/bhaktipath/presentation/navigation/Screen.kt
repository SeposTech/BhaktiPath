package com.spiritual.bhaktipath.presentation.navigation

sealed class Screen(val route: String) {

    data object Splash : Screen("Splash")
    data object Home : Screen("Home")
}