package com.kt.apps.media.api.models.players

import com.kt.apps.media.api.models.stream.Link

data class Player(
    val id: String,
    val name: String,
    val streamLink: Link,
    val searchableLink: List<Link>,
    val parentId: String,
) {
    override fun toString(): String {
        return "Player(id='$id'," +
                " name='$name'," +
                " streamLink=$streamLink," +
                " searchableLink=$searchableLink," +
                " parentId='$parentId')"
    }
}