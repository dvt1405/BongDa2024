package com.kt.apps.media.football

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import com.kt.apps.media.api.IFootballMatchRepository
import com.kt.apps.media.football.di.annotations.FootballRepository
import com.kt.apps.media.football.di.annotations.FootballRepositoryType
import com.kt.apps.media.football.models.DarkThemeConfig
import com.kt.apps.media.football.ui.screens.FootballLiveApp
import com.kt.apps.media.football.ui.screens.player.PlayerViewModel
import com.kt.apps.media.football.ui.screens.splash.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    @FootballRepository(FootballRepositoryType.VeBo)
    lateinit var repository: IFootballMatchRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)
        enableEdgeToEdge()
        setContent {
            val darkTheme = shouldUseDarkTheme(uiState)
            // Update the edge to edge configuration to match the theme
            // This is the same parameters as the default enableEdgeToEdge call, but we manually
            // resolve whether or not to show dark theme using uiState, since it can be different
            // than the configuration's dark theme value based on the user preference.
            DisposableEffect(darkTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        android.graphics.Color.TRANSPARENT,
                        android.graphics.Color.TRANSPARENT,
                    ) { darkTheme },
                    navigationBarStyle = SystemBarStyle.auto(
                        lightScrim,
                        darkScrim,
                    ) { darkTheme },
                )
                onDispose {}
            }
            CompositionLocalProvider(value = LocalLifecycleOwner provides this) {
                setContent {
                    FootballLiveApp(
                        splashViewModel = hiltViewModel(),
                        mainViewModel = hiltViewModel()
                    )
                }

            }
        }
    }
}

@Composable
private fun shouldUseDarkTheme(
    uiState: MainActivityUiState,
): Boolean = when (uiState) {
    MainActivityUiState.Loading -> isSystemInDarkTheme()
    is MainActivityUiState.Success -> when (uiState.userData.darkThemeConfig) {
        DarkThemeConfig.FOLLOW_SYSTEM -> isSystemInDarkTheme()
        DarkThemeConfig.LIGHT -> false
        DarkThemeConfig.DARK -> true
    }
}

/**
 * The default light scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=35-38;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val lightScrim = android.graphics.Color.argb(0xe6, 0xFF, 0xFF, 0xFF)

/**
 * The default dark scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=40-44;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val darkScrim = android.graphics.Color.argb(0x80, 0x1b, 0x1b, 0x1b)