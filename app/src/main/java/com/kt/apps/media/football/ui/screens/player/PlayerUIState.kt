package com.kt.apps.media.football.ui.screens.player

import com.kt.apps.media.api.models.football.FootballMatch
import com.kt.apps.media.api.models.players.Player
import com.kt.apps.media.api.models.stream.Link

data class PlayerUIState(
    val match: FootballMatch? = null,
    val currentLink: Link? = null,
    val otherLinks: List<Link>? = null,
    val player: Player? = null
) {
}