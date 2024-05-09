package com.kt.apps.media.api

import com.kt.apps.media.api.models.football.FootballMatch
import com.kt.apps.media.api.models.players.Player
import com.kt.apps.media.api.models.stream.Link
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow

interface IFootballMatchRepository {
    suspend fun getAllMatches(): Flow<List<FootballMatch>>
    suspend fun getMatchById(id: String): Flow<Player>
    suspend fun getMatchByLink(
        match: FootballMatch,
        link: Link
    ): Flow<Player>

    suspend fun getLiveMatches(): Flow<List<FootballMatch>>
    suspend fun filterMatchesByDate(date: String): Deferred<List<FootballMatch>>
    suspend fun filterMatchesByQuery(query: String): Deferred<List<FootballMatch>>
    suspend fun getLinkLiveStream(match: FootballMatch): Deferred<FootballMatch>
    suspend fun getLinkLiveStream(match: FootballMatch, html: String): Deferred<FootballMatch>
}