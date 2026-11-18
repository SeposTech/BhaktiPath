package com.spiritual.bhaktipath.presentation.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.play.core.appupdate.AppUpdateManager
import com.spiritual.bhaktipath.presentation.screens.DetailScreen
import com.spiritual.bhaktipath.presentation.screens.HomeScreen
import com.spiritual.bhaktipath.presentation.screens.SplashScreen

@Composable
fun AppNavigation(
    appUpdateManager: AppUpdateManager
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val activity = context as? Activity

    NavHost(navController, startDestination = Screen.Splash.route) {

        composable(Screen.Splash.route) {
            SplashScreen(appUpdateManager = appUpdateManager, onNavigateToHome = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }

        composable(route = Screen.Home.route) {
            HomeScreen(onItemClick = {
                navController.navigate(Screen.Detail.createRoute(it))
            }, onBackClick = {
                if (!navController.popBackStack()) {
                    activity?.finish()
                }
            })
        }

        composable(route = Screen.Detail.route) {
            navArgument("itemId") {
                type = androidx.navigation.NavType.IntType
            }
            DetailScreen(onBackPress = { navController.popBackStack() })
        }

    }

}
