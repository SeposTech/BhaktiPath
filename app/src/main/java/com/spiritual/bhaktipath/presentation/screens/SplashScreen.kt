package com.spiritual.bhaktipath.presentation.screens

import android.app.Activity
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.spiritual.bhaktipath.R
import com.spiritual.bhaktipath.utils.TrackScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun SplashScreen(modifier: Modifier = Modifier, appUpdateManager: AppUpdateManager,
                 onNavigateToHome: () -> Unit) {

    TrackScreen("SplashScreen")
    val context = LocalContext.current
    val activity = context as? Activity

    LaunchedEffect(Unit) {

        try {

            val appUpdateInfo =
                appUpdateManager.appUpdateInfo.await()

            if (
                appUpdateInfo.updateAvailability() ==
                UpdateAvailability.UPDATE_AVAILABLE &&
                appUpdateInfo.isUpdateTypeAllowed(
                    AppUpdateType.IMMEDIATE
                )
            ) {

                activity?.let {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.IMMEDIATE,
                        it,
                        100
                    )
                }

            } else {

                delay(2000.milliseconds)

                onNavigateToHome()
            }

        } catch (e: Exception) {

            delay(2000.milliseconds)

            onNavigateToHome()
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