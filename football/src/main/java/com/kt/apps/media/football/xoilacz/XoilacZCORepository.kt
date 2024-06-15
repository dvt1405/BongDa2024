package com.kt.apps.media.football.xoilacz

import android.util.Log
import com.google.gson.Gson
import com.kt.apps.media.api.IAppSettingsRepository
import com.kt.apps.media.api.IFootballMatchRepository
import com.kt.apps.media.api.livescore.XoilacZCOAPI
import com.kt.apps.media.api.models.football.FootballMatch
import com.kt.apps.media.api.models.football.FootballTeam
import com.kt.apps.media.api.models.players.Player
import com.kt.apps.media.api.models.stream.Link
import com.kt.apps.media.sharedutils.Constants
import com.kt.apps.media.sharedutils.ErrorCode
import com.kt.apps.media.sharedutils.di.coroutinescope.CoroutineDispatcherQualifier
import com.kt.apps.media.sharedutils.di.coroutinescope.CoroutineDispatcherType
import com.kt.apps.media.sharedutils.exceptions.NetworkExceptions
import com.kt.apps.media.sharedutils.getBaseUrl
import com.kt.apps.media.sharedutils.jsoupParse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okio.IOException
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.util.concurrent.TimeoutException
import java.util.concurrent.atomic.AtomicBoolean
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class XoilacZCORepository @Inject constructor(
    private val _httpClient: OkHttpClient,
    private val _appSettings: IAppSettingsRepository,
    @CoroutineDispatcherQualifier(CoroutineDispatcherType.IO)
    private val _ioDispatcher: CoroutineDispatcher,
    private val api: XoilacZCOAPI
) : IFootballMatchRepository {
    private var _cookie: Map<String, String> = emptyMap()
    private var _currentItems: MutableList<FootballMatch> = mutableListOf()
    private var _lastUpdate: Long = 0
    private var _baseUrl: String = DEFAULT_URL
    private var _isLoading: AtomicBoolean = AtomicBoolean(false)

    override suspend fun getAllMatches(): Flow<List<FootballMatch>> = withContext(_ioDispatcher) {
        _isLoading.set(true)
        val liveScoreJob: Deferred<XoilacZCOLiveScore> = getLiveScore(_baseUrl)
        if (System.currentTimeMillis() - _lastUpdate < 1000 * 60 * 5 && _currentItems.isNotEmpty()) {
            return@withContext flowOf(_currentItems)
                .map {
                    val liveScore = liveScoreJob.await()
                    mapWithScoreAndLiveStatus(it, liveScore)
                }
        }
        return@withContext getAllHtmlItems()
            .map {
                val liveScore = liveScoreJob.await()
                it.toFootballMatchListWithScore(liveScore).also {
                    synchronized(_currentItems) {
                        _currentItems = it.toMutableList()
                    }
                }
            }.retryWhen { cause: Throwable, attempt: Long ->
                attempt < 3 && cause is IOException
            }.catch { cause: Throwable ->
                when (cause) {
                    is IOException -> {
                        throw NetworkExceptions(
                            ErrorCode.ERROR_NETWORK,
                            cause.message ?: "Unknown error",
                            cause
                        )
                    }

                    is TimeoutException -> {
                        throw NetworkExceptions(
                            ErrorCode.ERROR_NETWORK,
                            cause.message ?: "Unknown error",
                            cause
                        )
                    }

                    else -> {
                        throw cause
                    }
                }

            }
            .onCompletion {
                if (it != null && _currentItems.isNotEmpty()) {
                    _lastUpdate = System.currentTimeMillis()
                }
                _isLoading.set(false)
            }
    }

    private fun Elements.toFootballMatchListWithScore(
        liveScore: XoilacZCOLiveScore
    ): List<FootballMatch> {
        return this.map {
            val match = it.toFootballMatch()
            val score = liveScore.firstOrNull {
                "${it.matchId}" == match.id
            }?.let {
                "${it.home.score} - ${it.away.score}"
            }
            match.copy(score = score ?: match.score)
        }
    }

    private fun mapWithScore(
        it: Elements,
        liveScore: XoilacZCOLiveScore
    ) = it.map {
        val footballMatch = it.toFootballMatch()
        val score = liveScore.firstOrNull {
            "${it.matchId}" == footballMatch.id
        }?.let {
            "${it.home.score} - ${it.away.score}"
        }
        footballMatch.copy(
            score = score ?: footballMatch.score
        )
    }

    private fun mapWithScoreAndLiveStatus(
        footballMatchList: List<FootballMatch>,
        liveScore: XoilacZCOLiveScore
    ) = footballMatchList.map { match ->
        val matchScore = liveScore.firstOrNull {
            "${it.matchId}" == match.id
        } ?: return@map match
        val liveStatus = matchScore.status.long
        match.copy(
            score = "${matchScore.home.score} - ${matchScore.away.score}",
            liveStatus = liveStatus
        )
    }

    private suspend fun getLiveScore(referer: String = "https://catalystcon.com/") =
        withContext(_ioDispatcher) {
            async {
                val liveScoreStr = api.liveScore(
                    mapOf(
                        "Referer" to referer,
                        "Origin" to referer.removeSuffix("/"),
                        Constants.HEADER_USER_AGENT to Constants.HEADER_USER_AGENT_CHROME
                    )
                ).string()
                Gson().fromJson(liveScoreStr, XoilacZCOLiveScore::class.java)
            }
        }

    private suspend fun getAllHtmlItems(): Flow<Elements> {
        return flow {
            val cookie = _cookie
            val response = jsoupParse(DEFAULT_URL, cookie)
            _baseUrl = response.responseUrl
            _cookie = response.cookie
            _appSettings.saveMapData(EXTRA_COOKIE_NAME, _cookie)
            val items = response.body.select(SELECTOR_ITEM)
            emit(items)
        }
    }

    private fun Element.toFootballMatch(): FootballMatch {
        val matchId = attributes().get("data-fid")
        val kickOffTimeInSecond = try {
            attributes().get("data-runtime").toLong()
        } catch (e: java.lang.Exception) {
            System.currentTimeMillis() / 1000
        }
        val a = getElementsByTag("a")[0]
        val link = a.attributes().get("href")
        val body = a.getElementsByClass("matches__item--body")[0]
        val header = a.getElementsByClass("matches__item--header")[0]
        val league = header.select(".matches__item--league > .text-ellipsis")[0].text()
        val date = header.select(".matches__item--date > .date")[0].text()
        val homeTeamLogo = body.select(".matches__team--home > .team__logo > img[src]")[0]
            .attributes()
            .get("src")
        val homeTeamName = body.select(".matches__team--home > .team__name")[0].text()
        val awayTeamLogo =
            body.select(".matches__team--away > .team__logo > img[src]")[0]
                .attributes()
                .get("src")
        val awayTeamName = body.select(".matches__team--away > .team__name")[0].text()
        val status = try {
            body.select(".matches__status > span.time").text()
        } catch (e: Exception) {
            ""
        }
        val score = try {
            body.select("div.matches__item--body > div.matches__status > span.time").text()
        } catch (e: Exception) {
            ""
        }
        val home = FootballTeam(
            name = homeTeamName.replace("\n", " "),
            logo = homeTeamLogo,
            id = "${matchId}_home",
            leagueName = league,
            countryName = ""
        )
        val away = FootballTeam(
            name = awayTeamName.replace("\n", " "),
            logo = awayTeamLogo,
            id = "${matchId}_away",
            leagueName = league,
            countryName = ""
        )
        return FootballMatch(
            id = matchId.takeIf {
                it.isNotEmpty()
            } ?: "${home.name} vs ${away.name}",
            name = "${home.name} vs ${away.name}",
            time = kickOffTimeInSecond.toString(),
            date = try {
                date.trim().split(" ")[1]
            } catch (e: Exception) {
                ""
            },
            hour = try {
                date.trim().split(" ")[0]
            } catch (e: Exception) {
                ""
            },
            homeTeam = home,
            awayTeam = away,
            stadium = "",
            score = score,
            liveStatus = status,
            league = league,
            streamLink = listOf(
                Link(
                    link = link,
                    format = "",
                    id = link,
                    name = "Default",
                    resolution = "",
                    streamType = "",
                    linkType = Link.LinkType.SEARCHABLE.value,
                    expired = kickOffTimeInSecond * 1000L + 1000 * 60 * 120 // 120 minutes
                )
            )
        )

    }

    override suspend fun getMatchById(id: String): Flow<Player> = withContext(_ioDispatcher) {
        return@withContext flow<Player> {
            val item = _currentItems.find { it.id == id }
            if (item == null) {
                throw IllegalStateException("Item not found")
            }
            if (item.streamLink.isEmpty()) {
                throw IllegalStateException("Item has no stream link")
            }
            val playableLink = item.streamLink.find {
                it.linkType == Link.LinkType.PLAYABLE.value
            }
            if (playableLink != null) {
                emit(
                    Player(
                        id = playableLink.id,
                        name = item.name,
                        streamLink = playableLink,
                        searchableLink = item.streamLink,
                        parentId = item.id
                    )
                )
                return@flow
            }

            val searchableLink = item.streamLink.first {
                it.linkType == Link.LinkType.SEARCHABLE.value
            }
            emitAll(getMatchByLink(item, searchableLink))
        }
    }

    private fun parseM3u8LinkFromFrame(
        url: String, link: Link, match: FootballMatch,
    ): Player? {
        var streamUrl: String? = null
        val response = jsoupParse(url, _cookie, Pair("Referer", url))
        _cookie = _cookie.toMutableMap().also {
            it.putAll(response.cookie)
        }
        val m3u8Dom = response.body
        Log.d("XoilacZCORepository", "parseM3u8LinkFromFrame: ${response.responseUrl}")
        val scripts = m3u8Dom.getElementsByTag("script")
        for (i in scripts) {
            val html = i.html().trim()
            if (html.startsWith("var")) {
                val matcher: Matcher = REGEX_URL.matcher(html)
                while (matcher.find()) {
                    matcher.group(0)?.replace("\u003d", "=")?.let {
                        streamUrl = it
                    }
                }
            }
        }
        return streamUrl?.let {
            Player(
                id = link.id,
                name = match.name,
                streamLink = link.copy(
                    link = it,
                    linkType = Link.LinkType.PLAYABLE.value,
                    requestHeader = mapOf(
                        "Referer" to response.responseUrl.getBaseUrl() + "/",
                        "Origin" to response.responseUrl.getBaseUrl()
                    )
                ),
                searchableLink = match.streamLink,
                parentId = match.id
            )
        }
    }

    override suspend fun getMatchByLink(match: FootballMatch, link: Link): Flow<Player> {
        if (link.linkType == Link.LinkType.PLAYABLE.value) {
            val searchableLink = _currentItems.indexOfFirst {
                it.id == match.id
            }.takeIf {
                it != -1
            }?.let {
                _currentItems[it].streamLink
            } ?: match.streamLink
            return flowOf(
                Player(
                    id = link.id,
                    name = match.name,
                    streamLink = link,
                    searchableLink = searchableLink,
                    parentId = match.id
                )
            )
        }

        return flow {
            val response = try {
                jsoupParse(link.link, _cookie, Pair("Referer", link.link))
            } catch (e: Exception) {
                throw e
            }
            _cookie = _cookie.toMutableMap().also {
                it.putAll(response.cookie)
            }
            val dom = response.body
            val otherLinks = dom.toOtherLinks().toMutableList()
            val iframes = dom.getElementById("player")!!.getElementsByTag("iframe")
            for (frame in iframes) {
                val src = frame.attributes().get("src")
                parseM3u8LinkFromFrame(src, link, match)?.let { player ->
                    val finalLink = otherLinks.indexOfFirst {
                        it.id == player.id
                    }.takeIf {
                        it != -1
                    }?.let {
                        otherLinks[it] = player.streamLink.copy(
                            name = otherLinks[it].name
                        )
                        otherLinks[it]
                    } ?: player.streamLink
                    updateLinkToMatch(match, otherLinks)
                    emit(player.copy(
                        searchableLink = otherLinks,
                        streamLink = finalLink
                    ).also {
                        Log.d("XoilacZCORepository", "getMatchByLink: $it")
                    })
                    return@flow
                }
            }
        }
    }

    private fun updateLinkToMatch(
        match: FootballMatch,
        otherLinks: MutableList<Link>
    ) {
        synchronized(_currentItems) {
            _currentItems.indexOfFirst {
                it.id == match.id
            }.takeIf { it != -1 }?.let {
                _currentItems[it] = _currentItems[it]
                    .copy(streamLink = otherLinks.toMutableList())
            }
        }
    }

    private fun Element.toOtherLinks(): List<Link> {
        return kotlin.runCatching {
            this.select("#tv_links > a").map {
                Link(
                    link = it.attributes().get("href"),
                    format = "",
                    id = it.attributes().get("href"),
                    name = it.text(),
                    resolution = "",
                    linkType = Link.LinkType.SEARCHABLE.value,
                    streamType = "",
                    expired = System.currentTimeMillis() + 1000 * 60 * 120
                )
            }
        }.getOrElse {
            emptyList()
        }
    }

    override suspend fun getLiveMatches(): Flow<List<FootballMatch>> = withContext(_ioDispatcher) {
        while (_isLoading.get()) {
            delay(50)
        }
        val allMatchFlow = _currentItems.takeIf {
            it.isNotEmpty()
        }?.let {
            flowOf(it)
        } ?: getAllMatches()

        return@withContext allMatchFlow
            .map {
                it.filter {
                    it.liveStatus.isNotEmpty() && it.liveStatus != "FT"
                            && it.liveStatus != "KT"
                }
            }
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
        private const val DEFAULT_URL = "https://90ptv.vip/"
        private const val EXTRA_COOKIE_NAME = "extra:cookie_xoilacz"
        private const val SELECTOR_ITEM = ".matches__item.col-lg-6.col-sm-6"
        private val REGEX_URL = Pattern.compile("(?<=urlStream\\s=\\s\").*?(?=\")")
    }
}