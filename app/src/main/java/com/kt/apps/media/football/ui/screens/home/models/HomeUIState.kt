package com.kt.apps.media.football.ui.screens.home.models

import com.kt.apps.media.api.models.football.FootballMatch
import com.kt.apps.media.api.models.football.FootballTeam
import com.kt.apps.media.api.models.stream.Link

data class HomeUIState(
    val isLoading: Boolean = false,
    val liveMatches: List<UIHomeLiveItem> = emptyList(),
    val upcomingMatches: List<FootballMatch> = emptyList()
)

interface UIHomeLiveItem {
    val id: String
    val name: String
    val time: String
    val date: String
    val hour: String
    val homeTeam: UIHomeFootballTeam
    val awayTeam: UIHomeFootballTeam
    val stadium: String
    val score: String
    val liveStatus: String
    val league: String
    val streamLink: List<HomeLink>
    val blurRes: Int
}

interface UIHomeFootballTeam {
    val id: String
    val name: String
    val leagueName: String
    val logo: String
    val countryName: String
    companion object {
        fun fromFootballTeam(team: FootballTeam): UIHomeFootballTeam {
            return object : UIHomeFootballTeam {
                override val id: String
                    get() = team.id
                override val name: String
                    get() = team.name
                override val leagueName: String
                    get() = team.leagueName
                override val logo: String
                    get() = team.logo
                override val countryName: String
                    get() = team.countryName
            }
        }
    }
}

internal fun UIHomeFootballTeam.toFootballTeam(): FootballTeam {
    return FootballTeam(
        id = id,
        name = name,
        leagueName = leagueName,
        logo = logo,
        countryName = countryName
    )
}
fun UIHomeLiveItem.toFootballMatch(): FootballMatch {
    return FootballMatch(
        id = id,
        name = name,
        time = time,
        date = date,
        hour = hour,
        homeTeam = homeTeam.toFootballTeam(),
        awayTeam = homeTeam.toFootballTeam(),
        stadium = stadium,
        score = score,
        liveStatus = liveStatus,
        league = league,
        streamLink = streamLink.map { it.toLink() }
    )
}

interface HomeLink {
    val format: String
    val link: String
    val expired: Long
    val resolution: String
    val name: String
    val id: String
    val linkType: String
    val streamType: String?

    companion object {
        fun fromLink(link: Link): HomeLink {
            return object : HomeLink {
                override val format: String
                    get() = link.format
                override val link: String
                    get() = link.link
                override val expired: Long
                    get() = link.expired
                override val resolution: String
                    get() = link.resolution
                override val name: String
                    get() = link.name
                override val id: String
                    get() = link.id
                override val linkType: String
                    get() = link.linkType
                override val streamType: String?
                    get() = link.streamType
            }
        }
    }
}
    fun HomeLink.toLink(): Link {
        return Link(
            format = this.format,
            link = this.link,
            expired = this.expired,
            resolution = this.resolution,
            name = this.name,
            id = this.id,
            linkType = this.linkType,
            streamType = this.streamType
        )
    }

internal fun FootballMatch.toHomeItem(
    blurRes: Int
): UIHomeLiveItem {
    val _id = this.id
    val _name = this.name
    val _time = this.time
    val _date = this.date
    val _hour = this.hour
    val _homeTeam = this.homeTeam
    val _awayTeam = this.awayTeam
    val _stadium = this.stadium
    val _score = this.score
    val _liveStatus = this.liveStatus
    val _league = this.league
    val _streamLink = this.streamLink.map {HomeLink.fromLink(it)
    }

    return object : UIHomeLiveItem {
        override val id: String
            get() = _id
        override val name: String
            get() = _name
        override val time: String
            get() = _time
        override val date: String
            get() = _date
        override val hour: String
            get() = _hour
        override val homeTeam: UIHomeFootballTeam
            get() = UIHomeFootballTeam.fromFootballTeam(_homeTeam)
        override val awayTeam: UIHomeFootballTeam
            get() = UIHomeFootballTeam.fromFootballTeam(_awayTeam)
        override val stadium: String
            get() = _stadium
        override val score: String
            get() = _score
        override val liveStatus: String
            get() = _liveStatus
        override val league: String
            get() = _league
        override val streamLink: List<HomeLink>
            get() = _streamLink
        override val blurRes: Int
            get() = blurRes

    }
}