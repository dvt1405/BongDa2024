package com.kt.apps.media.football

import com.kt.apps.media.football.models.UserAppConfigData

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(val userData: UserAppConfigData) : MainActivityUiState
}