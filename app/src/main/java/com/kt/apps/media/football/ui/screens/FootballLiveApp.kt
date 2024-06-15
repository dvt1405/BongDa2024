package com.kt.apps.media.football.ui.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kt.apps.media.football.App
import com.kt.apps.media.football.MainViewModel
import com.kt.apps.media.football.ui.screens.home.HomedScreen
import com.kt.apps.media.football.ui.screens.player.PlayerScreen
import com.kt.apps.media.football.ui.screens.splash.SplashScreen
import com.kt.apps.media.football.ui.screens.splash.SplashViewModel
import com.kt.apps.media.football.ui.theme.XemBongDaTheme

enum class FootballLiveNavScreens(val route: String) {
    SPLASH_SCREEN("SplashScreen"),
    MAIN_SCREEN("MainScreen"),
    PRIVACY_SCREEN("PrivacyPolicyScreen"),
    ABOUT_SCREEN("AboutScreen"),
    SETTINGS_SCREEN("SettingsScreen"),
    DETAIL_MATCH("DetailMatch/{matchId}")
}

@Composable
fun FootballLiveApp(
    darkTheme: Boolean = isSystemInDarkTheme(),
    splashViewModel: SplashViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val splashUIState = splashViewModel.uiState.collectAsState()
    XemBongDaTheme(
        darkTheme = darkTheme,
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = if (splashUIState.value.isFirstTimeOpen) {
                    FootballLiveNavScreens.SPLASH_SCREEN.route
                } else {
                    FootballLiveNavScreens.MAIN_SCREEN.route
                }
            ) {
                composable(FootballLiveNavScreens.SPLASH_SCREEN.route) {
                    SplashScreen(
                        splashViewModel,
                        navController
                    )
                }
                composable(FootballLiveNavScreens.MAIN_SCREEN.route) {
                    HomedScreen(
                        navController,
                        mainViewModel
                    )
                }
                composable(FootballLiveNavScreens.PRIVACY_SCREEN.route) {
                    HomedScreen(
                        navController,
                        mainViewModel
                    )
                }
                composable(
                    FootballLiveNavScreens.DETAIL_MATCH.route,
                    arguments = listOf(navArgument("matchId") { type = NavType.StringType })
                ) {
                    PlayerScreen(
                        matchId = it.arguments?.getString("matchId") ?: ""
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun FootballLiveAppPreview() {
    FootballLiveApp(
        splashViewModel = SplashViewModel(
            App.getInstance().appSettingsRepository
        )
    )
}