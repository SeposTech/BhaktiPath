package com.spiritual.bhaktipath.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.spiritual.bhaktipath.R
import com.spiritual.bhaktipath.presentation.navigation.Screen
import com.spiritual.bhaktipath.ui.theme.BhaktiPathTheme
import com.spiritual.bhaktipath.utils.TrackScreen
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun SplashScreen(modifier: Modifier = Modifier, navController: NavController) {

    TrackScreen("SplashScreen")
    LaunchedEffect(Unit) {
        delay(2000L.milliseconds)
        navController.navigate(Screen.Home.route) {
            popUpTo(Screen.Splash.route) { inclusive = true }
        }
    }

    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_splash_new),
            contentDescription = "BhaktiPath splash screen",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewSplashScreen() {
    BhaktiPathTheme {
        SplashScreen(navController = rememberNavController())
    }
}