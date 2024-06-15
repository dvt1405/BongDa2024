package com.kt.apps.media.football.nightyp.repository

import com.kt.apps.media.api.IWebsiteLinkFinderRepository
import com.kt.apps.media.api.models.football.FootballMatch
import com.kt.apps.media.api.models.players.Player
import com.kt.apps.media.api.models.stream.Link
import com.kt.apps.media.football.nightyp.models.NinetyPages
import com.kt.apps.media.sharedutils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named

class VeBoFootballMatchRepository @Inject constructor(
    webLinkFinderRepo: IWebsiteLinkFinderRepository,
    @Named(Constants.NETWORK_IO_SCOPE)
    private val _scope: CoroutineScope
) : AbsNinetyRepository(webLinkFinderRepo) {
    override var webName: String = NinetyPages.VeBo.id
    override suspend fun getAllMatches(): Flow<List<FootballMatch>> = flow {

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