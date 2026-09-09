package com.spiritual.bhaktipath

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.spiritual.bhaktipath.presentation.navigation.AppNavigation
import com.spiritual.bhaktipath.ui.theme.BhaktiPathTheme

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