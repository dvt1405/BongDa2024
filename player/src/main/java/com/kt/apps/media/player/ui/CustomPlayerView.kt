package com.kt.apps.media.player.ui

import android.view.SurfaceView
import android.view.TextureView
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.Player


@Composable
fun CustomPlayerView() {
}



@Composable
private fun VideoSurface(
    state: Player,
    surfaceType: SurfaceType,
    modifier: Modifier
) {
    val context = LocalContext.current
    key(surfaceType, context) {
        if (surfaceType != SurfaceType.None) {
            val videoView = remember {
                when (surfaceType) {
                    SurfaceType.None -> throw IllegalStateException()
                    SurfaceType.SurfaceView -> SurfaceView(context)
                    SurfaceType.TextureView -> TextureView(context)
                }
            }
            AndroidView(
                factory = { videoView },
                modifier = modifier,
            ) {
                // update player
                val currentPlayer = state
                val previousPlayer = it.tag as? Player
                if (previousPlayer === currentPlayer) return@AndroidView
                previousPlayer?.clearVideoView(it, surfaceType)
                it.tag = currentPlayer.apply {
                    setVideoView(it, surfaceType)
                }
            }
            DisposableEffect(Unit) {
                onDispose {
                    (videoView.tag as? Player)?.clearVideoView(
                        videoView,
                        surfaceType
                    )
                }
            }
        }
    }
}

enum class SurfaceType {
    SurfaceView,
    TextureView,
    None
}


fun Player.clearVideoView(
    view: View,
    surfaceType: SurfaceType
) {
    when (surfaceType) {
        SurfaceType.None -> throw IllegalStateException()
        SurfaceType.SurfaceView -> clearVideoSurfaceView(view as SurfaceView)
        SurfaceType.TextureView -> clearVideoTextureView(view as TextureView)
    }
}

fun Player.setVideoView(view: View, surfaceType: SurfaceType) {
    when (surfaceType) {
        SurfaceType.None -> throw IllegalStateException()
        SurfaceType.SurfaceView -> setVideoSurfaceView(view as SurfaceView)
        SurfaceType.TextureView -> setVideoTextureView(view as TextureView)
    }
}