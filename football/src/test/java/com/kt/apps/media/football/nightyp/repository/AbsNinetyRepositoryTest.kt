package com.kt.apps.media.football.nightyp.repository

import com.kt.apps.media.api.models.football.FootballMatch
import com.kt.apps.media.api.models.players.Player
import com.kt.apps.media.api.models.stream.Link
import com.kt.apps.media.football.nightyp.models.NinetyPages
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class AbsNinetyRepositoryTest {

    lateinit var absNinetyRepository: AbsNinetyRepository

    @Before
    fun setUp() {
        absNinetyRepository = object : AbsNinetyRepository(
            NinetyWebLinkFinderRepositoryImpl()
        ) {
            override var webName: String = NinetyPages.VeBo.id

            override suspend fun getAllMatches(): Flow<List<FootballMatch>> {
                TODO("Not yet implemented")
            }

            override suspend fun getMatchById(id: String): Flow<Player> {
                TODO("Not yet implemented")
            }

            override suspend fun getMatchByLink(
                match: FootballMatch,
                link: Link
            ): Flow<Player> {
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
    }

    @Test
    fun getWebsiteLinkFinderRepository_XoiLac() = runBlocking {
        absNinetyRepository.webName = NinetyPages.XoiLac.id
        val result = absNinetyRepository.getWebsiteLinkFinderRepository()
        assertEquals("https://tructiep12.xoilac16.org/home", result)
    }


    @Test
    fun getWebsiteLinkFinderRepository_Vebo() = runBlocking {
        absNinetyRepository.webName = NinetyPages.VeBo.id
        val result = absNinetyRepository.getWebsiteLinkFinderRepository()
        assertEquals("https://live7.vebo16.net/home", result)
    }

    @Test
    fun getWebsiteLinkFinderRepository_90phut() = runBlocking {
        absNinetyRepository.webName = NinetyPages.VeBo.id
        val result = absNinetyRepository.getWebsiteLinkFinderRepository()
        assertEquals("https://tructiep3.90phut12.live/home", result)
    }

    @Test
    fun testGetWebsiteLinkFinderRepository() {
    }
}