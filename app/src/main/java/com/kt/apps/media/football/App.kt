package com.kt.apps.media.football

import android.app.Application
import com.kt.apps.media.api.IAppSettingsRepository
import com.kt.apps.media.core.settings.AppSettings
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    val appSettingsRepository: IAppSettingsRepository by lazy {
        AppSettings.getInstance(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private lateinit var instance: App

        fun getInstance(): App {
            return instance
        }
    }
}