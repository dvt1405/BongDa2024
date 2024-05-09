package com.kt.apps.media.football.nightyp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NinetyWebsite(
    val id: String,
    val name: String,
    val url: String,
    val logo: String
) : Parcelable