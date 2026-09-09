package com.spiritual.bhaktipath

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.spiritual.bhaktipath.presentation.navigation.AppNavigation
import com.spiritual.bhaktipath.ui.theme.BhaktiPathTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BhaktiPathTheme {
                AppNavigation()
            }
        }
    }
}