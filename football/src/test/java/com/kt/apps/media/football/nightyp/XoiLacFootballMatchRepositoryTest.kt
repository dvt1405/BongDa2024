package com.kt.apps.media.football.nightyp

import com.kt.apps.media.football.nightyp.repository.NinetyWebLinkFinderRepositoryImpl
import com.kt.apps.media.football.nightyp.repository.XoiLacFootballMatchRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class XoiLacFootballMatchRepositoryTest {
    lateinit var xoiLacFootballMatchRepository: XoiLacFootballMatchRepository

    @Before
    fun setUp() {
        xoiLacFootballMatchRepository = XoiLacFootballMatchRepository(
            NinetyWebLinkFinderRepositoryImpl()
        )
    }

    @Test
    fun testFindPage() = runBlocking {
        val result = xoiLacFootballMatchRepository.getWebsiteLinkFinderRepository()
        assertEquals("https://tructiep12.xoilac16.org/home", result)
    }
}