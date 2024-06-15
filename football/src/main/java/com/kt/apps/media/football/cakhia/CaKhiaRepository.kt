package com.kt.apps.media.football.cakhia

import com.kt.apps.media.api.IFootballMatchRepository
import com.kt.apps.media.api.models.football.FootballMatch
import com.kt.apps.media.api.models.players.Player
import com.kt.apps.media.api.models.stream.Link
import com.kt.apps.media.sharedutils.di.coroutinescope.CoroutineDispatcherQualifier
import com.kt.apps.media.sharedutils.di.coroutinescope.CoroutineDispatcherType
import com.kt.apps.media.sharedutils.jsoupParse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CaKhiaRepository @Inject constructor(
    @CoroutineDispatcherQualifier(CoroutineDispatcherType.IO)
    private val ioDispatcher: CoroutineDispatcher
) : IFootballMatchRepository {
    override suspend fun getAllMatches(): Flow<List<FootballMatch>> {
        return flow {
            val response = jsoupParse(
                url = BASE_URL,
                cookie = emptyMap(),
            )
        }
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

    companion object {
        const val BASE_URL = "https://vaccinaid.org/"
        const val BASE_URL_2 = "https://cakhiaz.co/"
    }
}