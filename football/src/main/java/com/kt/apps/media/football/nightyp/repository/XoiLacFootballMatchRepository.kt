package com.kt.apps.media.football.nightyp.repository

import com.kt.apps.media.api.IWebsiteLinkFinderRepository
import com.kt.apps.media.api.models.football.FootballMatch
import com.kt.apps.media.api.models.players.Player
import com.kt.apps.media.api.models.stream.Link
import com.kt.apps.media.football.nightyp.models.NinetyPages
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class XoiLacFootballMatchRepository @Inject constructor(
    websiteLinkFinderRepositoryImpl: IWebsiteLinkFinderRepository
) : AbsNinetyRepository(websiteLinkFinderRepositoryImpl) {

    override var webName: String = NinetyPages.XoiLac.id

    override suspend fun getAllMatches(): Flow<List<FootballMatch>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMatchById(id: String): Flow<Player> {
        TODO("Not yet implemented")
    }

    override suspend fun getMatchByLink(match: FootballMatch, link: Link): Flow<Player> {
        TODO("Not yet implemented")
    }

    override suspend fun getLiveMatches(): Flow<List<FootballMatch>> {
        TODO("Not yet implemented")
    }


    override suspend fun getLinkLiveStream(match: FootballMatch): Deferred<FootballMatch> {
        TODO("Not yet implemented")
    }

    override suspend fun getLinkLiveStream(
        match: FootballMatch,
        html: String
    ): Deferred<FootballMatch> {
        TODO("Not yet implemented")
    }
}