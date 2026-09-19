package com.spiritual.bhaktipath.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun TrackScreen(screenName: String) {
    LaunchedEffect(Unit) {
        AnalyticsHelper.trackScreen(screenName)
    }
}