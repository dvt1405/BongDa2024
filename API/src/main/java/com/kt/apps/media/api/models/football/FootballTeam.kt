package com.kt.apps.media.api.models.football

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FootballTeam(
    val id: String,
    val name: String,
    val leagueName: String,
    val logo: String,
    val countryName: String
) : Parcelable {
    override fun toString(): String {
        return "FootballTeam(id='$id', " +
                "name='$name', " +
                "leagueName='$leagueName', " +
                "logo='$logo', " +
                "countryName='$countryName')n"
    }
}