package com.spiritual.bhaktipath

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.spiritual.bhaktipath.presentation.navigation.AppNavigation
import com.spiritual.bhaktipath.ui.theme.BhaktiPathTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var appUpdateManager: AppUpdateManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appUpdateManager = AppUpdateManagerFactory.create(this)
        setContent {
            BhaktiPathTheme {
                AppNavigation(appUpdateManager = appUpdateManager)
            }
        }
    }
}