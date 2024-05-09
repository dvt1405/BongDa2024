package com.kt.apps.media.api.models.football

import android.os.Parcelable
import com.kt.apps.media.api.models.stream.Link
import kotlinx.parcelize.Parcelize

@Parcelize
data class FootballMatch(
    val id: String,
    val name: String,
    val time: String,
    val date: String,
    val homeTeam: FootballTeam,
    val awayTeam: FootballTeam,
    val stadium: String,
    val score: String,
    val liveStatus: String,
    val league: String,
    val streamLink: List<Link>
) : Parcelable {
    override fun toString(): String {
        return "FootballMatch(id='$id', " +
                "name='$name', " +
                "time='$time', " +
                "date='$date', " +
                "homeTeam=$homeTeam, " +
                "awayTeam=$awayTeam, " +
                "stadium='$stadium', " +
                "score='$score', " +
                "liveStatus='$liveStatus', " +
                "league='$league', " +
                "streamLink=$streamLink)\n"
    }
}