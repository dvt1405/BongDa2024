package com.kt.apps.media.football.nightyp.repository

import com.kt.apps.media.api.IWebsiteLinkFinderRepository
import com.kt.apps.media.football.nightyp.models.NinetyWebsite
import com.kt.apps.media.sharedutils.Constants
import com.kt.apps.media.sharedutils.transformDeferred
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import okhttp3.OkHttpClient
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class NinetyWebLinkFinderRepositoryImpl @Inject constructor(
    private val _httpClient: OkHttpClient,
    @Named(Constants.NETWORK_IO_SCOPE)
    private val _scope: CoroutineScope
) : IWebsiteLinkFinderRepository {
    private var _lastTimeSync: Long = 0
    private val _cookies = mutableMapOf<String, String>()
    private var _parsingJob: Deferred<List<NinetyWebsite>>? = null
    private val _childPages = mutableMapOf<String, NinetyWebsite>()
    override suspend fun findWebsiteLink(name: String): Deferred<String> {
        if (true == _parsingJob?.isActive) {
            _parsingJob?.join()
        }
        if (System.currentTimeMillis() - _lastTimeSync < REFRESH_PARENT_PAGE_THRESHOLD) {
            runCatching {
                transformDeferred(getCacheWebsitePages(name)) {
                    it.url
                }
            }.onFailure {
                _lastTimeSync = 0
            }.onSuccess {
                return it
            }
        }
        val parentPage = getRemoteWebsiteConfig()

        return transformDeferred(getParsingJob(parentPage)) {
            it.find {
                it.id == name
            }?.url ?: throw IllegalArgumentException("Cannot find website link")
        }
    }

    private fun getParsingJob(url: String): Deferred<List<NinetyWebsite>> = _scope.async {
        runCatching {
            Jsoup.connect(url)
                .timeout(60_000)
                .get()
//                .also {
//                    it.userAgent(Constants.HEADER_USER_AGENT_CHROME)
//                    it.header(
//                        Constants.HEADER_ACCEPT_LANGUAGE,
//                        Constants.HEADER_ACCEPT_LANGUAGE_VALUE
//                    )
//                    if (_cookies.isNotEmpty()) {
//                        it.cookies(_cookies)
//                    }
//                }
//                .method(Connection.Method.GET)
//                .execute()
//                .also {
//                    _cookies.putAll(it.cookies())
//                }
//                .parse()
                .parseListPages()
        }.onFailure {
            when (it) {
                is IllegalArgumentException -> {
                    throw it
                }

                is IOException -> {
                    _cookies.clear()
                    throw it
                }

                else -> {
                    throw it
                }
            }
        }.onSuccess {
            _lastTimeSync = System.currentTimeMillis()
        }.getOrThrow()
    }.also {
        _parsingJob = it
    }

    private suspend fun getCacheWebsitePages(name: String): Deferred<NinetyWebsite> = _scope.async {
        synchronized(_childPages) {
            _childPages[name]
                ?: throw IllegalArgumentException("Cannot find website link from cache")
        }
    }

    private suspend fun getRemoteWebsiteConfig(): String {
        return PARENT_PAGE
    }

    private fun Element.parseListPages(): List<NinetyWebsite> {
        return this.select(".cl_item > a")
            .map {
                val link = it.attr("href")
                val logo = it.select(".item-logo > img")
                    .attr("src")
                val siteName = it.select(".item-name").text()
                NinetyWebsite(siteName, siteName, link, logo)
            }
    }

    fun cancel(exception: CancellationException? = null) {
        _parsingJob?.cancel(exception)
    }

    companion object {
        private const val PARENT_PAGE = "https://bit.ly/tiengruoi"
        private const val REFRESH_PARENT_PAGE_THRESHOLD = 1000 * 60 * 15
    }
}