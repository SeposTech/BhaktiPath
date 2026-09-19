package com.spiritual.bhaktipath.utils

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent

object AnalyticsHelper {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    fun init(context: Context) {
        firebaseAnalytics = FirebaseAnalytics.getInstance(context)
    }

    fun trackScreen(screenName: String) {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            param(FirebaseAnalytics.Param.SCREEN_CLASS, screenName)
        }
    }

    fun logEvent(eventName: String, params: Map<String, String> = emptyMap()) {
        firebaseAnalytics.logEvent(eventName) {
            params.forEach { (key, value) ->
                param(key, value)
            }
        }
    }


}