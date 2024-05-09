package com.kt.apps.media.football.nightyp

import com.kt.apps.media.football.nightyp.models.NinetyPages
import com.kt.apps.media.football.nightyp.repository.NinetyWebLinkFinderRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class NinetyWebLinkFinderRepositoryImplTest {

    private lateinit var ninetyWebLinkFinderRepositoryImpl: NinetyWebLinkFinderRepositoryImpl

    @Before
    fun setUp() {
        ninetyWebLinkFinderRepositoryImpl = NinetyWebLinkFinderRepositoryImpl()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun findWebsiteLink_90p() = runBlocking {
        val result = ninetyWebLinkFinderRepositoryImpl.findWebsiteLink(NinetyPages.Phut90.id)
            .await()
        assertEquals("https://90phut12.live/home?utm_source=landing2&utm_medium=landing2", result)
    }

    @Test
    fun findWebsiteLink_ThapCam() = runBlocking {
        val result = ninetyWebLinkFinderRepositoryImpl.findWebsiteLink(NinetyPages.ThapCam.id)
            .await()
        assertEquals("https://thapcam.tv/?utm_source=landing&utm_medium=landing", result)
    }

    @Test
    fun findWebsiteLink_Xoilac() = runBlocking {
        val result = ninetyWebLinkFinderRepositoryImpl.findWebsiteLink(NinetyPages.XoiLac.id)
            .await()
        assertEquals("https://xoilac16.org/home?utm_source=landing&utm_medium=landing", result)
    }


    @Test
    fun findWebsiteLink_VeBo() = runBlocking {
        val result = ninetyWebLinkFinderRepositoryImpl.findWebsiteLink(NinetyPages.VeBo.id)
            .await()
        assertEquals("https://vebo.tv/home?utm_source=landing&utm_medium=landing", result)
    }

    @Test
    fun findWebsiteLink_BanhKhuc() = runBlocking {
        val result = ninetyWebLinkFinderRepositoryImpl.findWebsiteLink(NinetyPages.BanhKhuc.id)
            .await()
        assertEquals("https://banhkhuc3.net/home?utm_source=landing&utm_medium=landing", result)
    }
}