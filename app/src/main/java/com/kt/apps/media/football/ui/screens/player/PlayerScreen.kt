package com.kt.apps.media.football.ui.screens.player

import androidx.activity.ComponentActivity
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.github.fengdai.compose.media.Media
import com.github.fengdai.compose.media.MediaState
import com.github.fengdai.compose.media.ResizeMode
import com.github.fengdai.compose.media.ShowBuffering
import com.github.fengdai.compose.media.SurfaceType
import com.github.fengdai.compose.media.rememberMediaState
import com.kt.apps.media.api.models.stream.Link
import com.kt.apps.media.player.ui.PlayerViewCompose

@OptIn(UnstableApi::class)
@Composable
fun PlayerScreen(
    matchId: String,
    playerViewModel: PlayerViewModel = hiltViewModel(
        LocalContext.current as ComponentActivity
    )
) {
    val uiState = playerViewModel.playerUIState.collectAsState()
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = it.calculateTopPadding()
                )
        ) {
            PlayerViewCompose(player = playerViewModel.exoPlayer)
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                items(uiState.value.otherLinks?.size ?: 0) { index ->
                    val link = uiState.value.otherLinks!![index]
                    PlayerLinkItem(
                        link = link,
                        onClick = {
                            playerViewModel.getLinkForMatch(link)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MinimizePlayerScreen(
    player: Player,
    itemName: String = "Minimize Player"
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.White)
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PlayerViewCompose(
            player = player,
            modifier = Modifier
                .fillMaxHeight(),
            useController = false
        )
        Spacer(modifier = Modifier.size(5.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(5.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ) {
            Text(
                text = itemName,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.size(1.dp))
            Text(
                text = "1 - 0",
                style = MaterialTheme.typography.titleSmall
            )
        }
        Spacer(modifier = Modifier.size(5.dp))
        IconButton(
            onClick = {
                if (player.isPlaying) {
                    player.pause()
                } else {
                    player.play()
                }
            }, modifier = Modifier.size(
                36.dp
            )
        ) {
            Icon(
                imageVector = if (player.isPlaying) {
                    Icons.Rounded.Menu
                } else {
                    Icons.Rounded.PlayArrow
                },
                contentDescription = "Play"
            )
        }

        IconButton(
            onClick = {

            }, modifier = Modifier.size(
                36.dp
            )
        ) {
            Icon(imageVector = Icons.Rounded.Close, contentDescription = "Close")
        }
    }
}

@Composable
fun PlayerLinkItem(
    link: Link,
    onClick: () -> Unit = {},
) {
    ElevatedButton(
        onClick = onClick,
        modifier = Modifier.clip(
            shape = RoundedCornerShape(50)
        )
    ) {
        Text(text = link.name)
    }
}

@Preview
@Composable
fun PlayerLinkItemPreview() {
    PlayerLinkItem(
        Link(
            "1",
            "Link 1",
            0L,
            "https://www.youtube.com/watch?v=1",
            "Link 1",
            "https://www.youtube.com/watch?v=1",
            "https://www.youtube.com/watch?v=1",
            "https://www.youtube.com/watch?v=1"
        ),
    )
}

@Preview
@Composable
fun MinimizePlayerScreenPreview() {
    MinimizePlayerScreen(
        ExoPlayer.Builder(LocalContext.current).build()
    )
}

@Preview
@Composable
fun PlayerScreenPreview() {
    PlayerScreen("1")
}

@Composable
fun TestMedia(
    player: Player
) {
    val state = rememberMediaState(player = player)
    Media(
        state = state,
        // following parameters are optional
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        surfaceType = SurfaceType.SurfaceView,
        resizeMode = ResizeMode.Fit,
        keepContentOnPlayerReset = false,
        useArtwork = true,
        showBuffering = ShowBuffering.Always,
        buffering = {
            Box(
                Modifier.fillMaxSize(),
                Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    ) { state ->
        SimpleController(state, Modifier.fillMaxSize())
    }
}

@Composable
fun SimpleController(
    state: MediaState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.1f))
    ) {
        Text(
            text = state.playerState.toString(),
            color = Color.White
        )
    }
}
