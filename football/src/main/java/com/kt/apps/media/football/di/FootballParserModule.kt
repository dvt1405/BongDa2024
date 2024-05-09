package com.kt.apps.media.football.di

import com.kt.apps.media.api.IFootballMatchRepository
import com.kt.apps.media.api.IWebsiteLinkFinderRepository
import com.kt.apps.media.football.di.annotations.FootballRepository
import com.kt.apps.media.football.di.annotations.FootballRepositoryType
import com.kt.apps.media.football.nightyp.repository.VeBoFootballMatchRepository
import com.kt.apps.media.football.nightyp.repository.NinetyWebLinkFinderRepositoryImpl
import com.kt.apps.media.football.xoilacz.XoilacZCORepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface FootballParserModule {
    @Binds
    @Singleton
    fun provideFootballParserModule(
        footballParserModuleImpl: NinetyWebLinkFinderRepositoryImpl
    ): IWebsiteLinkFinderRepository

    @Binds
    @FootballRepository(FootballRepositoryType.VeBo)
    fun provideFootballParserModule2(
        footballParserModuleImpl: VeBoFootballMatchRepository
    ): IFootballMatchRepository

    @Binds
    @FootballRepository(FootballRepositoryType.XoiLacZCO)
    fun provideFootballParserModule3(
        footballParserModuleImpl: XoilacZCORepository
    ): IFootballMatchRepository
}