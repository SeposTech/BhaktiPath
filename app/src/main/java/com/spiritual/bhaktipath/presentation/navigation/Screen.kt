package com.spiritual.bhaktipath.presentation.navigation

sealed class Screen(val route: String) {

    data object Splash : Screen("Splash")
    data object Home : Screen("Home")

    data object Detail : Screen("Detail/{itemId}") {
        fun createRoute(itemId: Int) = "Detail/$itemId"
    }
}