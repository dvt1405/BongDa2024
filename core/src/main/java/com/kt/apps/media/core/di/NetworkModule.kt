package com.kt.apps.media.core.di

import com.kt.apps.media.api.livescore.XoilacZCOAPI
import com.kt.apps.media.sharedutils.di.coroutinescope.CoroutineDispatcherQualifier
import com.kt.apps.media.sharedutils.di.coroutinescope.CoroutineDispatcherType
import com.kt.apps.media.sharedutils.Constants
import com.kt.apps.media.sharedutils.di.api.APIQualifier
import com.kt.apps.media.sharedutils.di.api.APISource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Singleton
    @Provides
    fun provideHttpLogger(): Interceptor {
        return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    @Singleton
    @Provides
    fun provideHttpClient(
        provideHttpLogger: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(provideHttpLogger)
            .build()
    }

    @Provides
    @Named(Constants.NETWORK_IO_SCOPE)
    fun providesNetworkPoolScope(): CoroutineScope {
        return CoroutineScope(Dispatchers.IO + SupervisorJob())
    }

    @Provides
    @CoroutineDispatcherQualifier(CoroutineDispatcherType.IO)
    fun providesDispatcherIO(): CoroutineDispatcher = Dispatchers.IO


    @Provides
    @CoroutineDispatcherQualifier(CoroutineDispatcherType.DEFAULT)
    fun providesDispatcherDefault(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @CoroutineDispatcherQualifier(CoroutineDispatcherType.MAIN)
    fun providesDispatcherMain(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @Singleton
    fun provideXoilacZCOAPI(
        client: OkHttpClient
    ): XoilacZCOAPI {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(APISource.XoilacZCO.value)
            .build()
            .create(XoilacZCOAPI::class.java)
    }

}