package com.spiritual.bhaktipath.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spiritual.bhaktipath.presentation.screens.DetailScreen
import com.spiritual.bhaktipath.presentation.screens.HomeScreen
import com.spiritual.bhaktipath.presentation.screens.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Screen.Splash.route) {

        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(route = Screen.Home.route) {
            HomeScreen(onItemClick = {
                navController.navigate(Screen.Detail.createRoute(it))
            })
        }

        composable(route = Screen.Detail.route) {
            val itemId = it.arguments?.getInt("itemId")
            DetailScreen(onBackPress = { navController.popBackStack() })
        }

    }

}
