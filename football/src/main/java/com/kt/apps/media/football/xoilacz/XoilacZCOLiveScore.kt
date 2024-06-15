package com.kt.apps.media.football.xoilacz

class XoilacZCOLiveScore : ArrayList<XoilacZCOLiveScoreItem>()
data class XoilacZCOLiveScoreItem(
    val away: Away,
    val awayCorner: Int,
    val awayHalfScore: Int,
    val halfStartTime: Int,
    val home: Home,
    val homeCorner: Int,
    val homeHalfScore: Int,
    val matchId: Int,
    val matchTime: Int,
    val status: Status
)

data class Status(
    val code: Int,
    val elapsed: Int,
    val long: String
)

data class Home(
    val score: String
)

data class Away(
    val score: String
)