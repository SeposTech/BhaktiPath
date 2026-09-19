package com.spiritual.bhaktipath.application

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.spiritual.bhaktipath.BuildConfig
import com.spiritual.bhaktipath.utils.AnalyticsHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BhaktiPath : Application() {

    override fun onCreate() {
        super.onCreate()
        AnalyticsHelper.init(this)
        // Disable Crashlytics in debug builds to avoid polluting crash reports
        FirebaseCrashlytics.getInstance().isCrashlyticsCollectionEnabled = !BuildConfig.DEBUG
    }
}