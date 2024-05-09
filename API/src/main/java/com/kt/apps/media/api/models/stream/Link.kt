package com.kt.apps.media.api.models.stream

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Link(
    val format: String,
    val link: String,
    val expired: Long,
    val resolution: String,
    val name: String,
    val id: String,
    val linkType: String,
    val streamType: String? = null
) : Parcelable {
    enum class LinkType(
        val value: String
    ) {
        PLAYABLE("playable"),
        SEARCHABLE("searchable"),
    }

    override fun toString(): String {
        return "Link(format='$format', " +
                "link='$link', " +
                "expired=$expired, " +
                "resolution='$resolution', " +
                "name='$name', " +
                "id='$id', " +
                "linkType='$linkType', " +
                "streamType=$streamType)"
    }
}