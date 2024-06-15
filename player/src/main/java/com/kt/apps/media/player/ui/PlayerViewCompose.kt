package com.kt.apps.media.player.ui

import android.view.LayoutInflater
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import com.kt.apps.media.player.R


@OptIn(UnstableApi::class) @Composable
fun PlayerViewCompose(
    modifier: Modifier = Modifier,
    player: Player? = null,
    useController: Boolean = true
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycle = remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle.value = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    AndroidView(
        factory = { context ->
            LayoutInflater.from(context)
                .inflate(R.layout.custom_player_view, null, false)
                .also {
                    it.findViewById<PlayerView>(R.id.player_view).player = player
                }
            PlayerView(context).also {
                if (!useController) {
                    it.controllerAutoShow = false
                    it.controllerHideOnTouch = false
                    it.useController = false
                }
                it.player = player
            }
        },
        update = {
            when (lifecycle.value) {
                Lifecycle.Event.ON_PAUSE -> {
                    it.onPause()
                    it.player?.pause()
                }

                Lifecycle.Event.ON_RESUME -> {
                    it.player = player
                    it.onResume()
                }

                Lifecycle.Event.ON_STOP -> {
                    it.player = null
                }

                else -> Unit
            }
        },
        modifier = modifier
            .aspectRatio(16 / 9f),


        )
}