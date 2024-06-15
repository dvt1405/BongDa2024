package com.kt.apps.media.football.ui.screens.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.DefaultHlsDataSourceFactory
import androidx.media3.exoplayer.hls.HlsMediaSource
import com.kt.apps.media.api.IFootballMatchRepository
import com.kt.apps.media.api.models.football.FootballMatch
import com.kt.apps.media.api.models.players.Player
import com.kt.apps.media.api.models.stream.Link
import com.kt.apps.media.football.di.annotations.FootballRepository
import com.kt.apps.media.football.di.annotations.FootballRepositoryType
import com.kt.apps.media.sharedutils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@UnstableApi
class PlayerViewModel @Inject constructor(
    @FootballRepository(FootballRepositoryType.XoiLacZCO)
    private val playerRepository: IFootballMatchRepository,
    val exoPlayer: ExoPlayer
) : ViewModel() {
    private val _playerUIState = MutableStateFlow(PlayerUIState())
    private var loadLinkJob: Job? = null
    val playerUIState: StateFlow<PlayerUIState>
        get() = _playerUIState

    fun getLinkForMatch(footballMatch: FootballMatch) {
        loadLinkJob?.cancel()
        val link = footballMatch.streamLink.first()
        viewModelScope.launch {
            _playerUIState.emit(_playerUIState.value.copy(
                match = footballMatch,
                currentLink = link,
                otherLinks = footballMatch.streamLink
            ))
            getLinkForMatch(link = link)
        }
    }

    fun getLinkForMatch(link: Link) {
        loadLinkJob?.cancel()
        loadLinkJob = viewModelScope.launch(Dispatchers.IO) {
            playerRepository.getMatchByLink(_playerUIState.value.match!!, link)
                .catch {

                }
                .collectLatest {
                    _playerUIState.emit(_playerUIState.value.copy(
                        match = _playerUIState.value.match,
                        player = it,
                        currentLink = it.streamLink,
                        otherLinks = it.searchableLink
                    ))
                    playMatch(it)
                }
        }
    }

    fun playMatch(player: Player) {
        viewModelScope.launch(Dispatchers.Main) {
            exoPlayer.setMediaSource(
                HlsMediaSource.Factory(
                    DefaultHlsDataSourceFactory(
                        DefaultHttpDataSource.Factory()
                            .setDefaultRequestProperties(
                                player.streamLink.requestHeader ?: mapOf(
                                    "Referer" to "https://91p.plcdn.xyz/",
                                    "Origin" to "https://91p.plcdn.xyz/",
                                )
                            )
                            .setUserAgent(Constants.HEADER_USER_AGENT_CHROME)
                    )
                ).createMediaSource(
                    MediaItem.fromUri(player.streamLink.link)
                        .buildUpon()
                        .build()
                )
            )
            exoPlayer.playWhenReady = true
            exoPlayer.prepare()
            exoPlayer.play()
        }
    }
}