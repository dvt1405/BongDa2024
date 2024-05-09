package com.kt.apps.media.api.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class League(
    open val id: String,
    open val name: String,
    open val logo: String,
    open val season: String,
) : Parcelable {
}

data class CountryLeague(
    override val id: String,
    override val name: String,
    override val logo: String,
    override val season: String,
    val country: String,
) : League(id, name, logo, season) {
}