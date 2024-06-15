package com.kt.apps.media.football.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kt.apps.media.api.IAppSettingsRepository
import com.kt.apps.media.football.App
import com.kt.apps.media.football.ui.screens.splash.uistates.SplashScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val _appSettings: IAppSettingsRepository
) : ViewModel() {
    private val _splashUISate by lazy {
        MutableStateFlow(
            SplashScreenState(
                true,
                false
            )
        )
    }

    val uiState: StateFlow<SplashScreenState> = _splashUISate.asStateFlow()

    init {
        viewModelScope.launch {
            _splashUISate.emit(
                _splashUISate.value.copy(
                    isFirstTimeOpen = isFirstTimeOpen(),
                    isAcceptedPrivacyPolicy = _appSettings.privacyPolicyAccepted()
                )
            )
        }
    }

    private suspend fun isFirstTimeOpen(): Boolean {
        return _appSettings.isFirstTimeUser()
    }

    fun setFirstTimeOpen(isFirstTimeOpen: Boolean) {
        viewModelScope.launch {
            _splashUISate.emit(
                _splashUISate.value.copy(isFirstTimeOpen = isFirstTimeOpen)
            )
            _appSettings.setFirstTimeUser(isFirstTimeOpen)
        }
    }
}