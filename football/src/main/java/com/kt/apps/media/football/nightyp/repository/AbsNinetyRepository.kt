package com.kt.apps.media.football.nightyp.repository

import com.kt.apps.media.api.IFootballMatchRepository
import com.kt.apps.media.api.IWebsiteLinkFinderRepository
import com.kt.apps.media.api.models.football.FootballMatch
import com.kt.apps.media.sharedutils.getBaseUrl
import org.jsoup.Jsoup

abstract class AbsNinetyRepository(
    protected val websiteLinkFinderRepository: IWebsiteLinkFinderRepository
) : IFootballMatchRepository {
    protected var lastResult: Result<List<FootballMatch>>? = null
    protected var lastRefreshResult: Long = 0
    abstract var webName: String
    suspend fun getWebsiteLinkFinderRepository(): String {
        var pageLink = websiteLinkFinderRepository.findWebsiteLink(webName).await()
        return Jsoup.connect(pageLink)
            .execute()
            .also {
                pageLink = it.url().toString()
                println(it.url().toString())
                println(it.url().host)
            }
            .parse()
            .select(".mh-buttons > a")
            .also {
                println(it.toString())
            }
            .first()?.attr("href")!!
            .let {
                pageLink.getBaseUrl() + it
            }
    }
}