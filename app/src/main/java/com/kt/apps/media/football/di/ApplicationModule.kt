package com.kt.apps.media.football.di

import com.kt.apps.media.api.IAppSettingsRepository
import com.kt.apps.media.core.settings.AppSettings
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ApplicationModule {

    @Binds
    @Singleton
    fun androidApplication(appSettings: AppSettings): IAppSettingsRepository
}